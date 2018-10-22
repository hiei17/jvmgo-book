package com.github.jvmgo;

import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.instructions.base.ClassInitLogic;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.LocalVars;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.*;

import java.util.List;

/**
 * @Author: panda
 * @Date: 2018/10/17 0017 23:58
 */
public class Jvm {

   private   Args cmd;
   private MyClassLoader classLoader;
   private Thread mainThread;

    public Jvm(Args args) {
        cmd=args;
        Classpath classpath = new Classpath(args.jre, args.classpath);
        classLoader = new MyClassLoader(classpath,args.printClassLoad);
        mainThread=new Thread();
    }

    public void start() {

        //加载VM类 执行static块 来初始化
        initVM();

        //执行主类的main（）方法
       execMain();
    }

    //先加载主类，然后执行其main（）方法
    private void execMain() {

        String className = cmd.getMainClass().replace(".", "/");
        CClass mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();

        if( mainMethod == null ){
            System.err.println("Main method not found in class "+ cmd.getMainClass());
            return;
        }


      //  public static void main(String[] arg) 的这个arg
        OObject argsArr=createArgsArray();

        Frame frame = mainThread.pushFrame(mainMethod);
        //调用main（）方法之前，需要给它传递args参数，这是通过直接操作局部变量表实现的。
        LocalVars localVars = frame.getLocalVars();
        localVars.setRef(0,argsArr);

        Interpret.execute(mainThread,cmd.printInstructExecute);
    }

    private OObject createArgsArray() {
        List<String> appArgs = cmd.getAppArgs();

        CClass stringClass = classLoader.loadClass("java/lang/String");

        ArrayClass arrayClass = stringClass.arrayClass();//String[]的class

        int size = appArgs.size();
        ArrayObject arrayObject = arrayClass.newArray(size);//String[] 对象
        for (int i = 0; i < size; i++) {
            //数组里面的元素一个个放进去
            arrayObject.set(i,StringPool.JString(classLoader,appArgs.get(i)));

        }
        return arrayObject;
    }


    private void initVM() {
        //先加载sun.mis.VM类
        CClass vmClass = classLoader.loadClass("sun/misc/VM");
        //类初始化方法 入虚拟机栈
        ClassInitLogic.init(mainThread,vmClass);

        //把现有的栈帧执行完 现在只有vm类初始化方法
        Interpret.execute(mainThread,cmd.printInstructExecute);

    }

}
