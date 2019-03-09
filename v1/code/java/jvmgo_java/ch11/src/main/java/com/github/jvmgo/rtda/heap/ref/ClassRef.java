package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.MyClassLoader;
import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 13:53
 */
public class ClassRef {

    //符号引用
    @Getter
    private CClass useClass;
    private String className;

    //虚拟机对第一次解析结果缓存
    private CClass clazz;

    /**
     *
     * @param clazz 写这本应用的.class
     */
    public ClassRef(CClass clazz, String className) {
        useClass =clazz;
        this.className=className;
    }

    //mark 连接3 解析
    //符号引用 转为直接引用
    public CClass resolveClass()  {

        if(clazz==null){

            MyClassLoader loader= useClass.classLoader;
            clazz= loader.loadClass(className);
            if (!clazz.isAccessibleTo(useClass)) {
                throw new IllegalAccessError();
            }
        }

        return clazz;
    }

    @Override
    public String toString() {
        return  className ;

    }
}
