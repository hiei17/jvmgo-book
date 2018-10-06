package com.github.jvmgo.instructions.comparisons.fcmp;


import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class FCMPL extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        FCMP._fcmp(frame, false);
    }
}