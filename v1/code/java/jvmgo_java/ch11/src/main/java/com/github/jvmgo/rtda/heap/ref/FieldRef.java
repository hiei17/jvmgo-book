package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.Field;
import com.github.jvmgo.rtda.heap.CClass;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 22:42
 */
public class FieldRef {

    //符号引用
    private ClassRef classRef;
    private String name;
    private String descriptor;

    //虚拟机对第一次解析结果缓存
    private Field field;

    public FieldRef(CClass useClass, String className, String name, String type) {
        classRef = new ClassRef(useClass, className);
        this.name = name;
        this.descriptor = type;
    }

    //mark 连接3 解析
    //符号引用 转为直接引用
    public Field resolveField() {
        if (field == null) {


            try {
                CClass fieldClass = classRef.resolveClass();

                //对jvm来说 只要描述和名字 有个不同 就是不同的field  也能重载 这个和java不同
                Field field = lookupField(fieldClass, name, descriptor);

                if (field == null) {
                    throw new NoSuchFieldError();

                }
                if (!field.isAccessibleTo(classRef.getUserClass())) {
                    throw new IllegalAccessError();
                }

                return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return field;
    }

    private Field lookupField(CClass fieldClass, String name, String descriptor) {


        //自己
        Field[] fields = fieldClass.getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                    return field;
                }
            }

        }

        //接口
        CClass[] interfaces = fieldClass.getInterfaces();
        if (interfaces != null) {

            for (CClass anInterface : interfaces) {
                Field field = lookupField(anInterface, name, descriptor);
                if (field != null) {
                    return field;
                }
            }
        }

        //父类
        CClass superClass = fieldClass.getSuperClass();
        if (superClass != null) {
            Field field = lookupField(superClass, name, descriptor);
            if (field != null) {
                return field;
            }

        }

        return null;
    }
}
