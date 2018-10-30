# Overall

The whole project comprises of four consecutive parts, which are `Preprocessor`, [Scanner](#scanner), `Parser` and `Interpreter`.

## Preprocessor

Preprocessor receives raw data as input and produces simplified data. Basically, it does the following things.

1. All comments are removed since they're irrelevant to the result of program.
2. `\r` is removed since in `Window` platform `\r\n` represents a new line, whereas in `Linux/MacOS` system a individual `\n` represents the same meaning.

## Scanner

Scanner receives simplified data from the preprocessor as input and produces a sequence of tokens as output.

In our cases, we defined five kinds of token, which are `Separator`, `Operator`, `Literal Value`, `Keywords` and `Identifier` respectively.

### Separator

Separators can be used to mark logical scope. For example, all statements in a function should be surrounded by a pair of `{` and `}`. At this point, we recognize the following symbols as separators.

| Separator | Explanation |
| :-: | :-: |
| { | left brace |
| } | right brace |
| { | left bracket |
| { | right bracket |
| ( | left parenthesis |
| ) | left parenthesis |
| ; | semicolon |
| , | comma |

### Operator

We recognize the following character combinations as operator. Notice that we adopt the longest match principle, which means the longest matched prefix will be recognized. 

| Operator | Explanation | type | Priority |
| :-: | :-: | :-: | :-: |
| - | negative sign | unary | 2 |
| + | positive sign | unary | 2 |
| ! | logical NOT | unary | 2 |
| ~ | bitwise NOT | unary | 2 |
| \* | multiplication | binary | 3 |
| / | division | binary | 3 |
| % | reminder | binary | 3 |
| + | addition | binary | 4 |
| - | subtraction | binary | 4 |
| << | left shift | binary | 5 |
| >> | right shift | binary | 5 |
| < | less than | binary | 6 |
| <= | less than or equal | binary | 6 |
| > | greater than | binary | 6 |
| >= | greater than or equal | binary | 6 |
| == | equal | binary | 7 |
| != | not equal | binary | 7 |
| & | bitwise AND | binary | 8 |
| ^ | bitwise XOR | binary | 9 |
| \| | bitwise OR | binary | 10 |
| && | logical AND | binary | 11 |
| \|\| | logical OR | binary | 12 |
| = | assign | binary | 14 |
| += | compound assignment by sum | binary | 14 |
| -= | compound assignment by difference | binary | 14 |
| \*= | compound assignment by product | binary | 14 |
| /= | compound assignment by quotient | binary | 14 |
| %= | compound assignment by remainder | binary | 14 |
| &= | compound assignment by bitwise AND | binary | 14 |
| ^= | compound assignment by bitwise XOR | binary | 14 |
| \|= | compound assignment by bitwise OR | binary | 14 |
| <<= | compound assignment by left shift | binary | 14 |
| >>= | compound assignment by right shift | binary | 14 |

### Literal Value

At this point, we only support four kinds of literal values.
 
- Integer value : in theory, we support unlimited long integer.
	1. trivial integer like `123`
	2. hexadecimal integer like `0x3f` and `0XA1` 
	3. character integer like `'k'`

- Real value : in theory, we support unlimited precision real value.
   1. trivial real value like `1.23`
   2. scientific number like `1.23e-5` and `2E9`
- Boolean value : `true` and `false`
- String value : literal value surrounded by double quotation like `"Hello, LLipter"`

Escape characters are properly recognized and organized. At this point, we only support the escape characters in the following table.

| Escape Character | Logical Meaning |
| :-: | :-: |
| \n | new line |
| \t | horizontal tab |
| \' | single quotation marks |
| \\" | double quotation marks |
| \\\\ | backslash |