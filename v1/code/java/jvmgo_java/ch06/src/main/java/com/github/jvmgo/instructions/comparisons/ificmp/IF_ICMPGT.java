package com.github.jvmgo.instructions.comparisons.ificmp;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IF_ICMPGT extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        int[] res = IfIcmp._icmpPop(frame);
        int val1 = res[0];
        int val2 = res[1];
        if (val1 > val2) {
            super.branch(frame);
        }
    }
}
