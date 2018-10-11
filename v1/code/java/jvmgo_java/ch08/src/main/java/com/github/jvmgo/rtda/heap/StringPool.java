package com.github.jvmgo.rtda.heap;

import java.util.HashMap;

/**
 * @Author: panda
 * @Date: 2018/10/11 0011 18:36
 */
public class StringPool {
    private static HashMap<String,JObject> internedStrings=new HashMap<>();

    //这里是str假设是本地类型
    public static JObject JString(MyClassLoader loader,String str){
        JObject jObject = internedStrings.get(str);
        if (jObject!=null){
            return jObject;
        }

        jObject = loader.loadClass("java/lang/String").newObject();
        jObject.setFieldValByNameAndType("var","[C",str);
        internedStrings.put(str,jObject);
        return jObject;
    }


    public static String getString(JObject jobject) {
       String charArr = (String) jobject.getFieldVar("value", "[C");
       return charArr;
    }
}
