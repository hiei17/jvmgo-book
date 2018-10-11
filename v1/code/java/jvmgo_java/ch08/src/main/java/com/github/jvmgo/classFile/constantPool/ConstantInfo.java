package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

public interface ConstantInfo {

     int CONST_TAG_CLASS = 7;
     int CONST_TAG_FIELD_REF = 9;
     int CONST_TAG_METHOD_REF = 10;
     int CONST_TAG_INTERFACE_METHOD_REF = 11;

     int CONST_TAG_STRING = 8;
     int CONST_TAG_INTEGER = 3;
     int CONST_TAG_FLOAT = 4;
     int CONST_TAG_LONG = 5;
     int CONST_TAG_DOUBLE = 6;
     int CONST_TAG_NAME_AND_TYPE = 12;
     int CONST_TAG_UTF8 = 1;
     int CONST_TAG_METHOD_HANDLE = 15;
     int CONST_TAG_METHOD_TYPE = 16;
     int CONST_TAG_INVOKE_DYNAMIC = 18;



    //Factory method
    static ConstantInfo createConstantInfo(int tag, BytecodeReader reader, ConstantPool constPool) {

        ConstantInfo constantInfo = null;

        switch (tag) {

            //数字
            case CONST_TAG_INTEGER:
                constantInfo = new ConstantIntegerInfo(reader);
                break;
            case CONST_TAG_FLOAT:
                constantInfo = new ConstantFloatInfo(reader);
                break;
            case CONST_TAG_LONG:
                constantInfo = new ConstantLongInfo(reader);
                break;
            case CONST_TAG_DOUBLE:
                constantInfo = new ConstantDoubleInfo(reader);
                break;

            case CONST_TAG_UTF8:
                constantInfo = new ConstUft8Info(reader);
                break;

            //以下3个引用UTF8
            case CONST_TAG_STRING:
                constantInfo = new ConstStringInfo(constPool, reader);
                break;
            case CONST_TAG_CLASS:
                constantInfo = new ConstClassInfo(constPool, reader);
                break;
            case CONST_TAG_NAME_AND_TYPE:
                constantInfo = new ConstantNameAndTypeInfo(constPool, reader);
                break;

            //以下3个引用CLASS+NAME_AND_TYPE
            case CONST_TAG_FIELD_REF:
                constantInfo = new ConstantFieldRefInfo(constPool, reader);
                break;
            case CONST_TAG_METHOD_REF:
                constantInfo = new ConstantMethodRefInfo(constPool, reader);
                break;
            case CONST_TAG_INTERFACE_METHOD_REF:
                constantInfo = new ConstantInterfaceMethodRefInfo(constPool, reader);
                break;

            //是Java SE 7才添加到class文件中的，目的是支持新增的invokedynamic指令
            case CONST_TAG_METHOD_HANDLE:
                constantInfo = null;
                reader.nextU1toInt();
                reader.nextU2ToInt();
                break;
            case CONST_TAG_METHOD_TYPE:
                constantInfo = null;
                reader.nextU2ToInt();
                break;
            case CONST_TAG_INVOKE_DYNAMIC:
                constantInfo = null;
                reader.nextU2ToInt();
                reader.nextU2ToInt();
                break;

            default:
             //   Main.panic("java.lang.ClassFormatError: constant poll tag!");
                break;
        }

        return constantInfo;
    }



}
