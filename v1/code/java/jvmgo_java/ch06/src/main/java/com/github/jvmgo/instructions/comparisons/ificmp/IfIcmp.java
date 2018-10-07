package com.github.jvmgo.instructions.comparisons.ificmp;


import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IfIcmp {
    static int[] _icmpPop(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int[] res = new int[2];
        res[1] = stack.popInt();
        res[0] = stack.popInt();
        return res;
    }
}
