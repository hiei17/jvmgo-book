package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.instructions.base.MethodInvokeLogic;
import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.*;

import java.util.HashMap;
import java.util.Map;

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

    // private static native void setOut0(PrintStream out);// System.out 最终需要这个
    private static NativeMethod setOut0=frame -> {

        //传参
        OObject out = frame.getLocalVars().getRef(0);
        CClass systemClass = frame.getMethod().getClazz();
        systemClass.setStaticFieldValByNameAndType("out", "Ljava/io/PrintStream;", out);

    };

    private static Map<String,String> _sysProps=new HashMap<>();
    static {
        _sysProps.put("java.version","1.8.0");

                _sysProps.put("java.vendor"  ,        "jvm.go");
                _sysProps.put("java.vendor.url",     "https://github.com/zxh0/jvm.go");
                _sysProps.put("java.home",          "todo");
                _sysProps.put("java.class.version",  "52.0");
                _sysProps.put("java.class.path",    "todo");
                _sysProps.put("java.awt.graphicsenv", "sun.awt.CGraphicsEnvironment");
                _sysProps.put("os.name",             "runtime.GOOS");  // todo
                _sysProps.put("os.arch",             "runtime.GOARCH");// todo
                _sysProps.put("os.version",         "");             // todo
                _sysProps.put("file.separator",      "/");           // todo os.PathSeparator
                _sysProps.put("path.separator",      ":");           // todo os.PathListSeparator
                _sysProps.put("line.separator",     "\n");           // todo
                _sysProps.put("user.name",           "");            // todo
                _sysProps.put("user.home",          "");           // todo
                _sysProps.put("user.dir",             ".");            // todo
                _sysProps.put("user.country",       "CN");           // todo
                _sysProps.put("file.encoding",       "UTF-8");
                _sysProps.put("sun.stdout.encoding",  "UTF-8");
                _sysProps.put("sun.stderr.encoding", "UTF-8");
    }

    private static NativeMethod initProperties=frame -> {

        LocalVars localVars = frame.getLocalVars();
        OObject props = localVars.getRef(0);


        OperandStack stack = frame.getOperandStack();
        stack.push(props);

        // public synchronized Object setProperty(String key, String value)
        Method setPropMethod = props.getClazz().getMethod("setProperty", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");

        Thread thread = frame.getThread();

        MyClassLoader classLoader = frame.getMethod().getClazz().classLoader;
        for (Map.Entry<String, String> entry : _sysProps.entrySet()) {
            OObject jkey = StringPool.JString(classLoader, entry.getKey());
            OObject jVal = StringPool.JString(classLoader, entry.getValue());
            OperandStack ops=new OperandStack(3);
            ops.push(props);
            ops.push(jkey);
            ops.push(jVal);
            Frame shimFrame=Frame.newShimFrame(thread,ops);//todo
            thread.pushFrame(shimFrame);
            MethodInvokeLogic.invoke(shimFrame,setPropMethod);
        }
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

         NativeMethod.register(jlSystem, "initProperties", "(Ljava/util/Properties;)Ljava/util/Properties;", initProperties);
     /*    NativeMethod.register(jlSystem, "setIn0", "(Ljava/io/InputStream;)V", setIn0);
         NativeMethod.register(jlSystem, "setOut0", "(Ljava/io/PrintStream;)V", setOut0);
         NativeMethod.register(jlSystem, "setErr0", "(Ljava/io/PrintStream;)V", setErr0);
         NativeMethod.register(jlSystem, "currentTimeMillis", "()J", currentTimeMillis);*/
    }
}
