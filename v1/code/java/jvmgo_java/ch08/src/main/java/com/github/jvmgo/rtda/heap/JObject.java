package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 18:36
 */
@Getter
public class JObject {


    protected JClass jClass;
    protected Object[] slots;

    public JObject(JClass jClass) {
        this.jClass=jClass;
        slots=new Object[jClass.instanceSlotCount];
    }

    public JObject() {
    }

    public boolean isInstanceOf(JClass mayBigClass) {
        return ClassHierarchyUtil.isAssignableFrom(mayBigClass,jClass);
    }

    @Override
    public String toString() {

        return jClass + " " + Arrays.toString(slots) ;
    }
}
