package com.github.jvmgo.instructions.references.invoke;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.JObject;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.ref.MethodRef;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import com.github.jvmgo.rtda.heap.util.MethodLookupUtil;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:37
 * 调用无须动态绑定的实例方法:构造函数、私有方法、超类方法。
 * 就是无需根据this来决定方法
 */
public class Invokespecial extends Index16Instruction {

    @Override
    public void execute(Frame frame) {

        JClass currentClass = frame.getMethod().getJClass();
        MethodRef methodRef = (MethodRef) currentClass.getRuntimeConstantPool().getConstant(index);//指定的方法引用
        Method method = methodRef.resolveMethod();
        if (method.isStatic()){
            throw new IncompatibleClassChangeError();
        }

        JClass resolveClass = methodRef.getClassRef().resolveClass();
        JClass methodClass = method.getJClass();

        //1.构造方法<init> 类要匹配
        if("<init>".equals(method.getName())){
            if(!methodClass.equals(resolveClass)){
                throw new NoSuchMethodError();
            }
            MethodInvokeLogic.invoke(frame,method);
            return;
        }


        //从操作数栈中弹出this引用，如果该引用是null，抛NullPointerException异常。
       JObject thisObjectRef = (JObject) frame.getOperandStack().peekFromTop(method.getArgSlotCount() - 1);
        if( thisObjectRef == null ){
            throw new NullPointerException();
        }

        if( method.isProtected() &&//方法是protected
                ClassHierarchyUtil.isSubClassOf(currentClass,methodClass)&&//当前类是方法的类的子类
        !methodClass.getPackageName().equals(currentClass.getPackageName()) )//当前类和方法的类不同包
        {
            JClass objectRefJClass = thisObjectRef.getJClass();//实例的类
             if(!objectRefJClass.equals(currentClass) &&
                 !  ClassHierarchyUtil.isSubClassOf(objectRefJClass,currentClass) ){//如果不是当前类也不是当前类的子类

                    //子类在自己里面 或者在和父类同包时 才能调用父类方法protected
                    throw new IllegalAccessError();
             }
        }

        //2.super调用的父类方法 从父类开始向上找
        if (currentClass.IsSuper() &&//都是true
            ClassHierarchyUtil.isSubClassOf(currentClass,resolveClass) //指明要调用父类及以上的  super
         ){

            //往上找
            Method  methodToBeInvoked = MethodLookupUtil.lookupMethodInClass(
                    currentClass,
                    methodRef.getName(),
                    methodRef.getDescriptor());
            if(methodToBeInvoked==null||methodToBeInvoked.isAbstract()){
                throw new AbstractMethodError();
            }
            MethodInvokeLogic.invoke(frame,methodToBeInvoked);
            return;
        }

        //3.private方法
        MethodInvokeLogic.invoke(frame,method);
    }


}
