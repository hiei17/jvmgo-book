package com.github.jvmgo.instructions;

import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.Instruction;
import com.github.jvmgo.instructions.comparisons.LCMP;
import com.github.jvmgo.instructions.comparisons.dcmp.DCMPG;
import com.github.jvmgo.instructions.comparisons.dcmp.DCMPL;
import com.github.jvmgo.instructions.comparisons.fcmp.FCMPG;
import com.github.jvmgo.instructions.comparisons.fcmp.FCMPL;
import com.github.jvmgo.instructions.comparisons.ifacmp.IF_ACMPEQ;
import com.github.jvmgo.instructions.comparisons.ifacmp.IF_ACMPNE;
import com.github.jvmgo.instructions.comparisons.ifcond.*;
import com.github.jvmgo.instructions.comparisons.ificmp.*;
import com.github.jvmgo.instructions.constants.Bipush;
import com.github.jvmgo.instructions.constants.Const;
import com.github.jvmgo.instructions.constants.Nop;
import com.github.jvmgo.instructions.constants.Sipush;
import com.github.jvmgo.instructions.control.*;
import com.github.jvmgo.instructions.conversions.d2x.D2F;
import com.github.jvmgo.instructions.conversions.d2x.D2I;
import com.github.jvmgo.instructions.conversions.d2x.D2L;
import com.github.jvmgo.instructions.conversions.f2x.F2D;
import com.github.jvmgo.instructions.conversions.f2x.F2I;
import com.github.jvmgo.instructions.conversions.f2x.F2L;
import com.github.jvmgo.instructions.conversions.i2x.*;
import com.github.jvmgo.instructions.conversions.l2x.L2D;
import com.github.jvmgo.instructions.conversions.l2x.L2F;
import com.github.jvmgo.instructions.conversions.l2x.L2I;
import com.github.jvmgo.instructions.extended.GOTO_W;
import com.github.jvmgo.instructions.extended.IFNONNULL;
import com.github.jvmgo.instructions.extended.IFNULL;
import com.github.jvmgo.instructions.extended.Wide;
import com.github.jvmgo.instructions.math.*;
import com.github.jvmgo.instructions.stacks.Dup;
import com.github.jvmgo.instructions.stacks.Swap;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 14:17
 */

public class InstructionFactory {
    //什么都不做
    private static Nop nop = new Nop();

    //隐含在操作码中的常量值推入操作数栈顶
    private static Const aconst_null = new Const(null);
    private static Const iconst_m1 = new Const(-1);

    private static Const iconst_0 = new Const(0);
    private static Const iconst_1 = new Const(1);
    private static Const iconst_2 = new Const(2);
    private static Const iconst_3 = new Const(3);
    private static Const iconst_4 = new Const(4);
    private static Const iconst_5 = new Const(5);

    private static Const lconst_0 = new Const(0L);
    private static Const lconst_1 = new Const(1L);

    private static Const fconst_0 = new Const(0.0f);
    private static Const fconst_1 = new Const(1.0f);
    private static Const fconst_2 = new Const(2.0f);

    private static Const dconst_0 = new Const(0.0);
    private static Const dconst_1 = new Const(1.0);

    //从局部变量表获取变量，然后推入操作数栈顶
    private static Load aload_0 = new Load(DateTypeEnum.a, 0);
    private static Load aload_1 = new Load(DateTypeEnum.a, 1);
    private static Load aload_2 = new Load(DateTypeEnum.a, 2);
    private static Load aload_3 = new Load(DateTypeEnum.a, 3);

    private static Load dload_0 = new Load(DateTypeEnum.d, 0);
    private static Load dload_1 = new Load(DateTypeEnum.d, 1);
    private static Load dload_2 = new Load(DateTypeEnum.d, 2);
    private static Load dload_3 = new Load(DateTypeEnum.d, 3);

    private static Load fload_0 = new Load(DateTypeEnum.f, 0);
    private static Load fload_1 = new Load(DateTypeEnum.f, 1);
    private static Load fload_2 = new Load(DateTypeEnum.f, 2);
    private static Load fload_3 = new Load(DateTypeEnum.f, 3);
    private static Load iload_0 = new Load(DateTypeEnum.i, 0);
    private static Load iload_1 = new Load(DateTypeEnum.i, 1);
    private static Load iload_2 = new Load(DateTypeEnum.i, 2);
    private static Load iload_3 = new Load(DateTypeEnum.i, 3);
    private static Load lload_0 = new Load(DateTypeEnum.l, 0);
    private static Load lload_1 = new Load(DateTypeEnum.l, 1);
    private static Load lload_2 = new Load(DateTypeEnum.l, 2);
    private static Load lload_3 = new Load(DateTypeEnum.l, 3);

