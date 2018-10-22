package com.github.jvmgo.instructions.references.invoke;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.ref.MethodRef;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import com.github.jvmgo.rtda.heap.util.MethodLookupUtil;



/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:39
 */
public class Invokevirtual extends Index16Instruction {

    @Override
    public void execute(Frame frame) {


        CClass currentClass = frame.getMethod().getClazz();
        MethodRef methodRef = (MethodRef) currentClass.getRuntimeConstantPool().getConstant(index);//指定的方法引用
        Method method = methodRef.resolveMethod();
        if (method.isStatic()){
            throw new IncompatibleClassChangeError();
        }

        OObject thisObjectRef = (OObject) frame.getOperandStack().peekFromTop(method.getArgSlotCount() - 1);
        if( thisObjectRef == null ){

            //hack
           /* if ((methodRef.getName().indexOf("print")>-1) ){
                println(frame.getOperandStack(), methodRef.getDescriptor());
                return;
            }*/
            throw new NullPointerException();
        }
        CClass methodClass = method.getClazz();

        if( method.isProtected() &&//方法是protected
                ClassHierarchyUtil.isSubClassOf(currentClass,methodClass)&&//当前类是方法的类的子类
                !methodClass.getPackageName().equals(currentClass.getPackageName()) )//当前类和方法的类不同包
        {
            CClass objectRefclazz = thisObjectRef.getClazz();//实例的类
            if(!objectRefclazz.equals(currentClass) &&
                    !  ClassHierarchyUtil.isSubClassOf(objectRefclazz,currentClass) ){//如果不是当前类也不是当前类的子类

                //子类在自己里面 或者在和父类同包时 才能调用父类方法protected
                throw new IllegalAccessError();
            }

        }

        //根据this的class 找到真正要调用的方法
        Method  methodToBeInvoked = MethodLookupUtil.lookupMethodInClass(
                thisObjectRef.getClazz(),
                methodRef.getName(),
                methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        MethodInvokeLogic.invoke(frame, methodToBeInvoked);
    }


}
