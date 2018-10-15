package com.github.jvmgo.instructions.references;

import com.github.jvmgo.instructions.base.NoOperandsInstruction;
import com.github.jvmgo.rtda.Frame;
import com.github.jvmgo.rtda.OperandStack;
import com.github.jvmgo.rtda.Thread;
import com.github.jvmgo.rtda.heap.OObject;
import com.github.jvmgo.rtda.heap.StringPool;

/**
 * @Author: panda
 * @Date: 2018/10/15 0015 20:45
 *
 * 异常抛出指令,
 */
public class Athrow extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        //前面的指令会保证把异常对象放在栈顶
        //异常对象引用
        OObject exObject = frame.getOperandStack().popRef();

        if( exObject == null ){
            throw  new NullPointerException();
        }

        Thread thread = frame.getThread();

        //可以找到并跳转到异常处理代码
        if (!findAndGotoExceptionHandler(thread, exObject)) {

            //遍历完Java虚拟机栈还是找不到catch

            //直接打印异常信息
            handleUncaughtException(thread, exObject);
        }
    }

    private boolean findAndGotoExceptionHandler(Thread thread, OObject exObject) {

        //从当前帧开始，遍历Java虚拟机栈，查找方法的异常处理表。
        while (!thread.isStackEmpty()){
            //当前帧的所属方法 有没有写过catch 这个位置的这个类型异常
           Frame frame = thread.currentFrame();
           int  pc = frame.getNextPC() - 1;

           int handlerPC = frame.getMethod().findExceptionHandler(exObject.getClazz(), pc);

            //如果找到了异常处理项
            if (handlerPC > 0) {
                OperandStack operandStack = frame.getOperandStack();
                //操作数栈清空
                operandStack.clear();

                //异常对象引用推入栈顶
                operandStack.push(exObject);

                //跳转到异常处理代码
                frame.setNextPC(handlerPC);
                return true;
            }

            //找不到异常处理项，本方法没有写这个catch  只能把异常往上抛 看上一层方法有没有处理
            // 则把frame弹出，继续遍历。
            thread.popFrame();

        }//while end
        return false;
    }


    //打印出Java虚拟机栈信息
    private void handleUncaughtException( Thread thread,OObject ex ) {

        //Java虚拟机栈清空  Java虚拟机栈已经空了，所以解释器也就终止执行了
        thread.clearnStack();

        //得异常对象的这个字段值 转成go字符串
        OObject  jMsg = (OObject)ex.getFieldVar("detailMessage", "Ljava/lang/String;");
        String  goMsg = StringPool.getString(jMsg);

        //打印 异常类名:异常信息
        System.err.println(ex.getClazz().getJavaName()+":"+goMsg);



        // 被放在extra里面了
        StackTraceElement[] ste=  (StackTraceElement[])ex.extra;
        for (StackTraceElement stackTraceElement : ste) {
            System.err.println(stackTraceElement);
        }


    }

}
