package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.rtda.heap.util.ClassHierarchyUtil;
import lombok.Getter;

import static com.github.jvmgo.rtda.heap.Access_flags.*;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 21:12
 */
@Getter
public abstract class ClassMember {
    protected     int accessFlags ;
    protected    String  name;
    public     String  descriptor;

    protected CClass clazz;

    public ClassMember(MemberInfo memberInfo, CClass clazz) {
        accessFlags = memberInfo.getAccessFlags();
        name = memberInfo.getName();
        descriptor = memberInfo.getDescriptor();
        this.clazz=clazz;
        copyAttributes(   memberInfo);
    }


    
    
    //accessFlags 6个 
   public boolean isPublic()  {
        return 0 != (accessFlags&ACC_PUBLIC);
    }
    public  boolean isPrivate()  {
        return 0 != (accessFlags&ACC_PRIVATE);
    }
    public  boolean isProtected()  {
        return 0 != (accessFlags&ACC_PROTECTED);
    }
    public  boolean isStatic()  {
        return 0 != (accessFlags&ACC_STATIC);
    }
    public boolean isFinal()  {
        return 0 != (accessFlags&ACC_FINAL);
    }
    public  boolean isSynthetic()  {
        return 0 != (accessFlags&ACC_SYNTHETIC);
    }

    public boolean isAccessibleTo(CClass userClass) {
        if (isPublic() ){
            return true;
        }

        if (!isPrivate() ){
            if( clazz.getPackageName().equals(userClass.getPackageName())) {
                return true;
            }
        }

        if( isProtected()) {
            //用的类是field所属类的子类
            if(ClassHierarchyUtil.isSubClassOf(userClass,clazz)){
                return true;
            }
        }

        return clazz.equals(userClass);
    }

    abstract void copyAttributes( MemberInfo info) ;


    @Override
    public String toString() {
        return clazz.getName()+name+descriptor;
    }
}
