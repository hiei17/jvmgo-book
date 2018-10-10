package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.ArrayObject;
import com.github.jvmgo.rtda.heap.MyClassLoader;
import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 20:35
 * int[] a1 = new int[10];
 */
public class Newarray implements Instruction {

    private int atype;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        atype=reader.nextU1toInt();//数组类型
    }

     @Override
    public void execute(Frame frame) {
        OperandStack operandStack = frame.getOperandStack();
        int arrayLength = operandStack.popInt();
        if (arrayLength<0){
            throw new NegativeArraySizeException();
        }

        MyClassLoader classLoader = frame.getMethod().getJClass().classLoader;

       ArrayClass arrClass = (ArrayClass) classLoader.loadClass(covert2ArrayClassName(atype));

        ArrayObject arr = arrClass.newArray(arrayLength);
        operandStack.push(arr);
    }

    private String covert2ArrayClassName(int atype) {
        switch (atype) {
            case 4:
                return "[Z";
            case 8:
                return"[B";
            case 5:
                return"[C";
            case 9:
                return"[S";
            case 10:
                return"[I";
            case 11:
                return"[J";
            case 6:
                return"[F";
            case 7:
                return"[D";
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }
}
