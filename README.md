# Acraga
An interpreter designed for Acraga language, which is a super simple, strong-type, c-like programming language.

# Acraga 语言概述


Acraga是一种简单的强类型编程语言，支持整数、实数、布尔值、字符串类型的变量。每行Acraga代码都需要以`;`作为结尾标识。Acraga对此外的格式没有任何要求，所有冗余的空白字符都会被解释器忽略。


# 词法规则

 - 整数

   `digits ::= 0|1|2|3|4|5|6|7|8|9`

   `integer ::= integer digits|digits`

 - 实数

   `real ::= integer . integer`

 - 布尔值

   `boolean ::= true : false`

 - 字符串

   `alphabet ::= ASCII能表示的所有字符`

   `literal ::= "alphabet*"`

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


   `add_expression ::= expression + value`

   `sub_expression ::= expression - value`

   `mul_expression ::= expression * value`

   `div_expression ::= expression / value`

`expression ::= add_expression | sub_expression | mul_expression | div_expression | value`

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
