package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;

/**
 * @Author: panda
 * @Date: 2018/10/15 0015 20:31
 */
public class ExceptionHandler {
    public int  startPc   ;
    public int  endPc     ;
    public int  handlerPc ;
    public ClassRef catchType ;


    public ExceptionHandler(CodeAttribute.ExceptionTableEntry e, RuntimeConstantPool runtimeConstantPool) {
        startPc=e.startPc;
        endPc=e.endPc;
        handlerPc=e.handlerPc;
        int catchType = e.catchType;

        //所有
        if(catchType!=0){

            this.catchType = (ClassRef) runtimeConstantPool.getConstant(catchType);
        }
    }

    public boolean canHandle(CClass exClass, int pc) {
        if(catchType==null){
            return true;
        }
        if(pc<startPc||pc>endPc){
            return false;
        }
        CClass cClass = catchType.resolveClass();
        return ClassHierarchyUtil.isSubClassOf(exClass,cClass )||exClass.equals(cClass);
    }
}
