package com.github.jvmgo.rtda.heap;

import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 13:53
 */
public class ClassRef {

    @Getter
    private JClass userClass;
    private String className;

    private JClass jClass;

    public ClassRef(JClass jclass, String className) {
        userClass=jclass;
        this.className=className;
    }

    public JClass resolveClass()  {

        if(jClass==null){

            MyClassLoader loader= userClass.classLoader;
            jClass= loader.loadClass(className);
            if (!jClass.isAccessibleTo(userClass)) {
                throw new IllegalAccessError();
            }
        }

        return jClass;
    }
}
