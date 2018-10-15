package com.github.jvmgo.instructions.comparisons.fcmp;


import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class FCMPG extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        FCMP._fcmp(frame, true);
    }
}
