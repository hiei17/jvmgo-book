package com.github.jvmgo.instructions.comparisons.fcmp;


import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 由于浮点数计算有可能产生NaN（Not a Number）值，所以比较两个浮点数时，除了大于、等于、小于之外， 还有第4种结果：无法比较
 * fcmpg和fcmpl指令的区别就在于对第4种结果的定义;
 * 当两个float变量中至少有一个是NaN时，用fcmpg指令比较的结果是1，而用fcmpl指令比较的结果是-1。
 */
public class FCMP {

    static void _fcmp(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        float val2 = stack.popFloat();
        float val1 = stack.popFloat();

        if (val1 > val2) {
            stack.push(1);
        } else if (val1 == val2) {
            stack.push(0);
        } else if (val1 < val2) {
            stack.push(-1);
        } else if (flag) {
            stack.push(1);
        } else {
            stack.push(-1);
        }

    }
}
