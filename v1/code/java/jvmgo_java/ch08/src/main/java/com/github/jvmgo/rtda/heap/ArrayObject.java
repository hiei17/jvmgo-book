package com.github.jvmgo.rtda.heap;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:42
 */
public class ArrayObject extends JObject {


    public final int length;

    public ArrayObject(ArrayClass arrayClass, Object[] objects) {
        jClass=arrayClass;
        slots=objects;
        length=objects.length;
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
}
