package com.github.jvmgo.instructions.base;

import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 20:30
 */

/**
 * 类初始化 就是调用<clinit>()V方法
 * mark 就是原来static{}
 * 必须初始化:
 * 1.new 和 static 相关指令时 会检查类有没有 如果没有 会先执行这里初始化 才能继续执行
*  2. 反射
 * 3.子类类初始化,会触发父类先
 * 4.main 最开始就会类加载
 *
 * 5.jdk7 支持动态语言的情况
 */

/**
 *
 * 特殊情况 要弄清:
 * 1. field 真正在的类 才会被初始化 比如: son.fatherField 只会初始化父类
 * 2. final static field(类常量) 编译时会入常量池, 它其实和原来定义它的类已经没关系了, 不会触发写它的那个类初始化
 * 3. new A类[]  这样A类的数组类被new  A类自己不会初始化
 */
public class ClassInitLogic {
    public static void init(Thread thread, JClass jClass) {
        jClass.initStarted=true;

        if (jClass.isInterface()) {
            return;
        }

        //本类
        Method initMethod= jClass.getClinitMethod();
        if(initMethod!=null){
            thread.pushFrame(initMethod);
        }

        //递归 都要先初始化父类
        JClass superClass = jClass.getSuperClass();
        if (superClass != null ) {
            init(thread, superClass);
        }

    }
}



