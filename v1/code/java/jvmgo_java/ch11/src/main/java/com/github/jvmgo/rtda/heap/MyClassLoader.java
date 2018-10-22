package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.rtda.heap.util.ClassNameHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 0:04
 *类加载器 可有用户自己继承ClassLoad 重写loadClass方法就行
 * 好处:
 * 高度灵活
 * 热部署
 * 代码加密
 */
public class MyClassLoader  {
    
    private Classpath classpath;
    //mark 方法区: 被加载的类信息,运行时常量池,常量,类变量, 即时编译器编译后的代码
    private Map<String, CClass> classMap =new HashMap<>();
    /*
    "void":    "V",
	"boolean": "Z",
	"byte":    "B",
	"short":   "S",
	"int":     "I",
	"long":    "J",
	"char":    "C",
	"float":   "F",
	"double":  "D",
    * */

    private boolean printClassLoad;



    public MyClassLoader(Classpath classpath, boolean printClassLoad) {
        this.classpath=classpath;
        this.printClassLoad=printClassLoad;

        //Class类的加载
        // 会触发bject类的加载 还有一些接口 都给他们补好Class实例
        loadBasicClasses();

        //void和基本类型 对应的Class类的实例 加载
        loadAllPrimitiveClasses();
    }

    private void loadAllPrimitiveClasses() {
        for (String primitiveType : ClassNameHelper.primitiveTypes.keySet()) {

          //  void和基本类型的类名就是void、int、float等
            loadPrimitiveClass(primitiveType);
        }

    }

    private void loadPrimitiveClass(String primitiveClassName ) {

        //对应的class的类信息
        PrimitiveClass primitiveClass=  new PrimitiveClass(primitiveClassName,this);

        CClass classClass =  loadClass("java/lang/Class");

        //基础类的对应class对象
        primitiveClass.jClass= classClass.newClassObject(primitiveClass);

       classMap.put(primitiveClassName,primitiveClass);

    }

    private void loadBasicClasses() {

        //先加载java.lang.Class类，这又会触发java.lang.Object等类和接口的加载。

        CClass classClass =  loadClass("java/lang/Class");
        // 然后遍历classMap，给已经加载的每一个类关联类对象
        classMap.values().stream()
                .filter(c->c.jClass==null)
                .forEach(aClass-> aClass.jClass= classClass.newClassObject(aClass)
                );
    }


    //懒加载
    public CClass loadClass(String className){

        CClass clazz = classMap.get(className);

        if (clazz==null){
            if(className.charAt(0)=='['){//[java/lang/String这样[开头是数组类
                clazz=loadArrayClass(className);
            }else {
                clazz=  loadNonArrayClass(className);
            }
            if(printClassLoad){
                System.out.format("[Loaded %s ]\n",className);
            }


            CClass jClassClass = classMap.get("java/lang/Class");
            //加载Object类的时候是没的 它也是Class类的父类 还有其他一些接口 会比Class类还想加载
            if(jClassClass!=null){//没有就算了 不然可以循环调用了
                //加载它的对应class
                clazz.jClass= jClassClass.newClassObject(clazz);
            }
        }
        return clazz;
    }

    private CClass loadArrayClass(String className) {

        CClass result= new ArrayClass(className,this);
        classMap.put(className, result);
        return result;
    }

