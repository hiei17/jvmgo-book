package com.github.jvmgo.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * �������︴�Ƶ�
 */
public class Util {
	public static int byteToInt(byte[] codes){
    	String s1 = byteToHexString(codes);
    	return Integer.valueOf(s1, 16).intValue();
    }
    
    
    
	public static String byteToHexString(byte[] codes ){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<codes.length;i++){
			byte b = codes[i];
			int value = b & 0xFF;
			String strHex = Integer.toHexString(value);
			if(strHex.length()< 2){
				strHex = "0" + strHex;
			}		
			buffer.append(strHex);
		}
		return buffer.toString();
	}

	public static byte[] int2bytes(int a) {
		return new byte[]{

				(byte) ((a >> 24) & 0xFF),
				(byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF)};
	}

	public static byte[] float2bytes(float val) {
		return ByteBuffer.allocate(4).putFloat(val).array();
	}

	public static float byte2float(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getFloat();
	}

	public static long byte2long(byte[] bytes1,byte[] bytes2) {
		return byte2long(concat(bytes1,bytes2));
	}


	public static byte[] long2bytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}

	public static byte[] double2bytes(double x) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(x);
		return bytes;

	}

	private static byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static double byte2double(byte[] bytes, byte[] bytes1) {
		return ByteBuffer.wrap(concat(bytes,bytes1)).order(ByteOrder.BIG_ENDIAN).getDouble();
	}
	public static double byte2double(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getDouble();
	}

	public static long byte2long(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong();
	}
}
