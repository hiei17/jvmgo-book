package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.util.MethodLookupUtil;
import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 22:42
 */
@Getter
public class MethodRef {
    private ClassRef classRef;//引用时指定的类
    private String name;
    private String descriptor;

    private Method method;//方法缓存 调用的不一定是它

    public MethodRef(JClass useClass, String className, String name, String type) {
        classRef = new ClassRef(useClass, className);
        this.name = name;
        this.descriptor = type;
    }

    public Method resolveMethod() {
        if (method == null) {


            JClass fieldClass = classRef.resolveClass();
            if (fieldClass.isInterface()) {
                throw new IncompatibleClassChangeError();

            }

            //先继承链往上找,再找接口,
            method= lookupMethod(fieldClass, name, descriptor);

            if (method == null) {
                throw new NoSuchMethodError();
            }

            JClass userClass = classRef.getUserClass();
            if(!method.isAccessibleTo(userClass))  {
                throw new IllegalAccessError();
            }
        }

        //最终被调用的不一定是它
        return method;
    }

    private  Method lookupMethod(JClass jClass, String name, String descriptor) {
        //沿着继承链往上找
        Method method = MethodLookupUtil.lookupMethodInClass(jClass, name, descriptor);

        if(method == null) {
            //接口里面找
            method =MethodLookupUtil.lookupMethodInInterfaces(jClass, name, descriptor);
        }

        return method;
    }

    @Override
    public String toString() {
        return
                classRef +" "+ name + descriptor ;

    }
}
