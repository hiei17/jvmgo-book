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

    //符号引用
    private ClassRef classRef;
    private String name;
    private String descriptor;

    //虚拟机对第一次解析结果缓存
    private Method method;

    public InterfaceMethodRef(CClass useClass, String className, String name, String type) {
        classRef = new ClassRef(useClass, className);
        this.name = name;
        this.descriptor = type;
    }

    //mark 连接3 解析
    //符号引用 转为直接引用
    public Method resolveMethod() {
        if (method == null) {


            CClass methodInClass = classRef.resolveClass();
            if (!methodInClass.isInterface()) {
                throw new IncompatibleClassChangeError();

            }

            //找接口,接口的接口 总之 找到定义这个方法的地方
            method= MethodLookupUtil.lookupMethodInInterfaces(methodInClass, name, descriptor);

            if (method == null) {
                throw new NoSuchMethodError(name+" "+descriptor);
            }

            CClass userClass = classRef.getUseClass();
            if(!method.isAccessibleTo(userClass))  {
                throw new IllegalAccessError();
            }
        }
        return method;
    }


}
