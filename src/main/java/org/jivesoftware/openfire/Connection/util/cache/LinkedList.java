package org.jivesoftware.openfire.Connection.util.cache;

/**
 * 定义链表结构
 * @param <E>
 */
public class LinkedList<E> {

    private LinkedListNode<E> head = new LinkedListNode();

    public LinkedList() {
    }

    /**
     * 获得头节点的下一个节点
     * @return
     */
    public LinkedListNode<E> getFirst(){
        LinkedListNode<E> node = this.head.next;

        return node == this.head ? null:node;
    }

    /**
     * 获得头节点的前一个节点，由此判定，这是一个循环链表
     * @return
     */
    public LinkedListNode<E>getLast(){
        LinkedListNode<E>node = this.head.previous;
        return node == this.head ? null:node;
    }

    //插入头节点
    public LinkedListNode<E> addFirst(LinkedListNode<E> node) {
        return node.insert(this.head.next,this.head);
    }

    public LinkedListNode<E> addFirst(E object){
        return new LinkedListNode(object, this.head.next, this.head);
    }

    /**
     * 插入尾节点
     * @param node
     * @return
     */
    public LinkedListNode<E> addLast(LinkedListNode<E> node) {
        return node.insert(this.head, this.head.previous);
    }

    public LinkedListNode<E> addLast(E object) {
        return new LinkedListNode(object, this.head, this.head.previous);
    }

    /**
     * 清空链表
     */
    public void clear() {
        for(LinkedListNode node = this.getLast(); node != null; node = this.getLast()) {
            node.remove();
        }

        this.head.next = this.head.previous = this.head;
    }

    public String toString() {
        LinkedListNode<E> node = this.head.next;

        StringBuilder buf;
        for(buf = new StringBuilder(); node != this.head; node = node.next) {
            buf.append(node.toString()).append(", ");
        }

        return buf.toString();
    }
}
