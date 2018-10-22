package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classFile.attributeInfo.SourceFileAttribute;
import com.github.jvmgo.rtda.heap.util.ClassNameHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

import static com.github.jvmgo.rtda.heap.Access_flags.*;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 20:53
 */
@Data
public class CClass {
    @Getter(AccessLevel.NONE)
    protected int accessFlags;// uint16 类的访问标志

    //完全限定名，具有java/lang/Object的形式
    protected   String name;// thisClassName
    private  String superClassName;
    private  String[] interfaceNames;
    private RuntimeConstantPool runtimeConstantPool;//存放运行时常量池指针
    private Field[] fields;
    private Method[] methods;

    //我用到的地方
    /**
     * 1.ldc 从常量池加载 入栈的是 class对象
     * 2.xxx.class xxx.getClass 得对象的class对象
     */
    public OObject jClass;//对应class对象
    private String sourceFile;

    /////////////////////////////////////////////////////////类加载里面时填充的
    public MyClassLoader classLoader;//加载时保留加载器引用
    public CClass superClass;
    public  CClass[] interfaces;
    public int instanceSlotCount;
    public int staticSlotCount;
    public   Object[] staticVars;

    //初始化标志
    public boolean initStarted=false;

    public CClass() {
    }

    public CClass(ClassFile cf, MyClassLoader myClassLoader) {
        try {
            classLoader=myClassLoader;
            accessFlags = cf.getAccessFlag();
            name = cf.getClassName();
            superClassName = cf.getSuperClassName();
            interfaceNames = cf.getInterfaceNames();


            //运行时常量池里面直接存field method 引用
            runtimeConstantPool = new RuntimeConstantPool(this, cf.getConstantPool());

            fields = Field.newFields( this,cf.getFields());
            methods = Method.newMethods( this,cf.getMethods());
            sourceFile=getSourceFile(cf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSourceFile(ClassFile cf) {
        SourceFileAttribute sfAttr = cf.getSourceFileAttribute();
        if ( sfAttr != null) {
            return sfAttr.getSourceFile();
        }
        //并不是每个class文件中都有源文件信息，这个因编译时的编译器选项而异
        return "Unknown";
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
        if(methods==null){
            return null;
        }
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

    public OObject newObject() {
        return new OObject(this);
    }


    public boolean isAccessibleTo(CClass userClass) {
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
        CClass clazz = (CClass) o;
        //必须类名和 类加载器 都是一个
        return Objects.equals(name, clazz.name) &&
                Objects.equals(classLoader, clazz.classLoader);
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

    //得到以本类为元素组成的数组类
    public ArrayClass arrayClass() {
       String arrayClassName= ClassNameHelper.getArrayClassName(name);

        return (ArrayClass) classLoader.loadClass(arrayClassName);
    }

    public Field getField(String fieldName, String fieldType) {

        for(CClass c=this;c.superClass!=null;c=c.getSuperClass()){

            for (Field field : c.getFields()) {
                if (field.getName().equals(fieldName)||field.getDescriptor().equals(fieldType)) {
                    return field;
                }
            }
        }
        return null;
    }


    public String getJavaName() {
        return name.replace('/','.');
    }

    public OObject newClassObject(CClass aClass) {
        OObject newClassObject=newObject();
        newClassObject.extra=aClass;
        return newClassObject;
    }

    public boolean isPrimitive() {
      return   ClassNameHelper.primitiveTypes.containsKey(name);
    }


    public void setStaticFieldValByNameAndType(String fieldName, String fieldType, Object object) {
        Field field= getField(fieldName,fieldType);
        staticVars[field.getSlotId()]=object;
    }
}
