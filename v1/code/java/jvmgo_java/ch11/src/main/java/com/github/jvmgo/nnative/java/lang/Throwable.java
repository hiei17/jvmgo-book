package com.github.jvmgo.nnative.java.lang;

import com.github.jvmgo.nnative.NativeMethod;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.OObject;

/**
 * @Author: panda
 * @Date: 2018/10/15 0015 18:20
 */
public class Throwable {

    // private native Throwable fillInStackTrace(int dummy);
    private static NativeMethod fillInStackTrace=frame -> {

        OObject thisObject = frame.getLocalVars().getRef(0);
        frame.getOperandStack().push(thisObject);

        thisObject.extra= createStackTraceElements(thisObject, frame.getThread());
    };



    public static void init() {
        NativeMethod.register("java/lang/Throwable", "fillInStackTrace",
                "(I)Ljava/lang/Throwable;", fillInStackTrace);

    }


    private static StackTraceElement[] createStackTraceElements(OObject thisObject, Thread thread) {


        //由于栈顶两帧正在执行fillInStackTrace（int）和fillInStackTrace（）方法，所以需要跳过这两帧。
        // 下面的几帧正在执行异常类的构造函数，所以也要跳过，具体要跳过多少帧数则要看异常类的继承层次
       int skipFrame=distanceToObject(thisObject.getClazz()) + 2;

       Frame[] frames=thread.getFrames(skipFrame);
        StackTraceElement[] stackTraceElements=new StackTraceElement[frames.length];
        for (int i = 0; i < stackTraceElements.length; i++) {
             stackTraceElements[i]=createStackTraceElement(frames[i]);
        }

        return stackTraceElements;
    }

    private static int distanceToObject(CClass clazz) {
      int  distance = 0;
        for (CClass c = clazz.getSuperClass(); c != null; c = c.getSuperClass()) {
            distance++;
        }
        return distance;
    }

    //根据帧 创建StackTraceElement实例
  private static   StackTraceElement createStackTraceElement( Frame frame)  {

        Method method = frame.getMethod();
        CClass clazz = method.getClazz();

        return new StackTraceElement(
                clazz.getSourceFile(),
                clazz.getJavaName(),
                method.getName(),
                method.getLineNumber(frame.getNextPC() - 1));
    }


}
