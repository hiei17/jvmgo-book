package com.github.jvmgo.rtda.heap;

import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 22:42
 */
@Getter
public class MethodRef {
   private ClassRef classRef;
   private String name       ;
    private String descriptor ;

  private   Method method;

    public MethodRef(JClass useClass, String className, String name, String type) {
        classRef=new ClassRef(useClass,className);
        this.name=name;
        this.descriptor=type;
    }

    public Method resolveMethod(){
        if (method==null){


            try {
                JClass fieldClass = classRef.resolveClass();



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method;
    }


}
