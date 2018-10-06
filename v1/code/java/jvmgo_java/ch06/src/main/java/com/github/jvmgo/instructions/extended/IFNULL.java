package com.github.jvmgo.instructions.extended;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IFNULL extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        Object ref = frame.getOperandStack().popRef();
        if (ref == null) {
            super.branch(frame);
        }
    }
}
