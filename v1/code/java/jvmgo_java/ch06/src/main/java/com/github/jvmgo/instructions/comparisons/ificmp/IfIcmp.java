package com.github.jvmgo.instructions.comparisons.ificmp;


import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IfIcmp {
    static int[] _icmpPop(Zframe frame) {
        OperandStack stack = frame.getOperandStack();
        int[] res = new int[2];
        res[1] = stack.popInt();
        res[0] = stack.popInt();
        return res;
    }
}
