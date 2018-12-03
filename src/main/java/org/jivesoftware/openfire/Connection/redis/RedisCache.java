package org.jivesoftware.openfire.Connection.redis;

import org.jivesoftware.openfire.Connection.util.cache.CacheSizes;
import org.jivesoftware.openfire.Connection.util.cache.CannotCalculateSizeException;
import org.jivesoftware.openfire.Connection.util.cache.LinkedList;
import org.jivesoftware.openfire.Connection.util.cache.LinkedListNode;

import java.io.Serializable;
import java.util.*;

public class RedisCache<K,V> implements Map<K,V>, Serializable {

    public RedisPoolMgr redisPoolMgr;
    protected LinkedList<K> lastAccessedList;
    protected LinkedList<K> ageList;
    private long maxCacheSize;
    private int cacheSize = 0;
    protected long maxLifetime;
    protected long cacheHits;
    protected long cacheMisses = 0L;//统计redis的未命中次数
    private String name;

    public RedisCache(String name, long maxSize, long maxLifetime) {
        this.name = name;
        this.maxCacheSize = maxSize;
        this.maxLifetime = maxLifetime;
        this.redisPoolMgr = RedisPoolMgr.getInstance();
        this.lastAccessedList = new LinkedList();
        this.ageList = new LinkedList();
        System.out.println("RedisCache==构造函数");
    }

    public synchronized V put(K key, V value){
        V answer = this.remove(key);//从链表中删除该节点
        int objectSize = 1;

        try {
            objectSize = CacheSizes.sizeOfAnything(value);//判断该节点大小
        } catch (CannotCalculateSizeException e) {
            e.printStackTrace();
        }

        //如果节点太大，则不放入链表
        if (this.maxCacheSize >0L && (double)objectSize > (double)this.maxCacheSize * 0.9D){
            return value;
        }else {//如果节点大小合适，则将该节点放入链表中
            this.cacheSize += objectSize;
            CacheObject cacheObject = new CacheObject(value,objectSize);
            LinkedListNode<K> lastAccessedNode = this.lastAccessedList.addFirst(key);
            cacheObject.lastAccessedListNode = lastAccessedNode;
            LinkedListNode<K> ageNode = this.ageList.addFirst(key);
            ageNode.timestamp = System.currentTimeMillis();
            cacheObject.ageListNode = ageNode;
            this.redisPoolMgr.setRedisCacheObject(this.name,key.toString(),cacheObject);//将其存入缓存中
            this.cullCache();

            return answer;
        }
    }

    /**
     * 获得key对应的value值并将这个值放到队列头部
     * @param key
     * @return
     */
    public synchronized V get(Object key){
        this.deleteExpireEntries();
        String strKey = null;
        if (key != null){
            strKey = key.toString();
        }

        CacheObject cacheObject = this.redisPoolMgr.getRedisCacheObject(this.name, strKey);
        if (cacheObject != null && cacheObject.object != null) {
            System.out.println("RedisCache==get==CacheObject中的对象类：" + cacheObject.object.getClass().getName());
        } else {
            System.out.println("RedisCache==get==CacheObject中的对象类：" + cacheObject);
        }

        if (cacheObject == null){
            this.cacheMisses++;
            return null;
        }

        this.cacheHits++;
        cacheObject.readCount++;

        //将该值放到队首
        cacheObject.lastAccessedListNode.remove();
        lastAccessedList.addFirst((LinkedListNode<K>) cacheObject.lastAccessedListNode);

        return (V) cacheObject.object;
    }

    //删除链表中cache大于规定大小的节点，并删除链表最后一个节点超时的节点
    protected final void cullCache(){
        if (this.maxCacheSize >= 0L){
            int desiredSize = (int)((double)this.maxCacheSize * 0.97D);
            if (this.cacheSize >= desiredSize){//如果cache太大
                this.deleteExpireEntries();//判断节点是否超时，如果超时则删除该节点
                desiredSize = (int)((double)this.maxCacheSize * 0.9D);
                if (this.cacheSize > desiredSize){
                    long t = System.currentTimeMillis();
                    //如果节点太大，则删除最后一个节点
                    do {
                        this.remove(this.lastAccessedList.getLast().object);
                    }while (this.cacheSize > desiredSize);
                }
            }
        }
    }

    /**
     * 删除超时的链表节点
     */
    protected void deleteExpireEntries(){
        if (this.maxLifetime > 0L){
            LinkedListNode<K> node = this.ageList.getLast();
            if (node != null){
                long expireTIme = System.currentTimeMillis() - this.maxLifetime;
                while (expireTIme > node.timestamp){
                    if (this.remove(node.object) == null){
                        return;
                    }

                    node = this.ageList.getLast();
                    if (node == null){
                        return;
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        deleteExpireEntries();//删除过期节点

        return redisPoolMgr.getRedisCacheSize(name);
    }

    @Override
    public boolean isEmpty() {
        deleteExpireEntries();

        return redisPoolMgr.isRedisCacheEmpty(name);
    }

    @Override
    public boolean containsKey(Object key) {
        // First, clear all entries that have been in cache longer than the
        // maximum defined age.
        deleteExpireEntries();

        // return map.containsKey(key);
        String strKey = "";
        if (null != key) {
            strKey = key.toString();
        }
        return redisPoolMgr.isRedisCacheContainsKey(name, strKey);
    }

    @Override
    public boolean containsValue(Object value) {
        deleteExpireEntries();

        if (value == null){
            return containNullValue();
        }

        Iterator it = values().iterator();

        while (it.hasNext()){
            if (value.equals(it.next())){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否有元素
     * @return true：有元素，false：没元素
     */
    private boolean containNullValue(){
        Iterator it = values().iterator();

        while (it.hasNext()){
            if (it.next() == null){
                return true;
            }
        }
        return false;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        redisPoolMgr.clearRedisCacheObject(name);

        lastAccessedList.clear();
        lastAccessedList = new LinkedList<>();
        ageList.clear();
        ageList = new LinkedList<>();

        cacheSize = 0;
        cacheHits = 0;
        cacheMisses = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {

        deleteExpireEntries();

        return new RedisCache.CacheObjectCollection(redisPoolMgr.getRedisCacheValues(name));
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private final class CacheObjectCollection<V> implements Collection<V> {

        private Collection<CacheObject<V>> cachedObjects;

        private CacheObjectCollection(Collection<CacheObject<V>> cachedObjects){
            this.cachedObjects = new ArrayList<>(cachedObjects);
        }

        @Override
        public int size() {
           return cachedObjects.size();
        }

        @Override
        public boolean isEmpty() {
           return size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            Iterator<V> it = iterator();
            while (it.hasNext()){
                if (it.next().equals(0)){
                    return true;
                }
            }
            return false;
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>() {
                private final Iterator<CacheObject<V>> it = cachedObjects.iterator();

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public V next() {
                    if (it.hasNext()) {
                        CacheObject<V> object = it.next();
                        if (object == null) {
                            return null;
                        } else {
                            return object.object;
                        }
                    } else {
                        throw new NoSuchElementException();
                    }
                }
            };
        }

        @Override
        public Object[] toArray() {
            Object[] array = new Object[size()];
            Iterator it = iterator();
            int i = 0;
            while (it.hasNext()) {
                array[i] = it.next();
            }
            return array;
        }

        @Override
        public <V> V[] toArray(V[] a) {
            Iterator<V> it = (Iterator<V>) iterator();
            int i = 0;
            while (it.hasNext()) {
                a[i++] = it.next();
            }
            return a;
        }

        @Override
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            Iterator it = c.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends V> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }
}
