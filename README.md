# Welcome to Acraga!
An interpreter designed for Acraga language, which is a super simple, strong-type, c-like programming language.

**Be open to any suggestions that can make Acraga better**

**Star⭐️ Acraga, fork it and pull request is well received**



# Acraga 语言概述

Acraga是一种简单的强类型编程语言，支持整数、实数、布尔值、字符串类型的变量。每行Acraga代码都需要以`;`作为结尾标识。Acraga对此外的格式没有任何要求，所有冗余的空白字符都会被解释器忽略。




# Lexical Rules

 - Integer:

   `digit ::= 0|1|2|3|4|5|6|7|8|9`

   `hex ::= digit|A|B|C|D|E|F`

   `integer ::= decimal | hexadecimal`

   `decimal ::= (+|-)? digit+`

   `hexadecimal ::= (0x|0X)hex+`  

 - Real:

   `real ::= decimal.digit+`

 - Boolean:

   `boolean ::= true|false`

 - String:

   `alphabet ::= ASCII码能表示的所有字符`

   `literal ::= "alphabet*"`

 - Identifier:

    `alphabet_english ::= [a-zA-Z]`

    `alphabet_id ::= alphabet_english | _ | digits`

    `alphabet_id_first ::= alphabet_english | _`

    `identifier ::= alphabet_id_first alphabet_id*`

 - Keywords/Reserved words:

    - if
    - else
    - while
    - for
    - void
    - int
    - double
    - bool
    - string
    - void
    - return

- Comment:

    `//this is a comment.`
    `/*this is also`
    `a comment */`

- Operator:

    `operator ::=  '+' | '-' | '*' | '/' | '=' | '%' | '==' | '!=' | '>' | '<' | '>=' | '<='`

- Delimiter:

    `delimiter ::= '(' | ')' | '[' | ']' | '{' | '}' | ';' | ','`



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

Acraga supports operation of int, double, bool, string and hexadecimal number, including add `+`, subtract`-`, multiple`*`, divide`/`, module`%` etc. For more operators see the priority list below.

P.S.**For string, only '+' operation is allowed**

**Priority of Operations（smaller number stands for higher priority）：**

- **(, ) and [,] has the highest priority**

- | Operator |            Explanation             |  type  | Priority |
  | :------: | :--------------------------------: | :----: | :------: |
  |    -     |           negative sign            | unary  |    2     |
  |    +     |           positive sign            | unary  |    2     |
  |    !     |            logical NOT             | unary  |    2     |
  |    ~     |            bitwise NOT             | unary  |    2     |
  |    ++    |       self Increment(prefix)       | unary  |    2     |
  |    --    |       self Decrement(prefix)       | unary  |    2     |
  |    \*    |           multiplication           | binary |    3     |
  |    /     |              division              | binary |    3     |
  |    %     |              reminder              | binary |    3     |
  |    +     |              addition              | binary |    4     |
  |    -     |            subtraction             | binary |    4     |
  |    <<    |             left shift             | binary |    5     |
  |    >>    |            right shift             | binary |    5     |
  |    <     |             less than              | binary |    6     |
  |    <=    |         less than or equal         | binary |    6     |
  |    >     |            greater than            | binary |    6     |
  |    >=    |       greater than or equal        | binary |    6     |
  |    ==    |               equal                | binary |    7     |
  |    !=    |             not equal              | binary |    7     |
  |    &     |            bitwise AND             | binary |    8     |
  |    ^     |            bitwise XOR             | binary |    9     |
  |    \|    |             bitwise OR             | binary |    10    |
  |    &&    |            logical AND             | binary |    11    |
  |   \|\|   |             logical OR             | binary |    12    |
  |    =     |               assign               | binary |    14    |
  |    +=    |     compound assignment by sum     | binary |    14    |
  |    -=    | compound assignment by difference  | binary |    14    |
  |   \*=    |   compound assignment by product   | binary |    14    |
  |    /=    |  compound assignment by quotient   | binary |    14    |
  |    %=    |  compound assignment by remainder  | binary |    14    |
  |    &=    | compound assignment by bitwise AND | binary |    14    |
  |    ^=    | compound assignment by bitwise XOR | binary |    14    |
  |   \|=    | compound assignment by bitwise OR  | binary |    14    |
  |   <<=    | compound assignment by left shift  | binary |    14    |
  |   >>=    | compound assignment by right shift | binary |    14    |

