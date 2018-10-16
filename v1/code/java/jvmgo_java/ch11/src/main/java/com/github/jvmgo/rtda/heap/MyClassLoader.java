package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;
import com.github.jvmgo.rtda.heap.util.ClassNameHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 0:04
 */
public class MyClassLoader  {
    
    private Classpath classpath;
    private boolean printClassLoad;

    //方法区
    public MyClassLoader(Classpath classpath, boolean printClassLoad) {
        this.classpath=classpath;
        this.printClassLoad=printClassLoad;

        //Class类的加载
        // 会触发bject类的加载 还有一些接口 都给他们补好Class实例
        loadBasicClasses();

        //void和基本类型 对应的Class类的实例 加载
        loadAllPrimitiveClasses();
    }

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



    private Map<String, CClass> classMap =new HashMap<>();

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
                OObject jClass = jClassClass.newClassObject(clazz);
                clazz.jClass= jClass;
            }
        }
        return clazz;
    }

    private CClass loadArrayClass(String className) {
       CClass result= new ArrayClass(className,this);
        classMap.put(className, result);
        return result;
    }

    private CClass loadNonArrayClass(String name) {
        //拿到数据
        byte[] data;
        try {
            data = classpath.readClass(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang,ClassNotFoundException");
        }

        //变class 放好相关类,set类加载器
        CClass classInfo = defineClass(data);

        //里面的field 分好slotId 如果是
        link(classInfo);

        return classInfo;
    }

    private void link(CClass classInfo) {
        //没做验证 
        
        prepare(classInfo);
    }

    private void prepare(CClass classInfo) {

        calcInstanceFieldSlotId(classInfo);
        calcStaticFieldSlotId(classInfo);

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

    private void calcInstanceFieldSlotId(CClass classInfo) {

       int slotId =0;

        if( classInfo.getSuperClass() != null) {
            slotId += classInfo.getSuperClass().instanceSlotCount;
        }
        for (Field field : classInfo.getFields()) {

            if(field.isStatic()){
                continue;
            }

            field.setSlotId(slotId++);

            if(field.isLongOrDouble()){
                slotId++;
            }

        }
        classInfo.instanceSlotCount=slotId;
    }

    private CClass defineClass(byte[] data)  {

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

        ClassFile classFile=new ClassFile(data);
        return new CClass(classFile,this);
    }
    
}
