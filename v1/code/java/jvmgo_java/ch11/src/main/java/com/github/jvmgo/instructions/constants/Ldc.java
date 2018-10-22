package com.github.jvmgo.instructions.constants;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.StringPool;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 20:30
 * 当前类的运行时常量池中取出常量 入栈
 */
public class Ldc extends Index16Instruction {

    private Boolean isIndexW;
    private Boolean isPushW;


    public Ldc(Boolean isIndexW, Boolean isPushW) {
        this.isIndexW = isIndexW;
        this.isPushW = isPushW;
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        if(!isIndexW) {
            index = reader.nextU1toInt();
        }else {
            super.fetchOperands(reader);
        }
    }

    @Override
    public void execute(Frame frame) {

        OperandStack operandStack = frame.getOperandStack();
        CClass clazz = frame.getMethod().getClazz();
        Object constant = clazz.getRuntimeConstantPool().getConstant(index);
        if (isPushW){
            operandStack.pushLorD(constant);
        }else {
            //如果这个常量是字符串 那么去字符串池拿
            if(constant instanceof String){
                //字符串池 也属于运行时常量池
                constant = StringPool.JString(clazz.classLoader,(String) constant);
            }
            //如果这个常量是类引用 那么它实际上要的是 这个类的 class对象
            else if(constant instanceof ClassRef){
                constant=((ClassRef)constant).resolveClass().jClass;
            }
            operandStack.push(constant);
        }
    }
}
