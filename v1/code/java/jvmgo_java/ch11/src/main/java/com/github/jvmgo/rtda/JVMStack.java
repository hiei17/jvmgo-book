package com.github.jvmgo.rtda;

import java.util.Arrays;

/**
 和堆一样，Java虚拟机规范对Java虚拟机栈的约束也相当宽
 松。Java虚拟机栈可以是连续的空间，也可以不连续；可以是固定大
 小，也可以在运行时动态扩展  。
 如果Java虚拟机栈有大小限制，执行线程所需的栈空间超出了这个限制，会导致StackOverflowError异常抛出。
 如果Java虚拟机栈可以动态扩展，但是内存已经耗尽，会导致OutOfMemoryError异常抛出。

 这个实现有大小限制
 */
public class JVMStack {

    private final static int maxSize=1024;
    private  int index =-1;
    private Frame[] stackFrames =new Frame[maxSize];


    public void push(Frame frame) {
        if (index-1 >=maxSize){
            throw  new StackOverflowError();
        }
        stackFrames[++index]=frame;

    }

    public Frame pop() {
        Frame result = stackFrames[index];
        stackFrames[index--]=null;
        return result;
    }

    public Frame top() {
        return stackFrames[index];
    }

    public boolean isEmpty() {
        return index==-1;
    }

    @Override
    public String toString() {
        String s="[";
        for (int i = 0; i <= index; i++) {
            s=s+ stackFrames[i]+",";

        }
        s+="]";
        return s;
    }

    public void clearn() {
        index=-1;
    }

    public Frame[] getFrames(int skip) {
        Frame[] frames = Arrays.copyOfRange(stackFrames, 0, index+1 - skip);

       return rervers(frames);
    }

    private Frame[] rervers(Frame[] frames) {
        Frame[] a=new Frame[frames.length];
        for (int i = frames.length-1; i !=-1 ; i--) {
            a[frames.length-1-i] = frames[i];
        }
        return a;
    }
}
