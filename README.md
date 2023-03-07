# Regular Expression builder for Java.
Regular expressions are powerful but not easily readable. 
It is not without reason that the 'saying' goes like this:

_Once a programmer had a problem. He tried to solve that with regulars expressions. Then he had two problems._

Now, there are other implementations of such a builder, like the one by [Sergey](https://github.com/sgreben/regex-builder).
But I'm not sure I like the API, so this is my attempt at writing one:

```java

import org.tbee.regexpbuilder.RegExp;
import static org.tbee.regexpbuilder.RE.*;

RegExp regExp = RegExp.of()
                      .startOfLine()
                      .optional("abc")
                      .group("g1", oneOrMore(digit().digit()))
                      .group("g2", notOneOf("xyz"));
Matcher matcher = regExp.toMatcher("abc1234def");
matcher.find();
String g1 = matcher.group(regExp.getGroupIdx("g1")); // 1234
```

