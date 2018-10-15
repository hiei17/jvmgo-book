package com.github.jvmgo.instructions.comparisons.dcmp;


import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 由于浮点数计算有可能产生NaN（Not a Number）值，所以比较两个浮点数时，除了大于、等于、小于之外， 还有第4种结果：无法比较
 * fcmpg和fcmpl指令的区别就在于对第4种结果的定义;
 * 当两个float变量中至少有一个是NaN时，用fcmpg指令比较的结果是1，而用fcmpl指令比较的结果是-1。
 */
public class DCMP {
    static void _dcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        double val2 = stack.popDouble();
        double val1 = stack.popDouble();

        if (val1 > val2) {
            stack.push(1);
        } else if (val1 == val2) {
            stack.push(0);
        } else if (val1 > val2) {
            stack.push(-1);
        } else if (flag) {
            stack.push(1);
        } else {
            stack.push(-1);
        }

    }
}
