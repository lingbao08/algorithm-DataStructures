package com.example.demo.structs;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * @author 復靈
 * 2016年8月28日11:53:49
 * 本类是手动编写的，是一个自己编写的linkedList链表类
 */

public class MyLinkedList<AnyType> implements Iterable<AnyType> {

    private int size;
    private Node<AnyType> first;
    private Node<AnyType> last;
    private int modCount;

    public int size(){
        return size;
    }

    public boolean add(AnyType a){
        addLast(a);
        return true;
    }

    public boolean addLast(AnyType a){
        add(size(),a);
        return true;

    }
    public void add(int index,AnyType a){
        //获取最后一个节点，并将最后一个节点作为新节点的前一个节点，新节点的后一个节点为null，然后将新节点赋值给last
        Node<AnyType> l = last;
        Node<AnyType> newNode = new Node<>(a, l, null);
        last = newNode;
        if(index==0)
            first = newNode;
        else
            l.next = newNode;
        size++;
        modCount++;
    }
    /**
     * 在节点n前增加元素a
     */
    private void addBefore(Node<AnyType> n,AnyType a){
        //构造一个新节点，在新节点的构造中，newNode的前一个节点已经为n.prev，newNode的后一个节点为n。
        //将newNode的前一个节点元素的后一个指针指向newNode；
        //将n的前一个节点指向newNode。
        Node<AnyType> newNode =  new Node<AnyType>(a, n.prev,n);
        newNode.prev.next = newNode;
        n.prev = newNode;
        size++;
        modCount++;
    }

    public boolean remove(int index){
        Node<AnyType> node = getNode(index);
        remove(node);
        return true;
    }

    public void remove(Node<AnyType> node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        modCount++;
    }


    private Node<AnyType> getNode(int index){
        if(index<0 || index>size()){
            throw new ArrayIndexOutOfBoundsException(index);
        }

        if(index<(size>>1)){
            Node<AnyType> n = first;
            for(int i=0;i<index;i++){
                n = n.next;
            }
            return n;
        }else{
            Node<AnyType> n = last;
            for(int i=0;i>index;i--){
                n = n.prev;
            }
            return n;
        }
    }


    @Override
    public Iterator<AnyType> iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator<AnyType>{

        private Node<AnyType> current = first;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        private Node<AnyType> lastReturned;
        private int nowIndex=0;
        @Override
        public boolean hasNext() {
            //当前下标小于size长度
            return nowIndex<size;
        }

        @Override
        public AnyType next() {
            if(modCount!=expectedModCount)
                throw new ConcurrentModificationException();
            if(!hasNext())
                throw new ArrayIndexOutOfBoundsException();

            lastReturned = current;
            AnyType item = lastReturned.item;
            current = current.next;
            okToRemove = true;
            nowIndex++;
            return item;
        }

        @Override
        public void remove() {
            if(modCount!=expectedModCount)
                throw new ConcurrentModificationException();
            if(!okToRemove)
                throw new IllegalArgumentException("状态异常，不能进行删除！");
            MyLinkedList.this.remove(lastReturned);
            expectedModCount++;
            nowIndex--;
        }

    }

    private static class Node<AnyType> {
        private AnyType item;//
        private Node<AnyType> prev;//上一个节点
        private Node<AnyType> next;//下一个节点
        public Node(AnyType item, Node<AnyType> prev, Node<AnyType> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}