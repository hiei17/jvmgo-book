package com.github.jvmgo.instructions.extended;


import com.github.jvmgo.instructions.Load;
import com.github.jvmgo.instructions.Store;
import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.Index8Instruction;
import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.instructions.math.Iinc;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.util.BytecodeReader;

/**
 * Author: zhangxin
 * Time: 2017/5/6 0006.
 * Desc:
 */
public class Wide implements com.github.jvmgo.instructions.base.Instruction {

    Instruction modifiedInstruction;

    /*
    先从字节码中读取一字节的操作码，然后创建子指令实例，最后读取子指令的操作数。因为没有实现ret指令，所以暂时调用 RuntimeException 函数终止程序执行。
    */
    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opCode = reader.nextU1toInt();
        Index8Instruction index8Instruction = new Load(DateTypeEnum.i);
        switch (opCode) {
            case 0x15:
                break;
            case 0x16:
                index8Instruction = new Load(DateTypeEnum.l);
                break;
            case 0x17:
                index8Instruction = new Load(DateTypeEnum.f);
                break;
            case 0x18:
                index8Instruction = new Load(DateTypeEnum.d);
                break;
            case 0x19:
                index8Instruction = new Load(DateTypeEnum.a);
                break;
            case 0x36:
                index8Instruction = new Store(DateTypeEnum.i);
                break;
            case 0x37:
                index8Instruction = new Store(DateTypeEnum.l);
                break;
            case 0x38:
                index8Instruction = new Store(DateTypeEnum.f);
                break;
            case 0x39:
                index8Instruction = new Store(DateTypeEnum.d);
                index8Instruction.index = reader.nextU2ToInt();
                break;
            case 0x3a:
                index8Instruction = new Store(DateTypeEnum.a);
                break;
            case 0x84:
                Iinc iinc = new Iinc();
                iinc.index = reader.nextU2ToInt();
                iinc.add = reader.nextU2ToShort();
                modifiedInstruction = index8Instruction;
                return;
            case 0xa9: // ret
                throw new RuntimeException("Unsupported opcode: 0xa9!");

        }
        index8Instruction.index = reader.nextU2ToInt();
        modifiedInstruction = index8Instruction;
    }

    //wide指令只是增加了索引宽度，并不改变子指令操作，所以其Execute() 方法只要调用子指令的Execute()方法即可
    @Override
    public void execute(Frame frame) {
        modifiedInstruction.execute(frame);
    }
}
