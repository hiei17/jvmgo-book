package com.github.jvmgo.classFile.constantPool;

import com.github.jvmgo.rtda.heap.*;
import com.github.jvmgo.rtda.heap.ref.ClassRef;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.util.BytecodeReader;
import lombok.Getter;

/**
 * {
 * u1 tag;
 * u2 name_index;
 * }
 */
@Getter
public class ConstClassInfo implements CovertRuntimeConstant {
	private ConstantPool constPool;
	private int nameIndex;
	
	public ConstClassInfo(ConstantPool aConstPool, BytecodeReader reader) {
		this.nameIndex = reader.nextU2ToInt();
		this.constPool = aConstPool;
	}



	@Override
	public ClassRef getValue(RuntimeConstantPool runtimeConstantPool) {

		CClass clazz = runtimeConstantPool.getClazz();
		String className = constPool.getUTF8(nameIndex);

		return new ClassRef(clazz,className);

	}


	@Override
	public String toString() {
		return  constPool.getUTF8(nameIndex);
	}
}
