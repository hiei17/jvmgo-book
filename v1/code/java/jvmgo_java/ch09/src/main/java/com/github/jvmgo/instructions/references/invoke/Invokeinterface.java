package com.github.jvmgo.instructions.references.invoke;

import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.ref.InterfaceMethodRef;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import com.github.jvmgo.rtda.heap.util.MethodLookupUtil;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 18:03
 */
public class Invokeinterface implements Instruction {
    private int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {

        index=reader.nextU2ToInt();
        /*第3字节的 值是给方法传递参数需要的slot数，其含义和给Method结构体定义
        的argSlotCount字段相同。正如我们所知，这个数是可以根据方法描
        述符计算出来的，它的存在仅仅是因为历史原因。*/
      int slotCount=  reader.nextU1toInt();
        //第4字节是留给 Oracle的某些Java虚拟机实现用的，它的值必须是0。
        int i = reader.nextU1toInt();


    }

    @Override
    public void execute(Frame frame) {
        CClass currentClass = frame.getMethod().getClazz();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) currentClass.getRuntimeConstantPool().getConstant(index);//指定的方法引用
        Method method = methodRef.resolveMethod();
      if(method.isStatic()||method.isPrivate()){
          throw new IncompatibleClassChangeError();
      }

        OObject thisObjectRef = (OObject) frame.getOperandStack().peekFromTop(method.getArgSlotCount() - 1);
        if( thisObjectRef == null ){
            throw new NullPointerException();
        }
        CClass thisObjectClass = thisObjectRef.getClazz();
        CClass interfaceClass = methodRef.getClassRef().resolveClass();

        if(!ClassHierarchyUtil.isImplements(thisObjectClass,interfaceClass)){
            throw new IncompatibleClassChangeError();
        }


       Method methodToBeInvoked = MethodLookupUtil.lookupMethodInClass(thisObjectClass,methodRef.getName(),
                methodRef.getDescriptor());

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
           throw new AbstractMethodError();
        }

        if (!methodToBeInvoked.isPublic() ){
            throw new IllegalAccessError();

        }

        MethodInvokeLogic.invoke(frame, methodToBeInvoked);
    }
}
