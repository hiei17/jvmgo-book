package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.JObject;

import java.util.Arrays;


public class LocalVars {

    public Object[] slots;
    public LocalVars(int maxLocals) {
        slots=new Object[maxLocals];
    }

    public void  setInt(int index , int val ) {
        slots[index]= val;
    }

    public int  getInt(int index ) {
        return (int) slots[index];
    }

    public void  setFloat(int index ,float val) {
        slots[index]=val;
    }

    public float getFloat(int index)  {
        return (float) slots[index];
    }

    // long consumes two slots
    public void  setLong(int index, long val) {
        slots[index]=val;
    }
    public long getLong(int index )  {
        return (long) slots[index];
    }

    // double consumes two slots
    public void setDouble(int index , double val ) {

        slots[index]=val;
    }

    public double getDouble(int index )  {
        return (double) slots[index];
    }

    public void setRef(int index , JObject ref) {
        slots[index]=ref;
    }

    public JObject getRef(int index )  {
        return (JObject) slots[index];
    }

    @Override
    public String toString() {
        return  Arrays.toString(slots) ;
    }
}
