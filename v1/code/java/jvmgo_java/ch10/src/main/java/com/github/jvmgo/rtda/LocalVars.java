package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.OObject;

import java.util.Arrays;


public class LocalVars {

    public Object[] slots;
    public LocalVars(int maxLocals) {
        slots=new Object[maxLocals];
    }

    public void  setInt(int index , int val ) {
        if(val==259){
            System.out.print(0);
        }
        slots[index]= val;
    }

    public int  getInt(int index ) {

        Object slot = slots[index];
        if(slot==null){
            return 0;
        }
        return (int) slot;
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

    public void setRef(int index , OObject ref) {
        slots[index]=ref;
    }

    public OObject getRef(int index )  {
        return (OObject) slots[index];
    }

    @Override
    public String toString() {
        return  Arrays.toString(slots) ;
    }
}
