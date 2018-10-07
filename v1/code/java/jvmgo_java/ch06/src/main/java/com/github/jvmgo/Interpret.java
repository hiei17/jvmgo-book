package com.github.jvmgo;

import com.github.jvmgo.instructions.InstructionFactory;
import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.util.BytecodeReader;


public class Interpret {
    public static void execute(Method method) {


        Frame frame = new Frame(method);
        Thread thread = new Thread();
        thread.pushFrame(frame);

        loop(thread, method.getCode());
    }

    //不变
    private static void loop(Thread thread, byte[] bytecode) {

        Frame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();
        int opcode=0;
        try {
            while (true) {
                int nextPC = frame.getNextPC();
                thread.setPc(nextPC);//调准指令pc以Thread里面记录为准

                reader.reset(bytecode, nextPC);
                opcode = reader.nextU1toInt();
                Instruction inst = InstructionFactory.newInstruction(opcode);
                inst.fetchOperands(reader);//拿到操作数

                frame.setNextPC(reader.getPos());

                System.out.print("PC: " + nextPC + "  " + inst.getClass().getSimpleName());
                inst.execute(frame);//执行
                System.out.println(frame.getLocalVars()+"   "+frame.getOperandStack());
            }
        } catch (Exception e) {
           // System.out.format("opcode : 0x%x\n",opcode);
            //System.out.println("LocalVars:"+frame.getLocalVars());
          //  System.out.println("OperandStack:"+frame.getOperandStack());
           // e.printStackTrace();
        }
    }



}