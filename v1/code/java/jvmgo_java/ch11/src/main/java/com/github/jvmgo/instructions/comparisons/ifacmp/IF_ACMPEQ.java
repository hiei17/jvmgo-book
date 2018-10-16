package com.github.jvmgo.instructions.comparisons.ifacmp;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IF_ACMPEQ extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        if (IfAcmp._acmp(frame)) {
            super.branch(frame);
        }
    }
}
