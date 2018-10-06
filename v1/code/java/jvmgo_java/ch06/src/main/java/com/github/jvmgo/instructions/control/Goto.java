package com.github.jvmgo.instructions.control;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class Goto extends BranchInstruction {
    @Override
    public void execute(Zframe frame) {
        super.branch(frame);
    }
}
