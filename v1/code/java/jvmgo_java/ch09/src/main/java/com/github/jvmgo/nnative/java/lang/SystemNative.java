package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.ArrayObject;
import com.github.jvmgo.rtda.heap.OObject;

/**
 * @Author: panda
 * @Date: 2018/10/14 0014 20:46
 */
public class SystemNative {
    private static final String jlSystem  = "java/lang/System";


    //StringBuilder.append（） 最终会调用这个本地方法
    //使用例子System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);

    // public static native void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
    private static NativeMethod arraycopy=frame -> {

        LocalVars localVars = frame.getLocalVars();
       //从局部变量表中拿到5个参数
        //源数组和目标数组必须兼容才能拷贝
        ArrayObject src;
        ArrayObject dest;
        try {
             src=(ArrayObject) localVars.getRef(0);//源数组
             dest = (ArrayObject)localVars.getRef(2);//目标数组
             checkArrayCopy(src, dest);
        } catch (Exception e) {
            throw new ArrayStoreException();
        }

        int srcPos = localVars.getInt(1);//源数组这里开始
        int destPos = localVars.getInt(3);//目标数组这里开始
        int length = localVars.getInt(4);


        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos+length > src.length ||
                destPos+length > dest.length) {
            throw new IndexOutOfBoundsException();
        }

        //最后，参数合法，
        // 调用ArrayCopy（）函数进行数组拷贝
        ArrayObject.copy(src, dest, srcPos, destPos, length);
    };

    private static void checkArrayCopy(OObject src, OObject dest) throws Exception{

        ArrayClass srcClass = (ArrayClass)src.getClazz();
        ArrayClass  destClass = (ArrayClass)dest.getClazz();

        if (srcClass.componentClass().isPrimitive()
                ||destClass.componentClass().isPrimitive()) {
            assert  srcClass.equals(destClass);
        }
    }


    //初始化时注册此方法
    public static void init() {
         NativeMethod.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy);

//         NativeMethod.register(jlSystem, "initProperties", "(Ljava/util/Properties;)Ljava/util/Properties;",
//                 initProperties);
//         NativeMethod.register(jlSystem, "setIn0", "(Ljava/io/InputStream;)V", setIn0);
//         NativeMethod.register(jlSystem, "setOut0", "(Ljava/io/PrintStream;)V", setOut0);
//         NativeMethod.register(jlSystem, "setErr0", "(Ljava/io/PrintStream;)V", setErr0);
//         NativeMethod.register(jlSystem, "currentTimeMillis", "()J", currentTimeMillis);
    }
}
