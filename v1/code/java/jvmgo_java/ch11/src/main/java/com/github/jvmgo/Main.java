package com.github.jvmgo;

public class Main {



    public static void main(String[] argv) {
        Args args = Args.parse(argv);
        if (!args.ok || args.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
        } else if (args.versionFlag) {
            System.out.println("java version \"1.8.0\"");
        } else {
           ( new Jvm(args)).start();
        }
    }



}
