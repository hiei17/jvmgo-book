package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.Field;
import com.github.jvmgo.rtda.heap.JClass;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 22:42
 */
public class FieldRef {
   private ClassRef classRef;
   private String name       ;
    private String descriptor ;

  private Field field;

    public FieldRef(JClass useClass, String className, String name, String type) {
        classRef=new ClassRef(useClass,className);
        this.name=name;
        this.descriptor=type;
    }

    public Field resolveField(){
        if (field==null){


            try {
                JClass fieldClass = classRef.resolveClass();

                //对jvm来说 只要描述和名字 有个不同 就是不同的field  也能重载 这个和java不同
                Field field = lookupField(fieldClass, name, descriptor);

                if (field == null ){
                    throw new NoSuchFieldError();

                }
                if (!field.isAccessibleTo(classRef.getUserClass()) ){
                    throw new IllegalAccessError();
                }

                return field;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return field;
    }

    private Field lookupField(JClass fieldClass, String name, String descriptor) {
        for (Field field : fieldClass.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor) ){
                return field;
            }
        }
        for (JClass anInterface : fieldClass.getInterfaces()) {
            Field field= lookupField( anInterface,  name,  descriptor);
            if (field!=null){
                return field;
            }
        }

        JClass superClass = fieldClass.getSuperClass();

        Field field= lookupField( superClass,  name,  descriptor);
        if (field!=null){
            return field;
        }

        return null;
    }
}
