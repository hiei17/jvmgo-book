package com.github.jvmgo.instructions.references.array;

import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.ArrayObject;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.util.BytecodeReader;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: panda
 * @Date: 2018/10/10 0010 22:03
 * 创建多维数组
 */
public class Multianewarray implements Instruction {
    private int index;//找到数据类
    private int dimensions;//维度 比如 int[][][]是3

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.nextU2ToInt();
        dimensions = reader.nextU1toInt();
    }

    @Override
    public void execute(Frame frame) {
        //index去运行时常量池→拿到类引用→拿到数组类
        //注意 :解析出来的直接就是数组类
        ArrayClass arrayClass = (ArrayClass) ((ClassRef) frame.getMethod().getClazz().getRuntimeConstantPool().getConstant(index)).resolveClass();

        OperandStack operandStack = frame.getOperandStack();
        Queue<Integer>  lens= popAndCheckCounts(operandStack,dimensions);


        ArrayObject arr = newMultiDimensionalArray(lens, arrayClass);
        operandStack.push(arr);
    }


    private ArrayObject newMultiDimensionalArray(Queue<Integer> lens, ArrayClass arrayClass) {

        ArrayClass componentClass =(ArrayClass)arrayClass.componentClass();
        int rowCount = lens.poll();
        //最外层数组
        ArrayObject arr = arrayClass.newArray(rowCount);

        while (!lens.isEmpty()){
            for (int i = 0; i < rowCount; i++) {
               //最外层多维数组的每个元素都是 多维数组 递归
                arr.set(i,newMultiDimensionalArray(lens,componentClass));
            }
        }
        return arr;
    }

    private Queue<Integer> popAndCheckCounts(OperandStack operandStack, int dimensions) {
        LinkedList<Integer> lens = new LinkedList<>();

        while (dimensions>0){
            int len = operandStack.popInt();
            if(len<0){
                throw new NegativeArraySizeException();
            }
            lens.add(len);
            dimensions--;
        }

        return lens;
    }
}
