package com.github.jvmgo;

import com.github.jvmgo.classFile.ClassFile;
import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.constantPool.ConstantInfo;
import com.github.jvmgo.classFile.constantPool.ConstantPool;
import com.github.jvmgo.classpath.Classpath;
import com.github.jvmgo.rtda.heap.CClass;
import com.github.jvmgo.rtda.heap.Method;
import com.github.jvmgo.rtda.heap.MyClassLoader;

public class Main {



    //-verbose:inst -verbose -cp "D:\develop\bookCode\jvmgo-book\v1\code\java\example\out\production\classes" jvmgo
    // .book.ch09.GetClassTest   测反射

    /**
     *
     * System.out.println(void.class.getName()); // void
     * System.out.println(boolean.class.getName()); // boolean
     * System.out.println(byte.class.getName()); // byte
     * System.out.println(char.class.getName()); // char
     * System.out.println(short.class.getName()); // short
     * System.out.println(int.class.getName()); // int
     * System.out.println(long.class.getName()); // long
     * System.out.println(float.class.getName()); // float
     * System.out.println(double.class.getName()); // double
     * System.out.println(Object.class.getName()); // java.lang.Object
     * System.out.println(int[].class.getName()); // [I
     * System.out.println(int[][].class.getName()); // [[I
     * System.out.println(Object[].class.getName()); // [Ljava.lang.Object;
     * System.out.println(Object[][].class.getName()); // [[Ljava.lang.Object;
     * System.out.println(Runnable.class.getName()); // java.lang.Runnable
     * System.out.println("abc".getClass().getName()); // java.lang.String
     * System.out.println(new double[0].getClass().getName()); // [D
     * System.out.println(new String[0].getClass().getName()); //[Ljava.lang.String
     */

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

            CClass mainClass = classLoad.loadClass(className);

            Method mainMethod = mainClass.getMainMethod();

            Interpret.execute(mainMethod,args.printInstructExecute,args.getAppArgs());


        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.out.println("Could not find or loadClass main class " + args.getMainClass());
            e.printStackTrace();
        }
    }
//-verbose:inst -verbose -cp "D:\develop\bookCode\jvmgo-book\v1\code\java\example\out\production\classes" jvmgo.book.ch09.StringTest



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
