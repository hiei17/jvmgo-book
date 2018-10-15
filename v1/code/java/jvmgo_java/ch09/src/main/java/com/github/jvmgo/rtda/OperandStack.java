package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.OObject;

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
        nextIndex-=2;
        return objects[nextIndex ];
    }

    public OObject popRef() {
       return (OObject)pop();
    }

    public int popInt() {
        Object pop = pop();
        if(pop instanceof Boolean){
            if((Boolean) pop){
                return 1;
            }else {
                return 0;
            }
        }
        if(pop==null){
            return 0;
        }
        return (int) pop;
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

    public Object peekFromTop(int index) {
        return objects[nextIndex-index-1];
    }
}
