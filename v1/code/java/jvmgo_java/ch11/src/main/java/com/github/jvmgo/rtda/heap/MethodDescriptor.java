package com.github.jvmgo.rtda.heap;

import lombok.Getter;

import java.util.ArrayList;

/**
 * @Author: panda
 * @Date: 2018/10/9 0009 10:36
 */
@Getter
public class MethodDescriptor {
    ArrayList<String> parameterTypes;
    String returnType;

    public MethodDescriptor(ArrayList<String> parameterTypes, String returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }
}
