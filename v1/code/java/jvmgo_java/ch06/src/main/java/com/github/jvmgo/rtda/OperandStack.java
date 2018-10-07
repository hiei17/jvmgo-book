package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.JObject;

/**
 * 操作数栈
 */
public class OperandStack {

    private int nextIndex =0;
    private Object[] objects;


        //操作数栈的大小是编译器已经确定的
    public OperandStack(int maxLocals) {
            objects =new Object[maxLocals];
        }

    public void push(Object o) {
        objects[nextIndex++]=o;
    }

    public void pushLorD(Object i) {
        objects[nextIndex++]= i;
        nextIndex++;
    }



    public Object pop() {
       return objects[--nextIndex];
    }

    public Object pop2() {
        Object slot = objects[nextIndex - 1];
        nextIndex-=2;
        return slot;
    }

    public JObject popRef() {
       return (JObject)pop();
    }

    public int popInt() {
        return (int) pop();
    }
    public float popFloat() {
        return (float) pop();
    }

    public long popLong() {
        return (long) pop2();
    }
    public double popDouble() {
        return (double) pop2();
    }

    @Override
    public String toString() {
        String s="[";
        for (int i = 0; i <nextIndex; i++) {


                s =s+objects[i]+",  ";


        }
        return s+"]";
    }
}
