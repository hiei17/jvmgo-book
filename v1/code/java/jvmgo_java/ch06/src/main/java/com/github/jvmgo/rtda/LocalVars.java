package com.github.jvmgo.rtda;

import com.github.jvmgo.util.Util;


public class LocalVars {

    private Slot[] slots;
    public LocalVars(int maxLocals) {
        slots=new Slot[maxLocals];
        for (int i = 0; i < slots.length; i++) {
            slots[i]=new Slot();

        }
    }

    public void  setInt(int index , int val ) {
        slots[index].bytes = Util.int2bytes(val);
    }

    public int  getInt(int index ) {
        return Util.byteToInt( slots[index].bytes );
    }

    public void  setFloat(int index ,float val) {
        slots[index].bytes=Util.float2bytes(val);
    }

    public float getFloat(int index)  {
        return Util.byte2float( slots[index].bytes );
    }

    // long consumes two slots
    public void  setLong(int index, long val) {
        byte[] bytes = Util.long2bytes(val);
        System.arraycopy(bytes, 0,  slots[index], 0, 4);
        System.arraycopy(bytes, 4,  slots[index+1], 0, 4);
    }
    public long getLong(int index )  {
        return  Util.byte2long( slots[index].bytes, slots[index+1].bytes );
    }

    // double consumes two slots
    public void setDouble(int index , double val ) {

        byte[] bytes = Util.double2bytes(val);
        System.arraycopy(bytes, 0,  slots[index], 0, 4);
        System.arraycopy(bytes, 4,  slots[index+1], 0, 4);
    }

    public double getDouble(int index )  {
        return  Util.byte2double( slots[index].bytes, slots[index+1].bytes );
    }

    public void setRef(int index , Object ref) {
        slots[index].ref = ref;
    }

    public Object getRef(int index )  {
        return slots[index].ref;
    }

    @Override
    public String toString() {
        String s="[";
        for (int i = 0; i < slots.length; i++) {
            s =s+slots[i]+",  ";

        }
        return s+"]";
    }
}
