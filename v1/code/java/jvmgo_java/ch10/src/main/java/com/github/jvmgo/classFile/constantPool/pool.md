| classFile里的常量池        | 运行时常量池  | 
| --------   | -----:  | 
| 数字:i l f d        | 原样      |    
| UTF8        |       |       |
| STRING(引用UTF8)        |  字符串     |    
| ClASS(引用UTF8)        |  类名     |      
| NAME_AND_TYPE(引用UTF8)        |      |      
| FIELD_REF(引用ClASS,NAME_AND_TYPE)        |  field引用(类,成员名,描述)    |      
| METHOD_REF(引用ClASS,NAME_AND_TYPE)        |  method引用(类,成员名,描述)    |     
| INTERFACE_METHOD_REF(引用ClASS,NAME_AND_TYPE)        |  method引用(类,成员名,描述)    |       