    //变量从操作数栈顶弹出，然后存入局部变量表
    private static Store istore_0 = new Store(DateTypeEnum.i, 0);
    private static Store istore_1 = new Store(DateTypeEnum.i, 1);
    private static Store istore_2 = new Store(DateTypeEnum.i, 2);
    private static Store istore_3 = new Store(DateTypeEnum.i, 3);

    private static Store lstore_0 = new Store(DateTypeEnum.i, 0);
    private static Store lstore_1 = new Store(DateTypeEnum.i, 1);
    private static Store lstore_2 = new Store(DateTypeEnum.i, 2);
    private static Store lstore_3 = new Store(DateTypeEnum.i, 3);

    private static Store fstore_0 = new Store(DateTypeEnum.i, 0);
    private static Store fstore_1 = new Store(DateTypeEnum.i, 1);
    private static Store fstore_2 = new Store(DateTypeEnum.i, 2);
    private static Store fstore_3 = new Store(DateTypeEnum.i, 3);

    private static Store dstore_0 = new Store(DateTypeEnum.i, 0);
    private static Store dstore_1 = new Store(DateTypeEnum.i, 1);
    private static Store dstore_2 = new Store(DateTypeEnum.i, 2);
    private static Store dstore_3 = new Store(DateTypeEnum.i, 3);

    private static Store astore_0 = new Store(DateTypeEnum.i, 0);
    private static Store astore_1 = new Store(DateTypeEnum.i, 1);
    private static Store astore_2 = new Store(DateTypeEnum.i, 2);
    private static Store astore_3 = new Store(DateTypeEnum.i, 3);
    private static Instruction pop = new Dup(0, 1);
    private static Instruction pop2 = new Dup(0, 2);

    private static Instruction dup = new Dup(1, 0);
    private static Instruction dup_x1 = new Dup(1, 1);
    private static Instruction dup_x2 = new Dup(1, 2);
    private static Instruction dup2 = new Dup(2, 0);
    private static Instruction dup2_x1 = new Dup(2, 1);
    private static Instruction dup2_x2 = new Dup(2, 2);
    private static Instruction swap = new Swap();

    //加减乘除取余取反
    private static Instruction iadd = new MathOperator(DateTypeEnum.i, OperatorEnum.add);
    private static Instruction ladd = new MathOperator(DateTypeEnum.l, OperatorEnum.add);
    private static Instruction fadd = new MathOperator(DateTypeEnum.f, OperatorEnum.add);
    private static Instruction dadd = new MathOperator(DateTypeEnum.d, OperatorEnum.add);

    private static Instruction isub = new MathOperator(DateTypeEnum.i, OperatorEnum.sub);
    private static Instruction lsub = new MathOperator(DateTypeEnum.l, OperatorEnum.sub);
    private static Instruction fsub = new MathOperator(DateTypeEnum.f, OperatorEnum.sub);
    private static Instruction dsub = new MathOperator(DateTypeEnum.d, OperatorEnum.sub);

    private static Instruction imul = new MathOperator(DateTypeEnum.i, OperatorEnum.mul);
    private static Instruction lmul = new MathOperator(DateTypeEnum.l, OperatorEnum.mul);
    private static Instruction fmul = new MathOperator(DateTypeEnum.f, OperatorEnum.mul);
    private static Instruction dmul = new MathOperator(DateTypeEnum.d, OperatorEnum.mul);

    private static Instruction idiv = new MathOperator(DateTypeEnum.i, OperatorEnum.div);
    private static Instruction ldiv = new MathOperator(DateTypeEnum.l, OperatorEnum.div);
    private static Instruction fdiv = new MathOperator(DateTypeEnum.f, OperatorEnum.div);
    private static Instruction ddiv = new MathOperator(DateTypeEnum.d, OperatorEnum.div);

    private static Instruction irem = new MathOperator(DateTypeEnum.i, OperatorEnum.rem);
    private static Instruction lrem = new MathOperator(DateTypeEnum.l, OperatorEnum.rem);
    private static Instruction frem = new MathOperator(DateTypeEnum.f, OperatorEnum.rem);
    private static Instruction drem = new MathOperator(DateTypeEnum.d, OperatorEnum.rem);

    private static Instruction ineg = new Neg(DateTypeEnum.i);
    private static Instruction lneg = new Neg(DateTypeEnum.l);
    private static Instruction fneg = new Neg(DateTypeEnum.f);
    private static Instruction dneg = new Neg(DateTypeEnum.d);

    //按位布朗 只有l i
    private static Instruction iand = new MathOperator(DateTypeEnum.i,OperatorEnum.and);
    private static Instruction land = new MathOperator(DateTypeEnum.i,OperatorEnum.and);
    private static Instruction ior = new MathOperator(DateTypeEnum.i,OperatorEnum.or);
    private static Instruction lor = new MathOperator(DateTypeEnum.i,OperatorEnum.or);
    private static Instruction ixor = new MathOperator(DateTypeEnum.i,OperatorEnum.xor);
    private static Instruction lxor = new MathOperator(DateTypeEnum.i,OperatorEnum.xor);

