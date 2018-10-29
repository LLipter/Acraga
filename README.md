# Acraga
An interpreter designed for Acraga language, which is a super simple, strong-type, c-like programming language.





# Acraga 语言概述

Acraga是一种简单的强类型编程语言，支持整数、实数、布尔值、字符串类型的变量。每行Acraga代码都需要以`;`作为结尾标识。Acraga对此外的格式没有任何要求，所有冗余的空白字符都会被解释器忽略。






# Lexical Rules

 - Integer

   `digit ::= 0|1|2|3|4|5|6|7|8|9`

   `hex ::= digit|A|B|C|D|E|F`

   `integer ::= decimal | hexadecimal`

   `decimal ::= (+|-)? digit+`

   `hexadecimal ::= (0x|0X)hex+`  

 - Real

   `real ::= decimal.digit+`

 - Boolean

   `boolean ::= true|false`

 - String

   `alphabet ::= ASCII码能表示的所有字符`

   `literal ::= "alphabet*"`

 - Identifier

    `alphabet_english ::= [a-zA-Z]`

    `alphabet_id ::= alphabet_english | _ | digits`

    `alphabet_id_first ::= alphabet_english | _`

    `identifier ::= alphabet_id_first alphabet_id*`

 - Keywords/Reserved words：
    - if
    - else
    - while
    - int
    - double
    - bool
    - string
    - print

- Comment

    `//this is a comment, multiple lines of comments are not supported yet.`

- Operator：

    `operator ::=  '+' | '-' | '*' | '/' | '=' | '%' | '==' | '!=' | '>' | '<' | '>=' | '<='`

- Delimiter：

    `delimiter ::= '(' | ')' | '[' | ']' | '{' | '}' | ';' | ',' | '.'`





# Grammar Rules

 - Statements

    `statement ::= declare_statement | assign_statement | output_statement | if_statement`

    `statements ::= statement+`


 - Declaration statement

   `type ::= read | double | bool | string`

   `declare_statement ::= type identifier;`

 - Assignment statement

   `immediate_value ::= integer | real | boolean | literal`

   `value ::= immediate_value | identifier`

   `assign_statement ::= identifier = (value | expression);`

 - Output

   `output_statement ::= print(value)`

 - Expression

   `expression ::= T`

   `T ::= T==F | T!=F | F`

   `F ::= F>G | F<G | F>=G | F<=G | G`

   `G ::= G+H | G-H | H`

   `H ::= H*I | H/I | H%I | J`

   `J ::=  value | (T)`

   `value ::= integer | real | boolean | string`

 - if statement

   `if_statement ::= if(expression){statements}(else{statements})?`

 - while statememt

   `while_statement ::= while(expression){statements}`

- for statement

  `for_statement ::= for(initialization; boolean expression; control variable){statements}`







# How to use Acraga



## Arithmetic Statement

Acraga supports operation of int, double, bool, string and hexadecimal number, including add `+`, subtract`-`, multiple`*`, divide`/`, module`%`。

P.S.**For string, only '+' operation is allowed**

**Priority of Operations（larger number stands for higher priority）：**

- Lv1：=
- Lv2：==, !=
- Lv3：>, <, >=, <=
- Lv4：+, -
- Lv5：*, /, %
- Lv6：-(negative)
- **(, ) has the highest priority**

So that we can do something like these：

- `(1 + 2) * (3 * (4 + 5) - 6) = 63`
- `1 + 2 * 3 * 4 + 5 - 6 =24`
- `1 + 3 % 2 = 2`
- `1.3323 + 3.14 - 5.1 = -0.6277`
- `0x0001 + 0x0A02 = 2563`

In Java：

```java
String str1 = "abc";
System.out.println(str1 + "d");// output:abcd
String str2 = "1";
String str3 = "2";
System.out.println(str2 + str3);// output:12
```

- `a + b = ab`
- Moreover, scientific notation is valid： 1.23e2 = $$1.23\times10^2$$



# TODO

- 整数支持十六进制数字 eg. 0xF4A1 (直接显示对应十进制数字62625) ——(✔️)
- 实数支持科学计数法表示 eg. 3.14e5 ——(✔️)
- 支持布尔运算 eg. true & false
- 支持数组 eg. test[10] ——(✔️)
- 支持声明与赋值同时完成
- 支持更复杂的输出语句 eg. print(x + y)
- 支持格式化输出语句 eg. printf("hello %s", your_name)
- 支持输入语句 eg. int x = scanf()
- 支持for循环语句 ——(✔️)
- 支持+=、-=、/=、*=
- 修改表达式部分的定义，加入括号，并且使之可以提现运算符优先级的区别，可以参考上学期ppt ——(✔️)

</hr>

0. test&debug
1. 能够在全局区执行一些语句
2. 让for语句可以执行初始化
3. 引入多行注释的语法
4. 引入显示类型转换的概念
5. **重构精简代码**
6. ***** 引入类的概念
7. ***** 用C++复刻项目，比较Java与C++版本性能上的区别
8. 支持在函数参数中传递数组类型的变量
9. 支持逗号运算符的操作
10. 支持++,-- 两种运算符，考虑同时支持前置后置形式
11. 实现必要的库函数，例如提供函数输出方式的print()方法
12. ***** 实现类的概念后可以定义一些标准库数据结构
13. 测试，争取没有逻辑错误以及运行时崩溃的情况
14. 用Acraga编写一些简单的小程序， 检查解释器在更真实的场景下的正确性，同时检查设计的语法是否符合交互性
15. 编写全面的文档，讲解解释器的整体构架以及具体细节并介绍如何编写合法的Acraga程序。





