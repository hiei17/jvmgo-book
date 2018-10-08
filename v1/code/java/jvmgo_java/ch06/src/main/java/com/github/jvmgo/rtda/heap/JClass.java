package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Objects;

import static com.github.jvmgo.rtda.heap.Access_flags.*;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 20:53
 */
@Getter
public class JClass {
    @Getter(AccessLevel.NONE)
    private int accessFlags;// uint16 类的访问标志

    //完全限定名，具有java/lang/Object的形式
    private  String name;// thisClassName
    private  String superClassName;
    private  String[] interfaceNames;
    private RuntimeConstantPool runtimeConstantPool;//存放运行时常量池指针
    private Field[] fields;
    private Method[] methods;

/////////////////////////////////////////////////////////类加载里面时填充的
    public MyClassLoader classLoader;//加载时保留加载器引用
    public  JClass superClass;
    public  JClass[] interfaces;
    public int instanceSlotCount;
    public int staticSlotCount;
    public   Object[] staticVars;


    public JClass(ClassFile cf, MyClassLoader myClassLoader) {
        try {
            classLoader=myClassLoader;
            accessFlags = cf.getAccessFlag();
            name = cf.getClassName();
            superClassName = cf.getSuperClassName();
            interfaceNames = cf.getInterfaceNames();


            fields = Field.newFields( this,cf.getFields());
            methods = Method.newMethods( this,cf.getMethods());

            //运行时常量池里面直接存field method 引用
            runtimeConstantPool = new RuntimeConstantPool(this, cf.getConstantPool());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //8个 标志位是否被设置
   public boolean isPublic() {
        return 0 != (accessFlags & ACC_PUBLIC);
    }

    public boolean isFinal() {
        return 0 != (accessFlags & ACC_SUPER);
    }

    public  boolean isInterface() {
        return 0 != (accessFlags & ACC_INTERFACE);
    }

    public  boolean isAbstract() {
        return 0 != (accessFlags & ACC_ABSTRACT);
    }

    public  boolean isSynthetic() {
        return 0 != (accessFlags & ACC_SYNTHETIC);
    }

    public  boolean isAnnotation() {
        return 0 != (accessFlags & ACC_ANNOTATION);
    }

    public boolean isEnum() {
        return 0 != (accessFlags & ACC_ENUM);
    }

    public Method getMainMethod() {
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    public Method getStaticMethod(String name,String descriptor ){

        for (Method method : methods) {
            if(method.isStatic()){
                if(method.name.equals(name)&&method.getDescriptor().equals(descriptor)){
                    return method;
                }
            }
        }

        return null;
    }

    public boolean isSubClassOf(JClass mayBeSuper) {
        for( JClass c = superClass; c != null; c = c.superClass ){
            if (c == mayBeSuper) {
                return true;
            }
        }
        return false;
    }

    public String getPackageName() {
        int i = name.lastIndexOf("/");
        if (i > 0) {
            return name.substring(0, i);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JClass jClass = (JClass) o;
        return Objects.equals(name, jClass.name) &&
                Objects.equals(classLoader, jClass.classLoader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, classLoader);
    }

    public Object newObject() {
        return new JObject(this);
    }

    public boolean isAssignableFrom(JClass smallClass) {
        if (this.equals(smallClass)){
            return true;
        }

        if( !this.isInterface()) {
            return smallClass.isSubClassOf(this);
        } else {
            return smallClass.isImplements(this);
        }

    }

    // self implements iface
    private boolean isImplements(JClass smallClass) {
        for (JClass c = this; c != null; c = c.superClass ){
            for (JClass anInterface : c.getInterfaces()) {
                if( smallClass == anInterface || smallClass.isSubInterfaceOf(anInterface) ){
                    return true;
                }
            }

        }
        return false;
    }
    public boolean isSubInterfaceOf(JClass iface) {
        for (JClass superInterface : interfaces) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAccessibleTo(JClass userClass) {

            return isPublic() || getPackageName().equals(userClass.getPackageName());

    }
}
