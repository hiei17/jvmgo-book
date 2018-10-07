package com.github.jvmgo.rtda.heap;

import com.github.jvmgo.classFile.MemberInfo;
import com.github.jvmgo.classFile.attributeInfo.CodeAttribute;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: panda
 * @Date: 2018/10/6 0006 21:40
 */
@Getter
public class Method extends ClassMember{

  private int maxStack ;
  private int maxLocals ;
  private   byte[]  code ;

    private Method(MemberInfo memberInfo, JClass ownClass) {
        super(memberInfo,ownClass);
    }

    public static Method[] newMethods(JClass ownClass, MemberInfo[] memberInfos)  {

      return   Arrays.stream(memberInfos)
                .map(memberInfo->new Method(memberInfo,ownClass))
                .toArray(Method[]::new);

    }

     @Override
     void copyAttributes(MemberInfo info) {

        //暂时只处理Code属性
        CodeAttribute codeAttribute = info.getCodeAttr();
        if (codeAttribute==null){
            return;
        }
        maxLocals=codeAttribute.getMaxLocals();
        maxStack=codeAttribute.getMaxStack();
        code=codeAttribute.getCode();
    }
}
