package com.github.jvmgo.instructions.comparisons.ifcond;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IFNE extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        int val = frame.getOperandStack().popInt();
        if (val != 0) {
            super.branch(frame);
        }
    }
}
