package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.rtda.heap.util.ClassNameHelper;

import static com.github.jvmgo.rtda.heap.Access_flags.ACC_PUBLIC;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 20:47
 */
public class ArrayClass extends JClass {


    /**
     * 普通的类从class文件中加载，但是数组类由Java虚拟机在运行时生成。
     * @param arrayClassName 数组类名 如 [java/lang/String
     * @param myClassLoader 类加载器
     */
    public ArrayClass(String arrayClassName,MyClassLoader myClassLoader) {
        super();
        name=arrayClassName;
        classLoader=myClassLoader;

        //数组类同一的 都是这些↓
        accessFlags=ACC_PUBLIC;
        initStarted=true;
        superClass=myClassLoader.loadClass("java/lang/Object");
        interfaces=new JClass[2];
        interfaces[0]=myClassLoader.loadClass("java/lang/Cloneable");
        interfaces[1]=myClassLoader.loadClass("java/io/Serializable");
    }


    public ArrayObject newArray(int count) {

        switch (name) {
           /* case "[Z": return new ArrayObject(this, new Integer[count]);
            case "[B": return new ArrayObject(this, new Boolean[count]);
            case "[C": return new ArrayObject(this, new Integer[count]);
            case "[S": return new ArrayObject(this, new Integer[count]);
            case "[I": return new ArrayObject(this, new Integer[count]);
            case "[J": return new ArrayObject(this, new Integer[count]);
            case "[F": return new ArrayObject(this, new Float[count]);
            case "[D": return new ArrayObject(this, new Double[count]);*/
            default: return new ArrayObject(this, new Object[count]);
        }
    }

    private String componentClassName;
    public JClass componentClass() {
        if(componentClassName==null) {
            componentClassName = ClassNameHelper.getComponentClassNameFromArrayClassName(name);
        }
      return classLoader.loadClass(componentClassName);
    }
}
