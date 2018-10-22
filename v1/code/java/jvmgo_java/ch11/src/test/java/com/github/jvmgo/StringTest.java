package com.github.jvmgo;

/**
 * @Author: panda
 * @Date: 2018/10/20 0020 20:43
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1 == s2);//true 池同一个

        String s3 = new String("abc");
        System.out.println(s1 == s3);//强制在堆里面new一个新对象 不是一个了
        System.out.println(s1 == s3.intern());//true  s3.intern()是池中的那个
        System.out.println(s3 == s3.intern());//false
    }
}