    //位移
    private static Instruction ishl = new Sh(DateTypeEnum.i, false, false);
    private static Instruction lshl = new Sh(DateTypeEnum.l, false, false);
    private static Instruction ishr = new Sh(DateTypeEnum.i, true, false);
    private static Instruction lshr = new Sh(DateTypeEnum.l, true, false);
    private static Instruction iushr = new Sh(DateTypeEnum.i, true, true);
    private static Instruction lushr = new Sh(DateTypeEnum.l, true, true);

    //原始类型转换
    static Instruction i2l = new I2L();
    static Instruction i2f = new I2F();
    static Instruction i2d = new I2D();
    static Instruction l2i = new L2I();
    static Instruction l2f = new L2F();
    static Instruction l2d = new L2D();
    static Instruction f2i = new F2I();
    static Instruction f2l = new F2L();
    static Instruction f2d = new F2D();
    static Instruction d2i = new D2I();
    static Instruction d2l = new D2L();
    static Instruction d2f = new D2F();
    static Instruction i2b = new I2B();
    static Instruction i2c = new I2C();
    static Instruction i2s = new I2S();

    //
    static Instruction lcmp = new LCMP();
    //弹出f/d比较结果int入账
    static Instruction fcmpl = new FCMPL();
    static Instruction fcmpg = new FCMPG();
    static Instruction dcmpl = new DCMPL();
    static Instruction dcmpg = new DCMPG();




