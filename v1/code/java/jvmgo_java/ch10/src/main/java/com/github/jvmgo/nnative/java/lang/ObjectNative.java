package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 13:13
 *
 * java.lang.Object里的本地方法
 */
public class ObjectNative {
   static final String  jlObject = "java/lang/Object";


    public static void init() {

        //java.lang.Object.getClass（）
        NativeMethod.register(jlObject, "getClass", "()Ljava/lang/Class;", getClass);
        NativeMethod.register(jlObject, "hashCode", "()I", hashCode);
        NativeMethod.register(jlObject, "clone", "()Ljava/lang/Object;", clone);
    }

    //this的对应class对象入栈  这样所有类都能用 类名.getClass()来得到对应class对象了
  private static   NativeMethod getClass= frame->{
        OObject thisObject = frame.getLocalVars().getRef(0);
        OObject classObject = thisObject.getClazz().jClass;
        frame.getOperandStack().push(classObject);
    };
    private static    NativeMethod hashCode=frame -> {
        OObject thisObject = frame.getLocalVars().getRef(0);
        frame.getOperandStack().push(thisObject.hashCode());//本来该是本地方法转
    };

    private static    NativeMethod clone=frame -> {
        OObject thisObject = frame.getLocalVars().getRef(0);
        CClass thisClazz = thisObject.getClazz();
        CClass cloneClass = thisClazz.classLoader.loadClass("java/lang/Cloneable");
        if(!ClassHierarchyUtil.isImplements(thisClazz,cloneClass)){
            throw new RuntimeException("CloneNotSupportedException");
        }

        frame.getOperandStack().push(cloneObject(thisObject));
    };


    private static OObject cloneObject(OObject thisObject) {
        OObject cloneObject=new OObject(thisObject.getClazz());
        Object[] slots = thisObject.slots;
        cloneObject.slots=new Object[slots.length];
        System.arraycopy(slots, 0, cloneObject.slots, 0, slots.length);
        return cloneObject;
    }


}
