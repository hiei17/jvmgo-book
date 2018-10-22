package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.Index16Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.ArrayObject;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.ref.ClassRef;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 21:00
 * 创建引用类型数组
 * stack.push(new pop1[pop2])
 */
public class ANewarray extends Index16Instruction {


    @Override
    public void execute(Frame frame) {

        CClass componentClass = ((ClassRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index)).resolveClass();

        OperandStack operandStack = frame.getOperandStack();
        int arrayLength = operandStack.popInt();
        if (arrayLength<0){
            throw new NegativeArraySizeException();
        }

        //mark 注意 用componentClass 生成以它为元素的数组 componentClass不会触发类初始化
        ArrayClass arrClass = componentClass.arrayClass();

        ArrayObject arr = arrClass.newArray(arrayLength);
        operandStack.push(arr);
    }
}
