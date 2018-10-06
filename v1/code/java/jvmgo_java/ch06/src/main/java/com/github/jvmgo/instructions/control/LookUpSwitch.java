package com.github.jvmgo.instructions.control;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc:如果case值不可以编码成一个索引表(case中的数值不是连续的)，则实现成lookupswitch指令
 */
public class LookUpSwitch extends BranchInstruction {

    int npairs;
    //matchOffsets有点像Map，它的key是case值，value是跳转偏移,但是并没有实现成map,而是用数组代替,两个连续的数位key-value;
    int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();

        //default
        super.offSet= reader.nextU4ToInt();

        npairs = reader.nextU4ToInt();
        matchOffsets = reader.nextUint32s(npairs * 2);
    }

    @Override
    public void execute(Zframe frame) {

        //case
        int key = frame.getOperandStack().popInt();
        for (int i = 0; i < npairs * 2; i += 2) {

            if (matchOffsets[i] == key) {
                super.offSet=matchOffsets[i + 1];
               break;
            }
        }

        super.branch(frame);
    }
}
