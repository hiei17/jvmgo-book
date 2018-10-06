package com.github.jvmgo;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.constantPool.ConstantInfo;
import com.github.jvmgo.classFile.constantPool.ConstantPool;
import com.github.jvmgo.classpath.Classpath;

public class Main {


    private ClassFile cf;

    public static void main(String[] argv) {
        Args args = Args.parse(argv);
        if (!args.ok || args.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
        } else if (args.versionFlag) {
            System.out.println("java version \"1.8.0\"");
        } else {
            startJVM(args);
        }
    }

    private static void startJVM(Args args) {
        Classpath cp = new Classpath(args.jre, args.classpath);
        System.out.printf("classpath:%s class:%s args:%s\n",
                cp, args.getMainClass(), args.getAppArgs());

        String className = args.getMainClass().replace(".", "/");
        try {
            byte[] classData = cp.readClass(className);

            ClassFile classFile= new ClassFile(classData);
           MemberInfo mainMethod = getMainMethod(classFile);

            new Interpret(mainMethod);

           
        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.out.println("Could not find or load main class " + args.getMainClass());
        }
    }



    private static MemberInfo getMainMethod(ClassFile classFile) {
        for (MemberInfo method : classFile.getMethods()) {
            String methodName=method.getName();
            String methodNameDescriptor=method.getDescriptor();

            if( "main".equals(methodName)&&"([Ljava/lang/String;)V".equals(methodNameDescriptor)){
                return method;
            }
        }

        return null;
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: "+ cf.getMajorVersion()+"."+cf.getMinorVersion());

        ConstantPool constantPool = cf.getConstantPool();
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        System.out.println("constants count: "+ constantPool.getConstantPoolSize());
        for(int i = 1; i < constantPool.getConstantPoolSize() ; i++) {

           if(constantInfos[i]!=null){
               System.out.println(i+": "+constantInfos[i]);
           }
        }


        System.out.format("access flags: 0x%x\n", cf.getAccessFlag());
        System.out.println("this class: "+ constantPool.getUTF8(cf.getClassNameIndex()));
        System.out.println("super class: "+ constantPool.getUTF8(cf.getSuperClassNameIndex()));
        System.out.println("interfaces: "+cf.getInerfaceindexes().length);
        MemberInfo[] fields = cf.getFields();
        System.out.println("fields count: "+ fields.length);
        for (MemberInfo memberInfo : fields) {
            System.out.format("  %s\n", constantPool.getUTF8(memberInfo.getNameIndex()));
        }

        MemberInfo[] methods = cf.getMethods();
        System.out.println("methods count: "+ methods.length);
        for (MemberInfo memberInfo : methods) {
            System.out.format("  %s\n", constantPool.getUTF8(memberInfo.getNameIndex()));
        }

    }


    public static void panic(String msg) {
    	System.out.println(msg);
    	System.exit(0);
    }

}
