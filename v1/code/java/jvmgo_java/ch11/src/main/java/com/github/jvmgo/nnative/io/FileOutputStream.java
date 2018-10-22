package com.github.jvmgo.nnative.io;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.heap.OObject;

/**
 * @Author: panda
 * @Date: 2018/10/18 0018 2:35
 */
public class FileOutputStream {

    //最终打印的方法
// private native void writeBytes(byte b[], int off, int len, boolean append) throws IOException;
    private static NativeMethod writeBytes=frame -> {
        LocalVars localVars = frame.getLocalVars();

        //this := vars.GetRef(0)
        OObject ref = localVars.getRef(0);
        int startIndex = localVars.getInt(1);
        int len = localVars.getInt(3);
        //append := vars.GetBoolean(4)

      //  ref.
       // System.out.print(ref.su);
        //写到控制台。
    //    os.Stdout.Write(goBytes)
    };

    public  static void init(){
        NativeMethod.register("java/io/FileOutputStream", "writeBytes", "([BIIZ)V", writeBytes);
    }
}
