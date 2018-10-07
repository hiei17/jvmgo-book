package com.github.jvmgo.instructions.extended;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IFNONNULL extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        Object ref = frame.getOperandStack().popRef();
        if (ref != null) {
            super.branch(frame);
        }
    }
}
