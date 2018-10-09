package com.github.jvmgo.instructions.extended;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.heap.JObject;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class IFNONNULL extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        JObject ref = frame.getOperandStack().popRef();
        if (ref != null) {
            super.branch(frame);
        }
    }
}
