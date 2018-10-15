package com.github.jvmgo.rtda.heap;

import static com.github.jvmgo.rtda.heap.Access_flags.ACC_PUBLIC;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 11:35
 * 基本类型的类
 */

public class PrimitiveClass extends CClass {

    public PrimitiveClass(String primitiveClassName, MyClassLoader loader) {
        accessFlags=ACC_PUBLIC;
        //  void和基本类型的类名就是void、int、float等
        name=primitiveClassName;
        classLoader=loader;
        initStarted=true;

        //基本类型的类没有超类，也没有实现任何接口。
    }
}
