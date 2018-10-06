package com.github.jvmgo.util;

import lombok.Data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

//刘欣那里复制的
@Data
public class BytecodeReader {
	private byte[] codes;
	private int pos;
	
	public BytecodeReader(byte[] bytes) {
		this.codes = bytes;
		this.pos = 0;
	}

    public BytecodeReader() {

    }

	public void reset(byte[] bytes,int pos) {
		this.codes = bytes;
		this.pos = pos;
	}

	public int nextU1toInt() {
		return Util.byteToInt(new byte[] { codes[pos++] });
	}


	public int nextU2ToInt() {
		return Util.byteToInt(new byte[] { codes[pos++], codes[pos++] });
	}
	public short nextU2ToShort() {
		return  (short) nextU2ToInt();
	}

	public int nextU4ToInt() {
		return Util.byteToInt(new byte[] { codes[pos++], codes[pos++], codes[pos++], codes[pos++] });
	}

	public float nextU4ToFloat() {
		byte[] bytes= nextBytes(4);

		return Util.byte2float(bytes);
	}

	public String nextU4ToHexString() {
		return Util.byteToHexString((new byte[] { codes[pos++], codes[pos++], codes[pos++], codes[pos++] }));
	}


	public byte[] nextBytes(int len) {
		if (pos + len >= codes.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		byte[] data = Arrays.copyOfRange(codes, pos, pos + len);
		pos += len;
		return data;
	}
	
	
	public long next2U4ToLong() {
		byte[] bytes= nextBytes(8);
		return Util.byte2long(bytes);

	}

	public double next2U4Double() {
		byte[] bytes= nextBytes(8);
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
	}

	public int[] nextUint16s() {
		int count= nextU2ToInt();
		int[] result=new int[count];
		for (int i=0;i<count;i++){
			result[i]= nextU2ToInt();
		}
		return result;
	}

	public int[] nextUint32s(int len) {
		int[] data = new int[len];
		for (int i = 0; i < len; i++) {
			data[i] = nextU4ToInt();
		}
		return data;

	}
	//保证下一个数的位置是4的整数倍

	public void skipPadding() {
		pos+=pos%4;
	}


	/*public void back(int n) {
		this.pos -= n;
	}*/
}