    public static Instruction newInstruction(int opcode) {
        switch (opcode) {

            //常量指令把常量推入操作数栈顶
            case 0x00:
                return nop;
            case 0x01:
                return aconst_null;
            case 0x02:
                return iconst_m1;
            case 0x03:
                return iconst_0;
            case 0x04:
                return iconst_1;
            case 0x05:
                return iconst_2;
            case 0x06:
                return iconst_3;
            case 0x07:
                return iconst_4;
            case 0x08:
                return iconst_5;
            case 0x09:
                return lconst_0;
            case 0x0a:
                return lconst_1;
            case 0x0b:
                return fconst_0;
            case 0x0c:
                return fconst_1;
            case 0x0d:
                return fconst_2;
            case 0x0e:
                return dconst_0;
            case 0x0f:
                return dconst_1;

            case 0x10:
                return new Bipush();
            case 0x11:
                return new Sipush();

            //从局部变量表获取变量，然后推入操作数栈顶
            case 0x15:
                return new Load(DateTypeEnum.i);
            case 0x16:
                return new Load(DateTypeEnum.l);
            case 0x17:
                return new Load(DateTypeEnum.f);
            case 0x18:
                return new Load(DateTypeEnum.d);
            case 0x19:
                return new Load(DateTypeEnum.a);
            case 0x1a:
                return iload_0;
            case 0x1b:
                return iload_1;
            case 0x1c:
                return iload_2;
            case 0x1d:
                return iload_3;
            case 0x1e:
                return lload_0;
            case 0x1f:
                return lload_1;
            case 0x20:
                return lload_2;
            case 0x21:
                return lload_3;
            case 0x22:
                return fload_0;
            case 0x23:
                return fload_1;
            case 0x24:
                return fload_2;
            case 0x25:
                return fload_3;
            case 0x26:
                return dload_0;
            case 0x27:
                return dload_1;
            case 0x28:
                return dload_2;
            case 0x29:
                return dload_3;
            case 0x2a:
                return aload_0;
            case 0x2b:
                return aload_1;
            case 0x2c:
                return aload_2;
            case 0x2d:
                return aload_3;

            //变量从操作数栈顶弹出，然 后存入局部变量表。
            case 0x36:
                return new Store(DateTypeEnum.i);
            case 0x37:
                return new Store(DateTypeEnum.l);
            case 0x38:
                return new Store(DateTypeEnum.f);
            case 0x39:
                return new Store(DateTypeEnum.d);
            case 0x3a:
                return new Store(DateTypeEnum.a);

            case 0x3b:
               
                return istore_0;
            case 0x3c:
               
                return istore_1;
            case 0x3d:
               
                return istore_2;
            case 0x3e:
               
                return istore_3;
            case 0x3f:
               
                return lstore_0;
            case 0x40:
               
                return lstore_1;
            case 0x41:
               
                return lstore_2;
            case 0x42:
               
                return lstore_3;
            case 0x43:
               
                return fstore_0;
            case 0x44:
               
                return fstore_1;
            case 0x45:
               
                return fstore_2;
            case 0x46:
               
                return fstore_3;
            case 0x47:
               
                return dstore_0;
            case 0x48:
               
                return dstore_1;
            case 0x49:
               
                return dstore_2;
            case 0x4a:
               
                return dstore_3;
            case 0x4b:
               
                return astore_0;
            case 0x4c:
               
                return astore_1;
            case 0x4d:
               
                return astore_2;
            case 0x4e:
               
                return astore_3;

            //操作数栈弹出
            case 0x57:
                return pop;
            case 0x58:
                return pop2;

            //复制
            case 0x59:
               
                return dup;
            case 0x5a:
               
                return dup_x1;
            case 0x5b:
               
                return dup_x2;
            case 0x5c:
               
                return dup2;
            case 0x5d:
               
                return dup2_x1;
            case 0x5e:
               
                return dup2_x2;
            case 0x5f:
                return swap;

            //算数

            case 0x60:
               
                return iadd;
            case 0x61:
               
                return ladd;
            case 0x62:
               
                return fadd;
            case 0x63:
               
                return dadd;

            case 0x64:
               
                return isub;
            case 0x65:
               
                return lsub;
            case 0x66:
               
                return fsub;
            case 0x67:
               
                return dsub;
            case 0x68:
               
                return imul;
            case 0x69:
               
                return lmul;
            case 0x6a:
               
                return fmul;
            case 0x6b:
               
                return dmul;
            case 0x6c:
               
                return idiv;
            case 0x6d:
               
                return ldiv;
            case 0x6e:
               
                return fdiv;
            case 0x6f:
                return ddiv;
            case 0x70:
                return irem;
            case 0x71:
                return lrem;
            case 0x72:
                return frem;
            case 0x73:
                return drem;
            case 0x74:
                return ineg;
            case 0x75:
                return lneg;
            case 0x76:
                return fneg;
            case 0x77:
                return dneg;


            //位移
            case 0x78:
                return ishl;
            case 0x79:
                return lshl;
            case 0x7a:
                return ishr;
            case 0x7b:
                return lshr;
            case 0x7c:
                return iushr;
            case 0x7d:
                return lushr;

            //布朗
            case 0x7e:
                return iand;
            case 0x7f:
                return land;
            case 0x80:
                return ior;
            case 0x81:
                return lor;
            case 0x82:
                return ixor;
            case 0x83:
                return lxor;

                //操作数:增值 局部变量表index
            case 0x84:
                return new Iinc();

            //基本类型转换 4*3个
            case 0x85:;
                return i2l;
            case 0x86:;
                return i2f;
            case 0x87:;
                return i2d;
            case 0x88:;
                return l2i;
            case 0x89:;
                return l2f;
            case 0x8a:;
                return l2d;
            case 0x8b:;
                return f2i;
            case 0x8c:;
                return f2l;
            case 0x8d:;
                return f2d;
            case 0x8e:;
                return d2i;
            case 0x8f:;
                return d2l;
            case 0x90:;
                return d2f;
            case 0x91:;
                return i2b;
            case 0x92:;
                return i2c;
            case 0x93:;
                return i2s;

            //弹出能l/f/d比较结果int入账
            case 0x94:
                return lcmp;
            case 0x95:
                return fcmpl;
            case 0x96:
                return fcmpg;
            case 0x97:
                return dcmpl;
            case 0x98:
                return dcmpg;

            //根据弹出的数和0比较 结果 满足条件 就按操作数跳转
            case 0x99:
                return new IFEQ();
            case 0x9a:
                return new IFNE();
            case 0x9b:
                return new IFLT();
            case 0x9c:
                return new IFGE();
            case 0x9d:
                return new IFGT();
            case 0x9e:
                return new IFLE();
            //和上面不同的是 2个数都是弹出的 比较
            case 0x9f:
                return new IF_ICMPEQ();
            case 0xa0:
                return new IF_ICMPNE();
            case 0xa1:
                return new IF_ICMPLT();
            case 0xa2:
                return new IF_ICMPGE();
            case 0xa3:
                return new IF_ICMPGT();
            case 0xa4:
                return new IF_ICMPLE();
            case 0xa5:
                return new IF_ACMPEQ();
            case 0xa6:
                return new IF_ACMPNE();
            //控制指令
            //无条件就是跳转
            case 0xa7:
                return new Goto();
            // 如果case值可以编码成一个索引表，则实现成tableswitch指令；
            // 否则实现成lookupswitch指令。
            case 0xaa:
                return new TableSwitch();
            case 0xab:
                return new LookUpSwitch();

            //扩展
            case 0xc4:
                return new Wide();

            // case 0xc5:
            // 	return &MULTI_ANEW_ARRAY{}
            case 0xc6:
                return new IFNULL();
            case 0xc7:
                return new IFNONNULL();
            case 0xc8:
                return new GOTO_W();
            default:
                throw new RuntimeException(String.format("Unsupported opcode: 0x%x!", opcode));
        }

    }
}
