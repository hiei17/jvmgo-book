package com.github.jvmgo.rtda;

import com.github.jvmgo.rtda.heap.JObject;
import com.github.jvmgo.util.Util;

/**
 * @Author: panda
 * @Date: 2018/10/4 0004 13:08
 */

public class Slot {

   public byte[] bytes=new byte[4];
   public JObject ref;

   @Override
   public String toString() {
      if (ref!=null){
         return ref+"";
      }
      return Util.byteToInt(bytes)+"" ;

   }
}
