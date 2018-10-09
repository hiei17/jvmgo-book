package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.rtda.heap.ref.RuntimeConstantPool;

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
    }

    private Map<String, JClass> classInfoMap=new HashMap<>();

    public JClass loadClass(String className){

        JClass classInfo = classInfoMap.get(className);

        if (classInfo==null){
            classInfo=  loadNonArrayClass(className);
            if(printClassLoad){
                System.out.format("[Loaded %s ]\n",className);
            }
        }
        return classInfo;
    }

    private JClass loadNonArrayClass(String name) {
        //拿到数据
        byte[] data;
        try {
            data = classpath.readClass(name);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang,ClassNotFoundException");
        }

        //变class 放好相关类,set类加载器
        JClass classInfo = defineClass(data);

        //里面的field 分好slotId 如果是
        link(classInfo);

        return classInfo;
    }

    private void link(JClass classInfo) {
        //没做验证 
        
        prepare(classInfo);
    }

    private void prepare(JClass classInfo) {

        calcInstanceFieldSlotId(classInfo);
        calcStaticFieldSlotId(classInfo);

        allocAndInitStaticVars(classInfo);
    }

    private void allocAndInitStaticVars(JClass classInfo) {
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
        staticVars[field.getSlotId()]=constant;
    }



    private void calcStaticFieldSlotId(JClass classInfo) {
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

    private void calcInstanceFieldSlotId(JClass classInfo) {

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

    private JClass defineClass(byte[] data)  {

        //byte[] -> classFile ->class
        JClass jClass = parseClass(data);



        jClass.interfaces = resolveInterfaces(jClass.getInterfaceNames());
        if ( ! "java/lang/Object".equals(jClass.getName())) {
            jClass.superClass = loadClass(jClass.getSuperClassName());
        }
        classInfoMap.put(jClass.getName(),jClass);
        return jClass;
    }

    private  JClass[] resolveInterfaces(String[] interfaceNames)  {


        JClass[] interfaces= new JClass[interfaceNames.length];

        for (int i = 0; i < interfaces.length; i++) {

            interfaces[i]=loadClass(interfaceNames[i]);
        }
        return interfaces;
    }



    private JClass parseClass(byte[] data) {

        ClassFile classFile=new ClassFile(data);
        return new JClass(classFile,this);
    }


}
