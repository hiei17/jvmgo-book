package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 18:36
 */
@Getter
public class OObject {


    protected CClass clazz;
    public Object[] slots;

    //记录Object结构体实例的额外信息
    //本章，只用它来记录[类对象]对应的[Class结构体]指针 不是类对象 就没有东西
    public Object extra;

    public OObject(CClass clazz) {
        this.clazz =clazz;
        slots=new Object[clazz.instanceSlotCount];
    }

    public OObject() {
    }

    public boolean isInstanceOf(CClass mayBigClass) {
        return ClassHierarchyUtil.isAssignableFrom(mayBigClass, clazz);
    }

    @Override
    public String toString() {

        return clazz + " " + Arrays.toString(slots) ;
    }

    public void setFieldValByNameAndType(String fieldName,String fieldType, Object object) {
       Field field= clazz.getField(fieldName,fieldType);
       slots[field.getSlotId()]=object;
    }

    public Object getFieldVar(String fieldName, String fieldType) {
        Field field= clazz.getField(fieldName,fieldType);
        return  slots[field.getSlotId()];
    }
}
