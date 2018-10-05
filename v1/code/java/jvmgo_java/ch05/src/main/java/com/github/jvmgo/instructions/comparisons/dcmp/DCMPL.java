package com.github.jvmgo.instructions.comparisons.dcmp;


import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:
 */
public class DCMPL extends NoOperandsInstruction {
    @Override
    public void execute(Zframe frame) {
        DCMP._dcmp(frame, false);
    }
}
