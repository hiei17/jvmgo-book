package com.github.jvmgo.instructions.comparisons.ifcond;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IFGT extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.getOperandStack().popInt();
        if (val > 0) {
            super.branch(frame);
        }
    }
}
