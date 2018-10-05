package com.github.jvmgo.rtda;

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
    private  int size=-1;
    private Zframe[] stackFrames =new Zframe[maxSize];


    public void push(Zframe frame) {
        if (size>=maxSize){
            throw  new StackOverflowError();
        }
        stackFrames[++size]=frame;

    }

    public Zframe pop() {
        return stackFrames[size--];
    }

    public Zframe top() {
        return stackFrames[size];
    }
}
