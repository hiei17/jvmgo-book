package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.JClass;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 19:30
 */
public class ClassHierarchyUtil {

    public static boolean isAssignableFrom(JClass high,JClass low) {

        if (high.equals(low)){
            return true;
        }

        String highName = high.getName();
        if ("java/lang/Object".equals(highName)) {
            return true;
        }

        if(low instanceof ArrayClass){
            if(! (high instanceof ArrayClass)){
                return "java/lang/Cloneable".equals(highName) || "java/io/Serializable".equals(highName);
            }

            //2者都是数组
            JClass lowCompent = ((ArrayClass) low).componentClass();
            JClass highCompent = ((ArrayClass) high).componentClass();
            return lowCompent.equals(highCompent)||isAssignableFrom(highCompent,lowCompent);
        }

        if (low.isInterface()) {
            if(high.isInterface()){
                return isSubInterfaceOf(low,high);
            }
            return false;
        }


        if( !high.isInterface()) {
            return isSubClassOf(low,high);
        }

        return isImplements(low,high);

    }

    // self implements iface
    public static boolean isImplements(JClass self,JClass smallClass) {
        for (JClass c = self; c != null; c = c.superClass ){
            for (JClass anInterface : c.getInterfaces()) {
                if( smallClass == anInterface || ClassHierarchyUtil.isSubInterfaceOf(smallClass,anInterface) ){
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean isSubInterfaceOf(JClass self,JClass iface) {
        for (JClass superInterface :self.getInterfaces() ) {
            if (superInterface == iface || isSubInterfaceOf(superInterface,iface)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubClassOf(JClass self,JClass mayBeSuper) {
        for( JClass c = self.superClass; c != null; c = c.superClass ){
            if (c == mayBeSuper) {
                return true;
            }
        }
        return false;
    }

}
