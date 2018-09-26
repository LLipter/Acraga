# Acraga
An interpreter designed for Acraga language, which is a super simple, strong-type, c-like programming language.

# Acraga 语言概述


Acraga是一种简单的强类型编程语言，支持整数、实数、布尔值、字符串类型的变量。每行Acraga代码都需要以`;`作为结尾标识。Acraga对此外的格式没有任何要求，所有冗余的空白字符都会被解释器忽略。


# 词法规则

 - 整数

   `digit ::= 0|1|2|3|4|5|6|7|8|9`

   `hex ::= digit|A|B|C|D|E|F`

   `integer ::= decimal | hexadecimal`

   `decimal ::= (+|-)? digit+`

   `hexadecimal ::= (0x|0X)hex+`  

 - 实数

   `real ::= decimal.digit+`

 - 布尔值

   `boolean ::= true|false`

 - 字符串

   `alphabet ::= ASCII码能表示的所有字符`

   `literal ::= alphabet*`

 - 标识符

    `alphabet_english ::= [a-zA-Z]`

    `alphabet_id ::= alphabet_english | _ | digits`

    `alphabet_id_first ::= alphabet_english | _`

    `identifier ::= alphabet_id_first alphabet_id*`

 - 保留字/关键字
    - if
    	- else
    	- while
    	- int
    	- float
    	- bool
    	- string
    	- print

# 语法规则

 - 语句

    `statement ::= declare_statement | assign_statement | output_statement | if_statement`

    `statements ::= statement+`


 - 声明语句

   `type ::= read | float | bool | string`

   `declare_statement ::= type identifier;`

 - 赋值语句

   `immediate_value ::= integer | real | boolean | literal`

   `value ::= immediate_value | identifier`

   `assign_statement ::= identifier = (value | expression);`

 - 输出语句

   `output_statement ::= print(value)`

 - 表达式

   `expression ::= T`

   `T ::= T+F | T-F | F`

   `F ::= F*G | F/G | F%G | G`

   `G ::=  value | (T)`

   `value ::= integer | real | boolean | string`

 - if语句

   `if_statement ::= if(expression){statements}(else{statements})?`

 - while语句

   `while_statement ::= while(expression){statements}`

- for语句

  `for_statement ::= for(initialization; boolean expression; control variable){statements}`




# TODO

  - 整数支持十六进制数字 eg. 0xF4A1 
  - 实数支持科学计数法表示 eg. 3.14e5
  - 支持布尔运算 eg. true & false
  - 支持数组 eg. test[10]
  - 支持声明与赋值同时完成
  - 支持更复杂的输出语句 eg. print(x + y)
  - 支持格式化输出语句 eg. printf("hello %s", your_name)
  - 支持输入语句 eg. int x = scanf()
  - 支持for循环语句
  - 支持+=、-=、/=、*=
  - 修改表达式部分的定义，加入括号，并且使之可以提现运算符优先级的区别，可以参考上学期ppt





# How to use Acraga

## 算术表达式

Acraga支持整型、~~实型~~  浮点型、布尔型、字符串类型与十六进制数的运算。其中包括加法 `+`、减法`-`、乘法`*`、除法`/`、取模`%`。

==注意：**仅对字符串类型支持加法运算**==

**运算符优先级（数字越小优先级越高）：**

- 0级：(、)、~
- 1级：*、/、%
- 2级：+、-
- 3级：=

因此我们可以进行如下运算：

- `(1 + 2) * (3 * (4 + 5) - 6) = 63`
- `1 + 2 * 3 * 4 + 5 - 6 =24`
- `1 + 3 % 2 = 2`
- `1.3323 + 3.14 - 5.1 = -0.6277`
- `~0 = 1`
- `0x0001 + 0x0A02 = 0x0A03`

在Java中：

```java
String str1 = "abc";
System.out.println(str1 + "d");// output:abcd
String str2 = "1";
String str3 = "2";
System.out.println(str2 + str3);// output:12
```

- `a + b = ab`

- 此外还支持科学计数法的表示： 1.23e2 = $$1.23\times10^2$$
