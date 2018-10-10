package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.rtda.heap.util.ClassNameHelper;
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
    protected int accessFlags;// uint16 类的访问标志

    //完全限定名，具有java/lang/Object的形式
    protected   String name;// thisClassName
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

    //初始化标志
    public boolean initStarted=false;

    public JClass() {
    }

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
    public  boolean IsSuper() {
        return 0 != (accessFlags & ACC_SUPER);
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
        Method main = getMethod("main", "([Ljava/lang/String;)V");
        if (main.isStatic()) {
            return main;
        }
        return null;
    }

    public Method getMethod(String name,String descriptor ){

        for (Method method : methods) {
            if(method.name.equals(name)&&method.getDescriptor().equals(descriptor)){
                return method;
            }
        }
        return null;
    }



    public String getPackageName() {
        int i = name.lastIndexOf("/");
        if (i > 0) {
            return name.substring(0, i);
        }
        return "";
    }

    public Object newObject() {
        return new JObject(this);
    }


    public boolean isAccessibleTo(JClass userClass) {
        return isPublic() || getPackageName().equals(userClass.getPackageName());
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

    @Override
    public String toString() {
        return  name ;

    }

    public Method getClinitMethod() {

        Method method = getMethod("<clinit>", "()V");
        assert method.isStatic();
        return method;
    }


    public ArrayClass arrayClass() {
       String arrayClassName= ClassNameHelper.getArrayClassName(name);

        return (ArrayClass) classLoader.loadClass(arrayClassName);
    }

}