 //mark 加载和验证 不是串行先后的顺序 有重叠
    private CClass loadNonArrayClass(String name) {


        //mark 加载1 拿到二进制流(规范没有规定 从哪来
        // 一般是从.class文件
        // 只要是符合规范的就行
        // 网络,计算生成(代理$proxy文件, jsp) ,其他文件(jar
        byte[] data;
        try {
            data = classpath.readClass(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang,ClassNotFoundException");
        }

        //变class 放好相关类(接口 父类),set类加载器
        CClass classInfo = defineClass(data);


        //里面的field 分好slotId 如果是
        link(classInfo);

        return classInfo;
    }

    private void link(CClass classInfo) {
        // mark 连接1 校验
        //校验:元数据 字节码 符号引用

        //mark 连接2 准备
        prepare(classInfo);

        //mark 连接3 解析
        //规范对时机没规定
        //我在 几个xxxRef文件 里面做
    }

    private void prepare(CClass classInfo) {

        //非static的field 分配空间
        calcInstanceFieldSlotId(classInfo);

        //类变量 分配内存,设默认初值
        // 如 int 是 0
        //如果 static int i = 1; 这样还是 0 赋值1要在类初始化里面
        calcStaticFieldSlotId(classInfo);

        //final 类变量 (就是常量)  从常量池拿出值 赋值
        allocAndInitStaticVars(classInfo);
    }

    private void allocAndInitStaticVars(CClass classInfo) {
        Object[] staticVars=new Object[classInfo.staticSlotCount];
        for (Field field : classInfo.getFields()) {

            if(field.isStatic()&&field.isFinal()){
                initStaticFinalVar(staticVars,classInfo.getRuntimeConstantPool(), field);
            }
        }
        classInfo.staticVars=staticVars;
    }

    //field 赋初值(classFile得
    private void initStaticFinalVar(Object[] staticVars, RuntimeConstantPool runtimeConstantPool, Field field) {
        int constValueIndex = field.constValueIndex;
        if (constValueIndex<0){
            return;
        }
        Object constant = runtimeConstantPool.getConstant(constValueIndex);
        if(constant instanceof String){
            constant = StringPool.JString(this,(String) constant);
        }
        staticVars[field.getSlotId()]=constant;
    }



    private void calcStaticFieldSlotId(CClass classInfo) {
        int slotId =0;

        for (Field field : classInfo.getFields()) {

            if(!field.isStatic()){
                continue;
            }


            field.setSlotId(slotId++);
            if(field.isLongOrDouble()){
                slotId++;
            }

        }

        classInfo.staticSlotCount=slotId;
    }

    //计算非static的field 要多少空间 并分配空间 field里面存所分配到空间的index
    private void calcInstanceFieldSlotId(CClass classInfo) {

       int slotId =0;

        if( classInfo.getSuperClass() != null) {
            slotId += classInfo.getSuperClass().instanceSlotCount;
        }
        for (Field field : classInfo.getFields()) {

            if(field.isStatic()){
                continue;
            }

            //我这么实现 按field原来出现的顺序分配 但是不一定要这样
            field.setSlotId(slotId++);

            if(field.isLongOrDouble()){
                slotId++;
            }

        }
        classInfo.instanceSlotCount=slotId;
    }

    private CClass defineClass(byte[] data)  {

        // mark 加载2 字节流代表的静态存储结构 转换为 方法区的运行时数据结构(具体无规定
        // mark 加载3 内存中(一般放在方法区中)生成代表这个类的 Class对象,作为这个类各种数据访问的入口
        //byte[] -> classFile ->class
        CClass clazz=parseClass(data);

        clazz.interfaces = resolveInterfaces(clazz.getInterfaceNames());
        if ( ! "java/lang/Object".equals(clazz.getName())) {
            clazz.superClass = loadClass(clazz.getSuperClassName());
        }
        classMap.put(clazz.getName(),clazz);
        return clazz;
    }

    private  CClass[] resolveInterfaces(String[] interfaceNames)  {

        CClass[] interfaces= new CClass[interfaceNames.length];

        for (int i = 0; i < interfaces.length; i++) {

            interfaces[i]=loadClass(interfaceNames[i]);
        }
        return interfaces;
    }



    private CClass parseClass(byte[] data) {

        // mark 连接1 校验 文件格式
        // 校验是: 为了防止以后运行的差错和病毒(可设置跳过验证
        //编译器谁都可以写 甚至没有编译器 直接来字节流
        //文件格式 是在 加载里面, 字节流转class这种运行时数据结构时  顺便校验
        ClassFile classFile=new ClassFile(data);

        return new CClass(classFile,this);
    }
    
}
