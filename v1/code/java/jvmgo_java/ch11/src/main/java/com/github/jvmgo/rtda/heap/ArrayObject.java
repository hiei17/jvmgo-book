package com.github.jvmgo.rtda.heap;

import java.util.Arrays;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:42
 */
public class ArrayObject extends OObject {


    public final int length;

    public ArrayObject(ArrayClass arrayClass, Object[] objects) {
        clazz =arrayClass;
        slots=objects;
        length=objects.length;
    }

    public static void copy(ArrayObject src, ArrayObject dest, int srcPos, int destPos, int length) {
        Object[] srcSlots = src.slots;
        Object[] destSlots = dest.slots;
        for (int i = srcPos, j=destPos; i < srcPos+length; i++,j++) {
            destSlots[j]=srcSlots[i];
        }
    }

    public Object get(int index) {

        if(index>length-1){
           throw new  ArrayIndexOutOfBoundsException();
        }
        return slots[index];
    }

    public void set(int index, Object data) {

        if(index>length-1){
            throw new  ArrayIndexOutOfBoundsException();
        }
       slots[index]=data;
    }

    @Override
    public String toString() {
        return Arrays.toString(slots);
    }
}
