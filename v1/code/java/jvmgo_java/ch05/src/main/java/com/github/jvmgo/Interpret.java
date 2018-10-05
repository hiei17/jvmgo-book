package com.github.jvmgo;

import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import com.github.jvmgo.instructions.InstructionFactory;
import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.rtda.Zframe;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.util.BytecodeReader;


public class Interpret {
    public Interpret(MemberInfo methodInfo) {

        //class文件解析出来的方法信息里面的Code属性 里面有 方法怎么执行的信息
        CodeAttribute codeAttr = methodInfo.getCodeAttr();
        int maxLocals = codeAttr.getMaxLocals();
        int maxStack = codeAttr.getMaxStack();
        byte[] bytes = codeAttr.getCode();

        Zframe frame = new Zframe(maxLocals, maxStack);
        Thread thread = new Thread();
        thread.pushFrame(frame);

        loop(thread, bytes);
    }

    private void loop(Thread thread, byte[] bytecode) {

        Zframe frame = thread.popFrame();
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
            System.out.format("opcode : 0x%x\n",opcode);
            System.out.println("LocalVars:"+frame.getLocalVars());
            System.out.println("OperandStack:"+frame.getOperandStack());
            e.printStackTrace();
        }
    }

    public static void main(String[] arg){
     int i=0;
     Object[] ada=new Object[5];
     ada[3]=i;
     System.out.print(ada);
    }

}