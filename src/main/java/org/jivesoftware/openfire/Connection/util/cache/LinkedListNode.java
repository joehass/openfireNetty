package org.jivesoftware.openfire.Connection.util.cache;

/**
 * 链表节点
 */
public class LinkedListNode<E> {
    public  LinkedListNode<E> previous;
    public LinkedListNode<E> next;
    public E object;
    public long timestamp;


    public LinkedListNode() {
        this.previous = this;
        this.next = this;
    }

    public LinkedListNode(E object,LinkedListNode<E> previous, LinkedListNode<E> next) {
        if(next != null && previous != null){
            this.insert(next,previous);
        }

        this.object = object;
    }

    /**
     * 从链表中移除当前记录
     * @return
     */
    public LinkedListNode<E> remove(){
        this.next.previous = this.previous;
        this.previous.next = this.next;
        this.previous = this.next = null;
        return this;
    }

    /**
     * 插入链表
     * @param next
     * @param previous
     * @return
     */
    public LinkedListNode<E> insert(LinkedListNode<E> next,LinkedListNode<E> previous){
        this.next = next;
        this.previous = previous;
        this.previous.next = this.next.previous = this;
        return this;
    }

    public String toString() {
        return this.object == null ? "null" : this.object.toString();
    }
}
