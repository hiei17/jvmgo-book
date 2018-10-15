package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.ArrayClass;
import com.github.jvmgo.rtda.heap.CClass;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 19:30
 */
public class ClassHierarchyUtil {

    public static boolean isAssignableFrom(CClass high, CClass low) {

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
            CClass lowCompent = ((ArrayClass) low).componentClass();
            CClass highCompent = ((ArrayClass) high).componentClass();
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
    public static boolean isImplements(CClass self, CClass smallClass) {
        for (CClass c = self; c != null; c = c.superClass ){
            for (CClass anInterface : c.getInterfaces()) {
                if( smallClass == anInterface || ClassHierarchyUtil.isSubInterfaceOf(smallClass,anInterface) ){
                    return true;
                }
            }

        }
        return false;
    }

    public static boolean isSubInterfaceOf(CClass self, CClass iface) {
        for (CClass superInterface :self.getInterfaces() ) {
            if (superInterface == iface || isSubInterfaceOf(superInterface,iface)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSubClassOf(CClass self, CClass mayBeSuper) {
        for(CClass c = self.superClass; c != null; c = c.superClass ){
            if (c == mayBeSuper) {
                return true;
            }
        }
        return false;
    }


}
