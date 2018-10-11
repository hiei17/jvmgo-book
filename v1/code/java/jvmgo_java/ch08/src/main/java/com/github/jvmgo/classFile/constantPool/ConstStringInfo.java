package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.CovertRuntimeConstant;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;

public class ConstStringInfo implements CovertRuntimeConstant {
	private ConstantPool constPool;
	private int nameIndex;

	public ConstStringInfo(ConstantPool aConstPool, BytecodeReader reader) {
		this.nameIndex = reader.nextU2ToInt();
		this.constPool = aConstPool;
	}



	@Override
	public String toString() {
		return "ConstStringInfo{" +
				"name=" + constPool.getUTF8(nameIndex) +
				'}';
	}

	@Override
	public Object getValue(RuntimeConstantPool runtimeConstantPool) {

		return constPool.getUTF8(nameIndex);
	}
}
