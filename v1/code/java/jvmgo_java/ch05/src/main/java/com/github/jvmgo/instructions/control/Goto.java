package com.github.jvmgo.instructions.control;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class Goto extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        super.branch(frame);
    }
}
