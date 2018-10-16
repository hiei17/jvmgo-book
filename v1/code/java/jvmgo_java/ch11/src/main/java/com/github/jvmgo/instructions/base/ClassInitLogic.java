package com.github.jvmgo.instructions.base;

import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 20:30
 */
public class ClassInitLogic {
    public static void init(Thread thread, CClass clazz) {
        clazz.initStarted=true;

        if (clazz.isInterface()) {
            return;
        }

        //本类
        Method initMethod= clazz.getClinitMethod();
        if(initMethod!=null){
            thread.pushFrame(initMethod);
        }

        //递归 都要先初始化父类
        CClass superClass = clazz.getSuperClass();
        if (superClass != null ) {
            init(thread, superClass);
        }

    }
}



