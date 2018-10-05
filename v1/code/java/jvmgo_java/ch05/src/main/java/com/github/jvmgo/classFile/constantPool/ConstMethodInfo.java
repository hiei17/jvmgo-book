package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

public class ConstMethodInfo implements ConstantInfo{
	private ConstantPool constPool;
	private int classIndex;
	private int nameAndTypeIndex;
	
	public ConstMethodInfo(ConstantPool aConstPool, BytecodeReader reader) {
		this.classIndex = reader.nextU2ToInt();
		this.nameAndTypeIndex = reader.nextU2ToInt();
	}

	@Override
	public String getValue() {
		return "";
	}

}
