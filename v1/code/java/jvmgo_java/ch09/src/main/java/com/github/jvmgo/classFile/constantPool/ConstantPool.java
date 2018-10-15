package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;
import lombok.Getter;

@Getter
public class ConstantPool {

	private final int constantPoolSize;
	private ConstantInfo[] constantInfos;
	
	public ConstantPool(BytecodeReader reader) {
		constantPoolSize = reader.nextU2ToInt();
		constantInfos = new  ConstantInfo[constantPoolSize];
		for(int i = 1; i < constantPoolSize ; i++) {
			int tag = reader.nextU1toInt();
			try {
				//多态
				ConstantInfo constInfo = ConstantInfo.createConstantInfo(tag, reader,this);
				constantInfos[i]=constInfo;

				if(tag == ConstantInfo.CONST_TAG_DOUBLE || tag == ConstantInfo.CONST_TAG_LONG) {
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(i +" tag:"+tag);

			}
		}
	}
	
	public String getUTF8(int index) {
		ConstantInfo constInfo = this.constantInfos[index];
		return constInfo == null ? ""  :constInfo.toString();
	}

	public String getClassName(int classNameIndex) {
		if (classNameIndex==0){
			return "";
		}
		ConstClassInfo constantInfo = (ConstClassInfo) constantInfos[classNameIndex];
		int nameIndex = constantInfo.getNameIndex();
		return getUTF8(nameIndex);
	}

	public ConstantInfo getContantInfo(int index){
		return constantInfos[index];
	}
}