So that we can do something like these：

- `(1 + 2) * (3 * (4 + 5) - 6) = 63`
- `1 + 2 * 3 * 4 + 5 - 6 =24`
- `1 + 3 % 2 = 2`
- `1.3323 + 3.14 - 5.1 = -0.6277`
- `0x0001 + 0x0A02 = 2563`
- `++1 = 2, --4 = 3`

In Java：

```java
String str1 = "abc";
System.out.print(str1 + "d");// output:abcd
String str2 = "1";
String str3 = "2";
System.out.print(str2 + str3);// output:12
```

- `a + b = ab`
- Moreover, scientific notation is valid： 1.23e2 = $$1.23\times10^2$$



## Function

### Main function

```c
int main(){
  //do something
  return 0;
}
```

```c
void main(){
  //do something
}
```

Both are supported in Acraga.

**Note that if you use 'int main', whatever the result is, append 'return 0' at the end of the function.**



### Other functions

```c
int foo(){
  //do something
}

double foo(int a){
  //do something
}
```

These are two different methods via override. 

```c
int foo(){
  //do something
}

double foo(){
  //do something
}
```

**Warning:  these two functions have the same function signature, which will throw exception.**



```Java
void testPrint(){
  print(Any type except void is supported);
  println(Whatever you like, even with no parameter);
}
```



```java
void testRead(){
  string str1 = read();// read until meets a whitespace
  string str2 = readLine();// read until you press enter
  int a = readInt();// read the first integer number which can be identified as a BigInteger
  double b = readDecimal();// read the first real number which can be identified as a BigDecimal
  bool c = readBool();// read the first boolean variable which can be identified as a Boolean
  // read input from keyboard
}
```





# TODO

- 整数支持十六进制数字 eg. 0xF4A1 (直接显示对应十进制数字62625) ——(✔️)
- 实数支持科学计数法表示 eg. 3.14e5 ——(✔️)
- 支持布尔运算 eg. true & false ——(✔️)
- 支持数组 eg. test[10] ——(✔️)
- 支持声明与赋值同时完成 ——(✔️)
- 支持更复杂的输出语句 eg. print(x + y) ——(✔️)
- 支持格式化输出语句 eg. printf("hello %s", your_name)
- 支持输入语句 eg. int x = scanf()
- 支持for循环语句 ——(✔️)
- 支持+=、-=、/=、*= ——(✔️)
- 修改表达式部分的定义，加入括号，并且使之可以提现运算符优先级的区别，可以参考上学期ppt ——(✔️)

****

0. test&debug ——(✔️)
1. 能够在全局区执行一些语句 ——(✔️)
2. 让for语句可以执行初始化 ——(✔️)
3. 引入多行注释的语法 ——(✔️)
4. 引入显示类型转换的概念,在函数参数传递的过程中允许隐式类型转换
5. **重构精简代码**
6. ** 引入类的概念
7. ** 用C++复刻项目，比较Java与C++版本性能上的区别
8. 支持在函数参数中传递数组类型的变量
9. 支持逗号运算符的操作 (int a=1, b=2;)
10. 支持++,-- 两种运算符，考虑同时支持前置后置形式 ——(✔️)
11. 实现必要的库函数，例如提供函数输出方式的print()方法 ——(✔️)
12. ** 实现类的概念后可以定义一些标准库数据结构，比如scanf，比如max，min等数学函数库
13. 测试，争取没有逻辑错误以及运行时崩溃的情况
14. 用Acraga编写一些简单的小程序， 检查解释器在更真实的场景下的正确性，同时检查设计的语法是否符合交互性 ——(✔️)
15. 编写全面的文档，讲解解释器的整体构架以及具体细节并介绍如何编写合法的Acraga程序
16. 支持增强的for循环
17. 检测函数签名，如果相同，报错 ——(✔️)
18. 支持代码块
19. ** 支持函数指针以及lambda表达式
20. 考虑如何定义参数可变的函数，这点和传递数组类型的变量共同是实现类似printf函数的基础
21. 在detectExpression中做更加细致的判断，判断哪种和哪种token是不可能连在一起的，以此抛出潜在的缺乏分号的错误。
22. 加入break, continue语法 ——(✔️)
23. 加入switch case语法
24. 静态多个报错
25. 报错位置不准确



****

- 完成readme
- 创建wiki
- 构建自动机





