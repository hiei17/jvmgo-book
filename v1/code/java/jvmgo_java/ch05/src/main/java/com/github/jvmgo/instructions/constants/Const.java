package com.github.jvmgo.instructions.constants;


import com.github.jvmgo.instructions.base.DateTypeEnum;
import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Zframe;

/**
 把隐含在操作码中的常量值推入操作数栈顶。
 */
public class Const extends NoOperandsInstruction {
    DateTypeEnum dateTypeEnum;
    Object ref;
    int i;
    long l;
    float f;
    double d;
    public Const(int i) {
        dateTypeEnum=DateTypeEnum.i;
        this.i=i;
    }

    public Const(float v) {
        dateTypeEnum=DateTypeEnum.f;
        f=v;
    }

    public Const(double v) {
        dateTypeEnum=DateTypeEnum.d;
        d=v;
    }
    public Const(long v) {
        dateTypeEnum=DateTypeEnum.l;
        this.l=v;
    }

    public Const(Object ref) {
        dateTypeEnum= DateTypeEnum.a;
        this.ref=ref;
    }

 /*   public Const(DateTypeEnum dateTypeEnum, Object o) {
        this.dateTypeEnum = dateTypeEnum;
        this.ref = o;
    }*/



    @Override
    public void execute(Zframe frame) {
        switch (dateTypeEnum){

                case a:
                    frame.getOperandStack().push(ref);break;
                case i:
                    frame.getOperandStack().push(i);break;
                case l:
                    frame.getOperandStack().pushLorD(l);break;
                case f:
                    frame.getOperandStack().push(f);break;
                case d:
                    frame.getOperandStack().pushLorD(d);break;
        }
    }

    /*public static class A_NULL {
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushRef(null);
        }
    }
    public static class cM1 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(-1);
        }
    }
    public static class I0 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(0);
        }
    }
    public static class I1 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(1);
        }
    }
    public static class I2 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(2);
        }
    }
    public static class I3 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(3);
        }
    }
    public static class I4 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(4);
        }
    }
    public static class I5 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushInt(5);
        }
    }
    public static class L0 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushLong(0);
        }
    }
    public static class L1 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushLong(1);
        }
    }
    public static class F0 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushFloat(0.0f);
        }
    }
    public static class F1 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushFloat(1.0f);
        }
    }
    public static class F2 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushFloat(2.0f);
        }
    }
    public static class D0 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushDouble(0.0);
        }
    }
    public static class D1 extends NoOperandsInstruction{
        @Override
        public void execute(Zframe frame) {
            frame.getOperandStack().pushDouble(0.0);
        }
    }*/
}
