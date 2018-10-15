package com.github.jvmgo.rtda.heap;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 20:55
 */
public interface Access_flags {
    public final int ACC_PUBLIC = 0x0001; // class field method
    public final int ACC_PRIVATE = 0x0002; // field method
    public final int ACC_PROTECTED = 0x0004; // field method
    public final int ACC_STATIC = 0x0008; // field method
    public final int ACC_FINAL = 0x0010; // class field method
    public final int ACC_SUPER = 0x0020; // class
    public final int ACC_SYNCHRONIZED = 0x0020; // method
    public final int ACC_VOLATILE = 0x0040; // field
    public final int ACC_BRIDGE = 0x0040; // method
    public final int ACC_TRANSIENT = 0x0080; // field
    public final int ACC_VARARGS = 0x0080; // method
    public final int ACC_NATIVE = 0x0100; // method
    public final int ACC_INTERFACE = 0x0200; // class
    public final int ACC_ABSTRACT = 0x0400; // class method
    public final int ACC_STRICT = 0x0800; // method
    public final int ACC_SYNTHETIC = 0x1000; // class field method
    public final int ACC_ANNOTATION = 0x2000; // class
    public final int ACC_ENUM = 0x4000; // class field
}
