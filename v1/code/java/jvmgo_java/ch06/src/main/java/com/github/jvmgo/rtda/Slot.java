package com.github.jvmgo.rtda;

import com.github.jvmgo.util.Util;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 13:08
 */

public class Slot {

   public byte[] bytes=new byte[4];
   public Object ref=new Object();

   @Override
   public String toString() {
      return Util.byteToInt(bytes)+"" ;

   }
}
