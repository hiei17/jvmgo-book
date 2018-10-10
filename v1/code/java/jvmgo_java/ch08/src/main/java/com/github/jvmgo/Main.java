package com.github.jvmgo;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.constantPool.ConstantInfo;
import com.github.jvmgo.classFile.constantPool.ConstantPool;
import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.rtda.heap.JClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.MyClassLoader;

public class Main {




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
        Classpath classpath = new Classpath(args.jre, args.classpath);
        MyClassLoader classLoad = new MyClassLoader(classpath,args.printClassLoad);

        String className = args.getMainClass().replace(".", "/");
        try {

            JClass mainClass = classLoad.loadClass(className);

            Method mainMethod = mainClass.getMainMethod();

            Interpret.execute(mainMethod,args.printInstructExecute);


        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.out.println("Could not find or loadClass main class " + args.getMainClass());
        }
    }




    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.getMajorVersion() + "." + cf.getMinorVersion());

        ConstantPool constantPool = cf.getConstantPool();
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        System.out.println("constants count: " + constantPool.getConstantPoolSize());
        for (int i = 1; i < constantPool.getConstantPoolSize(); i++) {

            if (constantInfos[i] != null) {
                System.out.println(i + ": " + constantInfos[i]);
            }
        }


        System.out.format("access flags: 0x%x\n", cf.getAccessFlag());
        System.out.println("this class: " + constantPool.getUTF8(cf.getClassNameIndex()));
        System.out.println("super class: " + constantPool.getUTF8(cf.getSuperClassNameIndex()));
        System.out.println("interfaces: " + cf.getInterfaceIndexes().length);
        MemberInfo[] fields = cf.getFields();
        System.out.println("fields count: " + fields.length);
        for (MemberInfo memberInfo : fields) {
            System.out.format("  %s\n", constantPool.getUTF8(memberInfo.getNameIndex()));
        }

        MemberInfo[] methods = cf.getMethods();
        System.out.println("methods count: " + methods.length);
        for (MemberInfo memberInfo : methods) {
            System.out.format("  %s\n", constantPool.getUTF8(memberInfo.getNameIndex()));
        }

    }


    public static void panic(String msg) {
        System.err.println(msg);
        System.exit(0);
    }

}
