package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

public class ConstStringInfo implements ConstantInfo{
	private ConstantPool constPool;
	private int nameIndex;

	public ConstStringInfo(ConstantPool aConstPool, BytecodeReader reader) {
		this.nameIndex = reader.nextU2ToInt();
		this.constPool = aConstPool;
	}

	@Override
	public String getValue() {
		return this.constPool.getUTF8(this.nameIndex);
	}

	@Override
	public String toString() {
		return "ConstStringInfo{" +
				"name=" + constPool.getUTF8(nameIndex) +
				'}';
	}
}
