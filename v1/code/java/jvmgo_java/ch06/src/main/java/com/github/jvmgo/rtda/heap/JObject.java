package com.github.jvmgo.rtda.heap;

import lombok.Getter;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 18:36
 */
@Getter
public class JObject {


    private JClass jClass;
    private Object[] slots;

    public JObject(JClass jClass) {
        this.jClass=jClass;
        slots=new Object[jClass.instanceSlotCount];
    }

    public boolean isInstanceOf(JClass mayBigClass) {
        return mayBigClass.isAssignableFrom(jClass);
    }

    @Override
    public String toString() {
        String[] strings = jClass.getName().split("ch06/");
        return strings[1];
    }
}
