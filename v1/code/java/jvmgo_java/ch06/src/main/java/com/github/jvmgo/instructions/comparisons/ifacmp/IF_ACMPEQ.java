package com.github.jvmgo.instructions.comparisons.ifacmp;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class IF_ACMPEQ extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        if (IfAcmp._acmp(frame)) {
            super.branch(frame);
        }
    }
}
