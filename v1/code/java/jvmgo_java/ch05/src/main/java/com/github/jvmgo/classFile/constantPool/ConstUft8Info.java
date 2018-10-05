package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.util.BytecodeReader;

public class ConstUft8Info implements ConstantInfo{

	private String value;
	
	public ConstUft8Info(BytecodeReader reader) {
		int length = reader.nextU2ToInt();
		byte[] bytes = reader.nextBytes(length);
		this.value = new String(bytes);

	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return value;
	}
}
