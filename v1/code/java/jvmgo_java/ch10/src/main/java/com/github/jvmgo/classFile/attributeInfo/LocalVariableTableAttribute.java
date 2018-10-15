package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.util.BytecodeReader;


public class LocalVariableTableAttribute implements AttributeInfo {
    private LocalVariableTableEntry[] localVariableTable;

    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {
        int length = reader.nextU2ToInt();
        localVariableTable = new LocalVariableTableEntry[length];
        for (int i=0;i<length;i++){
            localVariableTable[i]=new LocalVariableTableEntry(reader);
        }

        return this;
    }

    private class LocalVariableTableEntry {
        private final int startPc;
        private final int length;
        private final int nameIndex;
        private final int descriptorIndex;
        private final int index;

        public LocalVariableTableEntry(BytecodeReader reader) {
                    startPc         =reader.nextU2ToInt();
                    length          =reader.nextU2ToInt();
                    nameIndex       =reader.nextU2ToInt();
                    descriptorIndex =reader.nextU2ToInt();
                    index           =reader.nextU2ToInt();
        }
    }

}
