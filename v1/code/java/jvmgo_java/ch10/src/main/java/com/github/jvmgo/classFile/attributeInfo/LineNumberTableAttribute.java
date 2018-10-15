package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 21:57
 */
public class LineNumberTableAttribute implements AttributeInfo {


    private LineNumberTableEntry[] lineNumberTable;



    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {
        int length = reader.nextU2ToInt();


        lineNumberTable = new LineNumberTableEntry[length];

        for (int i=0;i<length;i++){
            lineNumberTable[i]=new LineNumberTableEntry(reader);
        }
        return this;
    }

    public Integer getLineNumber(int pc) {
        for (LineNumberTableEntry entry : lineNumberTable) {

            if(entry.startPc>pc){
                return entry.lineNumber;
            }
        }

        return -1;

    }

    private class LineNumberTableEntry {
        private final int startPc;
        private final int lineNumber;

        public LineNumberTableEntry(BytecodeReader reader) {
            startPc=reader.nextU2ToInt();
            lineNumber=reader.nextU2ToInt();
        }
    }
}
