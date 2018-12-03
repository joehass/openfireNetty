package org.jivesoftware.openfire.Connection.redis;

import org.jivesoftware.openfire.Connection.util.cache.LinkedListNode;

import java.io.Serializable;

public class CacheObject<V> implements Serializable {

    private static final long serialVersionUID = -5995752477807603819L;
    public V object;
    public int size;
    public LinkedListNode<?> lastAccessedListNode;
    public LinkedListNode<?> ageListNode;
    public int readCount = 0;

    public CacheObject(V object, int size) {
        this.object = object;
        this.size = size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public V getObject() {
        return object;
    }

    public void setObject(V object) {
        this.object = object;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedListNode<?> getLastAccessedListNode() {
        return lastAccessedListNode;
    }

    public void setLastAccessedListNode(LinkedListNode<?> lastAccessedListNode) {
        this.lastAccessedListNode = lastAccessedListNode;
    }

    public LinkedListNode<?> getAgeListNode() {
        return ageListNode;
    }

    public void setAgeListNode(LinkedListNode<?> ageListNode) {
        this.ageListNode = ageListNode;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }
}
