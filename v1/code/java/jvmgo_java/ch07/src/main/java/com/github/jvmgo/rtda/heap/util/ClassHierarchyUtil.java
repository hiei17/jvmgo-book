package com.github.jvmgo.rtda.heap.util;

import com.github.jvmgo.rtda.heap.JClass;

/**
 * @Author: panda
 * @Date: 2018/10/8 0008 19:30
 */
public class ClassHierarchyUtil {

    public static boolean isAssignableFrom(JClass self,JClass smallClass) {
        if (self.equals(smallClass)){
            return true;
        }

        if( !self.isInterface()) {
            return isSubClassOf(smallClass,self);
        } else {
            return isImplements(smallClass,self);
        }
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
