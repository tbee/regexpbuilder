# Regular Expression builder for Java.
Regular expressions are powerful but not easily readable. 
It is not without reason that the 'saying' goes like this:

_Once a programmer had a problem., he tried to solve that with regulars expressions, then he had two problems._

Now, there are already implementations of a RegEx builder, like the one by [Sergey](https://github.com/sgreben/regex-builder).
But I'm not sure I like the API, so this is my attempt at writing one:

```java
import org.tbee.regexpbuilder.RegExp;
import static org.tbee.regexpbuilder.RE.*;

RegExp regExp = RegExp.of()
                      .startOfLine()
                      .optional("abc")
                      .group("g1", oneOrMore(digit().digit()))
                      .group("g2", notOneOf("xyz"));
Matcher matcher = regExp.toMatcher("abc1234de");
matcher.find();
String g1 = matcher.group(regExp.indexOf("g1")); // results in "1234"
```

It becomes interesting when things are more complex, for example for a log line:

```java
// RegExp for this: 127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] "GET /home.html HTTP/1.1" 200 2048
RegExpCore regExp = RegExp.of()
        .group("ip", oneOrMore(nonWhitespace()))
        .whitespace()
        .group("client", oneOrMore(nonWhitespace()))
        .whitespace()
        .group("user", oneOrMore(nonWhitespace()))
        .whitespace()
        .text("[")
        .group("datetime", oneOrMore(oneOf(wordChar().or().text(":/"))))
        .whitespace()
        .group("offset", oneOf("+-").followedBy().occurs(4, digit()))
        .text("]")
        .whitespace()
        .text("\"")
        .group("method", oneOrMore(nonWhitespace()))
        .whitespace()
        .group("url", oneOrMore(nonWhitespace()))
        .whitespace()
        .group("http", oneOrMore(nonWhitespace()))
        .text("\"")
        .whitespace()
        .group("status", oneOrMore(digit()))
        .whitespace()
        .group("size", oneOrMore(digit()));

String logLine = "127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] \"GET /home.html HTTP/1.1\" 200 2048";
Matcher matcher = regExp.toMatcher(logLine);
matcher.group(regExp.indexOf("datetime"); // returns "21/Jul/2014:9:55:27"
```

The `or()`, `and()`, and `followedBy()` are dummy methods, solely present for readability.
They can be omitted.
