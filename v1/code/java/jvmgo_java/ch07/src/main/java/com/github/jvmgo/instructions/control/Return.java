package com.github.jvmgo.instructions.control;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.Thread;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 11:09
 */
public class Return extends NoOperandsInstruction {
    private int returnSlotAmount;
    public Return(int returnSlotAmount) {
        this.returnSlotAmount =returnSlotAmount;
    }

    @Override
    public void execute(Frame frame) {
        Thread thread = frame.getThread();
        Frame executedFrame = thread.popFrame();
        Frame waitReturnFrame= thread.currentFrame();
        switch (returnSlotAmount){
            case 0:return;
            case 1:
                Object pop = executedFrame.getOperandStack().pop();
                waitReturnFrame.getOperandStack().push(pop);
                return;
            case 2:
                Object pop2 = executedFrame.getOperandStack().pop2();
                waitReturnFrame.getOperandStack().pushLorD(pop2);
        }

    }
}
