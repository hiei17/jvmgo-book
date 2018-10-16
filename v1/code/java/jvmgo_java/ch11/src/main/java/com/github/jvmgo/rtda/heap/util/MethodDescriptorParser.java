package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.MethodDescriptor;

import java.util.ArrayList;

/**
 * 用于产生方法描述对象的工具
 */
public class MethodDescriptorParser {
    private String raw;             //在class文件中原始的描述eg:(Ljava/lang/String;IL)V
    private int offset;             //解析raw的当前索引


    public MethodDescriptorParser(String raw) {
        this.raw = raw;
    }


    public MethodDescriptor parse() {

        ArrayList<String> parameterTypes = addParamTypes();
        String  returnType= parseReturnType();
        assert  offset == raw.length();
        return  new MethodDescriptor(parameterTypes,returnType);
    }


    private ArrayList<String> addParamTypes() {
        assert '(' == raw.charAt(offset);
        offset++;
        ArrayList<String> parameterTypes = new ArrayList<>();
        try {
            while (true){
                String t = parseFieldType();
                parameterTypes.add(t);
            }
        } catch (Exception e) {
            ;
        }
        assert ')' == raw.charAt(offset);
        offset++;
        return parameterTypes;
    }


    private String parseReturnType() {

        if ('V' == raw.charAt(offset++)) {
            return  "V";
        }

        offset--;
        return parseFieldType();
    }

    private String parseFieldType() {
        char c = raw.charAt(offset++);
        switch (c) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
                return c + "";
            //    Ljava/lang/String;
            case 'L':
                return parseObjectType();
            //[F
            case '[':
                return parseArrayType();
            default:
                offset--;
               throw new RuntimeException();
        }
    }

    //返回 Ljava/lang/String
    private String parseObjectType() {
        int semicolonIndex = raw.indexOf(';', offset);
        int start = offset - 1;
        int end = semicolonIndex + 1;
        offset = end;
        return raw.substring(start, end);
    }

   //返回[I
    private String parseArrayType() {
        int start = offset - 1;
        parseFieldType();
        int end = offset;
        return raw.substring(start, end);
    }


}
