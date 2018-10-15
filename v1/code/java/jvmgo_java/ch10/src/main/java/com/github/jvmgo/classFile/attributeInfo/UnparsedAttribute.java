package com.github.jvmgo.classFile.attributeInfo;

import com.github.jvmgo.util.BytecodeReader;

/**
 * @Author: panda
 * @Date: 2018/10/3 0003 19:18
 */
public class UnparsedAttribute implements AttributeInfo {
  private String name;
  private int length;
  private   byte[] info;

    public UnparsedAttribute(String name, int length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public AttributeInfo readInfo(BytecodeReader reader) {

        try {
            info =reader.nextBytes(length);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(name+" ppppppppppp"+length);
        }
        return this;
    }
}
