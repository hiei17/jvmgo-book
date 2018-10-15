package com.github.jvmgo.rtda.heap.ref;

import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.util.MethodLookupUtil;
import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 22:42
 */
@Getter
public class InterfaceMethodRef {
    private ClassRef classRef;
    private String name;
    private String descriptor;

    private Method method;

    public InterfaceMethodRef(CClass useClass, String className, String name, String type) {
        classRef = new ClassRef(useClass, className);
        this.name = name;
        this.descriptor = type;
    }

    public Method resolveMethod() {
        if (method == null) {


            CClass fieldClass = classRef.resolveClass();
            if (!fieldClass.isInterface()) {
                throw new IncompatibleClassChangeError();

            }

            //找接口,接口的接口 总之 找到定义这个方法的地方
            Method method= MethodLookupUtil.lookupMethodInInterfaces(fieldClass, name, descriptor);

            if (method == null) {
                throw new NoSuchMethodError();
            }

            CClass userClass = classRef.getUserClass();
            if(!method.isAccessibleTo(userClass))  {
                throw new IllegalAccessError();
            }
        }
        return method;
    }


}
