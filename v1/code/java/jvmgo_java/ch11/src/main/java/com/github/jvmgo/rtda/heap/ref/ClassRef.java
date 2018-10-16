package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.MyClassLoader;
import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 13:53
 */
public class ClassRef {

    @Getter
    private CClass userClass;
    private String className;

    private CClass clazz;

    public ClassRef(CClass clazz, String className) {
        userClass=clazz;
        this.className=className;
    }

    public CClass resolveClass()  {

        if(clazz==null){

            MyClassLoader loader= userClass.classLoader;
            clazz= loader.loadClass(className);
            if (!clazz.isAccessibleTo(userClass)) {
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
