package jvmgo.book.ch07;

public class InvokeVirtualTest {

   static Vector2D v2;
   static Vector2D v3;
    public static void main(String[] args) {
        v2 = new Vector2D(2.1, 2.2);
        v3 = new Vector3D(3.1, 3.2, 3.3);
        v2.multiply(2);
        v3.multiply(3);
        System.out.println(v2.x);
        System.out.println(v2.y);
        System.out.println(v3.x);
        System.out.println(v3.y);
        System.out.println(((Vector3D)v3).z);
    }

}
