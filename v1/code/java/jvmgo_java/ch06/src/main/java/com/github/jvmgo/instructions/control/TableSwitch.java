package com.github.jvmgo.instructions.control;


import com.github.jvmgo.instructions.base.BranchInstruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.util.BytecodeReader;

/**
 * Author: zhangxin
 * Time: 2017/5/5 0005.
 * Desc: 如果case值可以编码成一个索引表(case中的数值是连续的)，则实现成tableswitch指令
 */
public class TableSwitch extends BranchInstruction {
    int defaultOffset;
    //low和high记录case的取值范围
    int low;
    int high;
    //jumpOffsets是一个索引表，里面存放high-low+1个int值,，对应各种case情况下，执行跳转所需的字节码偏移量
    int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        //tableswitch指令操作码的后面有0~3字节的padding，以保证 defaultOffset在字节码中的地址是4的倍数
        reader.skipPadding();

        defaultOffset = reader.nextU4ToInt();//default走这

        //case的值是这个范围
        low = reader.nextU4ToInt();
        high = reader.nextU4ToInt();
        int jumpOffsetsCount = high - low + 1;

        //一个case 一个偏移量
        jumpOffsets = reader.nextUint32s(jumpOffsetsCount);
    }

    @Override
    public void execute(Zframe frame) {

        int index = frame.getOperandStack().popInt();

        if ((index >= low) && (index <= high)) {
            //case
            super.offSet = jumpOffsets[index - low];
        } else {
            //default
            super.offSet = defaultOffset;
        }

        super.branch(frame);
    }
}
