package com.github.jvmgo;

/**
 * @Author: panda
 */
public class Demo {
    static {
        i = 0;
        //Illegal forward reference
      //  System.out.println(i);
    }
    static int i = 1;
}