package com.github.jvmgo.instructions.math;

/**
 * @Author: panda
 * @Date: 2018/10/5 0005 15:27
 */
public enum OperatorEnum {
    add,
    sub,
    mul,
    div,
    rem,
    //neg,
    and,
    or,
    xor;


   int getResult(int a,int b){
       switch (this){
           case add:return  b+a;
           case sub:return  b-a;
           case mul:return  b*a;
           case div:return  b/a;//"java.lang.ArithmeticException: / by zero"
           case rem:return  b%a;//"java.lang.ArithmeticException: / by zero"
           case and:return  b&a;
           case or:return  b|a;
           case xor:return  b^a;
       }
       return 0;
   }

   Long getResult(long a,long b){

       switch (this){
           case add:return  b+a;
           case sub:return  b-a;
           case mul:return  b*a;
           case div:return  b/a;//"java.lang.ArithmeticException: / by zero"
           case rem:return  b%a;//"java.lang.ArithmeticException: / by zero"
           case and:return  b&a;
           case or:return  b|a;
           case xor:return  b^a;
       }
       return 0L;
   }

   float getResult(float a,float b){

       switch (this){
           case add:return  b+a;
           case sub:return  b-a;
           case mul:return  b*a;
           case div:return  b/a;
           case rem:return  b%a;
       }
       return 0;
   }

   double getResult(double a,double b){

       switch (this){
           case add:return  b+a;
           case sub:return  b-a;
           case mul:return  b*a;
           case div:return  b/a;
           case rem:return  b%a;
       }
       return 0;
   }



}
