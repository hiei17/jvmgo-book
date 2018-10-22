package com.github.jvmgo.instructions.base;

import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 20:30
 */
/**
 * 类初始化 就是调用<clinit>()V方法
 * mark 就是代码里面  static{} 和 static变量赋值
 * 按代码的顺序执行 ,如果类变量写在这之后 那么能赋值 但是不能访问(使用
 * 必须初始化:
 * 1.new 和 static 相关指令时 会检查类有没有初始化 如果没有 会先执行这里初始化 才能继续执行
 * 2.反射
 * 3.子类类初始化,如果它不是接口,会触发父类先
 * 4.main 最开始就会类加载
 * 5.jdk7 支持动态语言的情况
 */

/**
 *
 * 特殊情况 要弄清:
 * 1. field 真正在的类 才会被初始化 比如: son.fatherField 只会初始化父类
 * 2. final static field(类常量) 编译时会入常量池, 它其实和原来定义它的类已经没关系了, 不会触发写它的那个类初始化
 * 3. new A类[]  这样A类的数组类被new  A类自己不会初始化
 * 4.多个线程都需要初始同个类,会只让一个执行 其他线程阻塞 mark 单例模式的饿汉就是用的这个特点
 */

/**
 * public class Demo {
 *
 *  static {
 *      i=0;//定义之前可赋值
 *      System.out.print1n(i);//这句报错 Illegal forward reference 定义之前不可访问
 *
 *  }
 *  static int i = 1;//相当于2句  定义:static int i ; 赋值 i=1;
 */

/**
 *public class Demo {
 *
 *  static {
 *      i = 0;//定义之前赋值没事
 *      System.out.println(i);//定义之前不可使用
 *      i=1;//定义之前赋值没事
 *  }
 *
 *  //定义
 *  static int i;
 *
 * }
 */
public class ClassInitLogic {

    //就是从最高父类开始 执行静态方法"<clinit>", "()V" 就是类的static块
    public static void init(Thread thread, CClass clazz) {
        clazz.initStarted=true;

        //本类
        Method initMethod= clazz.getClinitMethod();
        if(initMethod!=null){
            thread.pushFrame(initMethod);
        }

        //mark  接口不需要想执行父类
        if (clazz.isInterface()) {
            return;
        }

        //递归 都要先初始化父类
        CClass superClass = clazz.getSuperClass();
        if (superClass != null ) {
            init(thread, superClass);
        }

    }
}



