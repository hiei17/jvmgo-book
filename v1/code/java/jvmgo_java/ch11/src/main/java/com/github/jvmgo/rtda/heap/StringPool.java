package com.github.jvmgo.rtda.heap;

import java.util.HashMap;

/**
 * @Author: panda
 * @Date: 2018/10/11 0011 18:36
 */
public class StringPool {
    private static HashMap<String, OObject> internedStrings=new HashMap<>();

    //这里是str假设是本地类型
    public static OObject JString(MyClassLoader loader, String str){

        OObject jObject = internedStrings.get(str);
        if (jObject!=null){
            return jObject;
        }

        jObject = loader.loadClass("java/lang/String").newObject();
        ArrayObject characterArray=convertString2CharArrayObject(loader,str);
        jObject.setFieldValByNameAndType("var","[C",characterArray);
        internedStrings.put(str,jObject);
        return jObject;
    }

    private static ArrayObject convertString2CharArrayObject(MyClassLoader loader,String str) {
        ArrayClass arrClass = (ArrayClass) loader.loadClass("[C");

        ArrayObject arr = arrClass.newArray(str.length());
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            arr.set(i,(int)chars[i]);
        }
        return arr;
    }





    public static String getString(OObject jobject) {
        ArrayObject values = (ArrayObject) jobject.getFieldVar("value", "[C");

        Object[] slots = values.slots;

        int length = slots.length;
        char[] c= new char[length];

        for (int i = 0; i < slots.length; i++) {

            int a= (Integer) slots[i];
            c[i]=(char)a;

        }

        return new String(c);
    }

    public static OObject internString(OObject aStringObject) {
        String string = getString(aStringObject);
        OObject oldSpringObject = internedStrings.get(string);
        if(oldSpringObject!=null){
            return oldSpringObject;
        }
        internedStrings.put(string,aStringObject);
        return aStringObject;
    }

}
