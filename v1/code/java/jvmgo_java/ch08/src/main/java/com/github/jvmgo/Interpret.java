package com.github.jvmgo;

import com.github.jvmgo.instructions.InstructionFactory;
import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.util.BytecodeReader;


public class Interpret {

    public static void execute(Method method, boolean printInstructExecute) {


        Thread thread = new Thread();
        thread.pushFrame(method);

        loop(thread, printInstructExecute);
    }

    //不变
    private static void loop(Thread thread, boolean printInstructExecute) {
        Frame frame=null;
        BytecodeReader reader = new BytecodeReader();
        try {
            while (true) {
                frame = thread.currentFrame();
                int nextPC = frame.getNextPC();//frame如果是新建的会从0开始
                thread.setPc(nextPC);//此指令开始执行的pc

                reader.reset(frame.getMethod().getCode(), nextPC);
                int opcode = reader.nextU1toInt();
                Instruction  inst = InstructionFactory.newInstruction(opcode);
                inst.fetchOperands(reader);//拿到操作数
                frame.setNextPC(reader.getPos());//更新frame


                inst.execute(frame);//执行

                if(printInstructExecute){
                    Method method =frame.getMethod();
                    String className = method.getJClass().getName();
                    String methodName = method.getName();
                    int pc = frame.getThread().getPc();
                    System.out.println(String.format("%s.%s() %d ",className, methodName, pc)+inst.getClass().getSimpleName());
                }

                if (thread.isStackEmpty()) {
                    break;
                }

               /* System.out.print("PC: " + nextPC + "  " + inst.getClass().getSimpleName());

                System.out.println(frame.getLocalVars()+"   stack"+frame.getOperandStack());*/
            }
        } catch (Exception e) {
            System.out.println("LocalVars:"+frame.getLocalVars());
            System.out.println("OperandStack:"+frame.getOperandStack());
           e.printStackTrace();
        }
    }



}