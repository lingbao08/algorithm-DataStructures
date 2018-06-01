package com.example.demo.structs;

import java.util.Iterator;

/**
 * 2016年8月22日13:04:43
 *
 * @author 復靈
 * @version 1.0
 * 一个手工的数组表
 */

public class MyArrayList<AnyType> implements Iterable<AnyType> {

    //初始默认容量
    private static final Integer DEFAULT_CAPACITY = 10;
    //数组表长度
    private int size;
    private AnyType[] array;

    public MyArrayList() {
        clear();
    }

    public int size() {
        return size;
    }

    /**
     * @param newSize 指定的新长度
     */
    public void ensureCapacity(int newSize) {
        if (newSize < size) {
            return;
        }
        Object[] arrayTemp = new Object[newSize];
        for (int i = 0; i < size; i++) {
            arrayTemp[i] = array[i];
        }
        array = (AnyType[]) arrayTemp;
        size = newSize;
    }

    public boolean add(AnyType t) {
        return add(size(), t);
    }

    /**
     * @param index 要增加的元素的索引
     * @param t     要增加的元素
     */
    public boolean add(int index, AnyType t) {
        //先扩容，再复制
        if (index >= size)
            ensureCapacity(size + 1);
        for (int i = index; i < size; i++) {
            if (i == index)
                array[i] = t;
            else
                array[i] = array[i + 1];
        }
        return true;
    }

    /**
     * @param index 要删除元素的索引
     */
    public boolean remove(int index) {
        //2016年8月22日16:14:56  未完
        if (index < size()) {
            for (int j = index; j < size() - 1; ) {
                array[j] = array[++j];
            }
            size--;
            return true;
        }
        return false;
    }

    public void clear() {
        array = (AnyType[]) new Object[0];
        size = 0;
    }

    @Override
    public Iterator<AnyType> iterator() {
        return new ArrayListIterator<AnyType>();
    }

    private class ArrayListIterator<AnyType> implements Iterator<AnyType> {

        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public AnyType next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return (AnyType) array[currentIndex++];
        }

        @Override
        public void remove() {
            //这里要用先减一，再调用方法。以便于后边的移动直接将前面的覆盖掉。
            MyArrayList.this.remove(--currentIndex);
        }

    }

}