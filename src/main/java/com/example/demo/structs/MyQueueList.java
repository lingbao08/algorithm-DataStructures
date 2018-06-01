package com.example.demo.structs;

import java.util.NoSuchElementException;

/**
 * @author 復靈
 * 2016年8月29日09:15:34
 * 该类讲述了一个队列的故事。队列以环形队列为例。
 * 以数组为实现基层。
 */
public class MyQueueList<AnyType> {

    private AnyType[] elementData;
    private int size;//队列的实际长度
    private int tempIndex = 0;
    //在内部用一个变量记录队尾；一个记录队伍
    private int head;//队首
    private int tail;//队尾

    //在创建队列时，必须指定固定的长度
    public MyQueueList(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("队列长度不合法");
        this.size = size;
        clear();
    }

    public int size() {
        if (tempIndex == 0)
            return 0;
        //队列的长度为（队尾的序号-队首的序号+size）%size
        return (tail - head + size) % size + 1;
    }

    //队列只能在队尾插入，在队首删除
    public boolean add(AnyType a) {
        if (size() == 0) {
            elementData[head] = a;
            tempIndex++;
            return true;
        }
        tail = (++tail) % size;
        elementData[tail] = a;
        //2016年8月29日13:52:09 这个if，不知道当队尾到达队首位置时，队列怎么办

        if (head == tail && (size() != 0)) {
            head = (++head) % size;
        }
        tempIndex++;
        return true;
    }

    public boolean remove() {
        //队列只能在队首删除！！！！
        //head位置上的原本的元素置为null，head后移一位
        elementData[head] = null;
        head++;
        int i = head;
        AnyType[] tempData = (AnyType[]) new Object[size];
        int k = 0;
        for (; i < size; i++) {
            tempData[k++] = elementData[i];
        }
        for (int j = 0; j < head; j++) {
            tempData[k++] = elementData[j];
        }
        System.arraycopy(tempData, 0, elementData, 0, tempData.length);
        head = 0;
        tail = elementData.length - 1;
        return true;
    }

    public boolean isFull() {
        return tempIndex >= size;
    }

    public AnyType get(int index) {
        if (index >= size() || index < 0)
            throw new ArrayIndexOutOfBoundsException();

        return elementData[index];
    }

    public int get(AnyType a) {
        int num = size << 1;
        for (int i = 0; i < size(); i++) {
            if (a.equals(elementData[i]) || (a == elementData[i])) {
                num = i;
            }
        }
        if (num >= size)
            throw new NoSuchElementException();
        return num;
    }

    public void clear() {
        elementData = (AnyType[]) new Object[size];
        head = tail = 0;
    }
}
