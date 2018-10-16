package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.classFile.constantPool.ConstantPool;
import com.github.jvmgo.util.BytecodeReader;
import lombok.Getter;

/**
 Code_attribute {

 u2 attribute_name_index;
 u4 attribute_length;

 u2 max_stack;//操作数栈的最大深度
 u2 max_locals;//局部变量表大小

 //字节码
 u4 code_length;
 u1 code[code_length];

 //异常处理表
 u2 exception_table_length;
 {
 u2 start_pc;
 u2 end_pc;
 u2 handler_pc;
 u2 catch_type;
 }
 exception_table[exception_table_length];

 //属性表
 u2 attributes_count;
 attribute_info attributes[attributes_count];
 }
 ```
 */
@Getter
public class CodeAttribute implements AttributeInfo {

    private int maxStack;//操作数栈的最大深度
    private int maxLocals;//局部变量表大小

    private byte[] code;//字节码

    private AttributeInfo[] attributes;//子属性

    private ConstantPool cp;
    private ExceptionTableEntry[] exceptionTable; //异常处理表

    public CodeAttribute(ConstantPool cp) {
        this.cp=cp;
    }

    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {
        maxStack = reader.nextU2ToInt();
        maxLocals = reader.nextU2ToInt();

        //字节码
        int codeLength = reader.nextU4ToInt();
        code = reader.nextBytes(codeLength);

        //异常处理表
        exceptionTable = readExceptionTable(reader);

        attributes = AttributeInfo.readAttributes(reader, cp);
        return this;
    }



    public    class ExceptionTableEntry  {


      public int  startPc   ;
      public int  endPc     ;
      public int  handlerPc ;
      public int  catchType ;

      private ExceptionTableEntry(BytecodeReader reader) {
          this.startPc =  reader.nextU2ToInt();
          this.endPc =  reader.nextU2ToInt();
          this.handlerPc =  reader.nextU2ToInt();
          this.catchType =  reader.nextU2ToInt();
      }

    }

    private   ExceptionTableEntry[]  readExceptionTable(BytecodeReader reader){

        int length = reader.nextU2ToInt();

        ExceptionTableEntry[]  exceptionTable = new ExceptionTableEntry[length];

        for (int i=0;i<length;i++){
            exceptionTable[i]=new ExceptionTableEntry(reader);
        }
        return exceptionTable;
    }


}
