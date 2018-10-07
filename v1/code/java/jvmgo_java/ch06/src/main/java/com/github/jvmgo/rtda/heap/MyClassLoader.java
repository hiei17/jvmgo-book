package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classpath.Classpath;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panda
 * @Date: 2018/10/7 0007 0:04
 */
public class MyClassLoader  {
    
    private Classpath classpath;

    //方法区
    private Map<String, JClass> classInfoMap=new HashMap<>();

    public MyClassLoader(Classpath classpath) {
        this.classpath=classpath;
    }

    public JClass loadClass(String className){

        JClass classInfo = classInfoMap.get(className);

        if (classInfo==null){
            classInfo=  loadNonArrayClass(className);
            classInfoMap.put(className,classInfo);
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

        System.out.format("[Loaded %s ]\n", name);

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
                initStaticFinalVar(staticVars,classInfo.runtimeConstantPool, field);
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

        classInfo.setStaticSlotCount(slotId);
    }

    private void calcInstanceFieldSlotId(JClass classInfo) {

       int slotId =0;

        if( classInfo.superClass != null) {
            slotId += classInfo.superClass.instanceSlotCount;
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
        classInfo.setInstanceSlotCount(slotId);
    }

    private JClass defineClass(byte[] data)  {

        //byte[] -> classFile ->class
        JClass jClass = parseClass(data);



        jClass.interfaces = resolveInterfaces(jClass.interfaceNames);
        jClass.superClass = resolveSuperClass(jClass.superClassName);

        return jClass;
    }

    private  JClass[] resolveInterfaces(String[] interfaceNames)  {


        JClass[] interfaces= new JClass[interfaceNames.length];

        for (int i = 0; i < interfaces.length; i++) {

            interfaces[i]=loadClass(interfaceNames[i]);
        }
        return interfaces;
    }

    private JClass resolveSuperClass(String superClassName){
        if ( ! "java/lang/Object".equals(superClassName)) {
           return loadClass(superClassName);
        }
        return null;
    }

    private JClass parseClass(byte[] data) {

        ClassFile classFile=new ClassFile(data);
        return new JClass(classFile,this);
    }


}
