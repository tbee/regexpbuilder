package org.tbee.regexpbuilder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tbee.regexpbuilder.RE.*;

public class RegExpTest {

    @Test
    public void exactTest() {
        RegExp regExp = RegExp.of()
                .exact("^foo");
        Assertions.assertEquals("\\^foo", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void oneOfTest() {
        RegExp regExp = RegExp.of()
                .oneOf("abc");
        Assertions.assertEquals("[abc]", regExp.toString());
        Assertions.assertEquals(6, countMatches(regExp.toMatcher("bcabca")));
        Assertions.assertEquals(3, countMatches(regExp.toMatcher("adbec")));
    }

    @Test
    public void notOneOfTest() {
        RegExp regExp = RegExp.of()
                .notOneOf("abc");
        Assertions.assertEquals("[^abc]", regExp.toString());
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("bcabca")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("adbec")));
    }

    @Test
    public void startOfLineTest() {
        RegExp regExp = RegExp.of()
                .startOfLine()
                .exact("^foo");
        Assertions.assertEquals("^\\^foo", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void endOfLineTest() {
        RegExp regExp = RegExp.of()
                .exact("^foo")
                .endOfLine();
        Assertions.assertEquals("\\^foo$", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void optionallyTest() {
        RegExp regExp = RegExp.of()
                .optional("^foo");
        Assertions.assertEquals("\\^foo?", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void zeroOrMoreTest() {
        RegExp regExp = RegExp.of()
                .zeroOrMore("^foo");
        Assertions.assertEquals("\\^foo*", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void oneOrMoreTest() {
        RegExp regExp = RegExp.of()
                .oneOrMore("[foo");
        Assertions.assertEquals("\\[foo+", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("[foo[foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("[foobar[foo")));
    }

    @Test
    public void groupTest() {
        RegExp regExp = RegExp.of()
                .group("g1", digit().digit())
                .referToGroup("g1")
                .referToGroup("g1");
        Assertions.assertEquals("(\\d\\d)\\1\\1", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("123456")));
    }

    @Test
    public void getGroupTest() {
        RegExp regExp = RegExp.of()
                .group("g1", digit().digit())
                .group("g2", digit().digit())
                .group("g3", digit().digit());
        Assertions.assertEquals("(\\d\\d)(\\d\\d)(\\d\\d)", regExp.toString());
        Matcher matcher = regExp.toMatcher("123456");
        Assertions.assertEquals(2, regExp.indexOf("g2"));
        Assertions.assertEquals(true, matcher.find());
        Assertions.assertEquals("34", matcher.group(regExp.indexOf("g2")));
    }

    @Test
    public void occursTest() {
        RegExp regExp = RegExp.of()
                .occurs(3, digit().digit());
        Assertions.assertEquals("\\d\\d{3}", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("123")));
    }

    @Test
    public void occursAtLeastTest() {
        RegExp regExp = RegExp.of()
                .occursAtLeast(2, digit().digit());
        Assertions.assertEquals("\\d\\d{2,}", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("123456")));
    }

    @Test
    public void occursBetweenTest() {
        RegExp regExp = RegExp.of()
                .occursBetween(1, 2, digit().digit());
        Assertions.assertEquals("\\d\\d{1,2}", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("1234")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("12")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("1")));
    }

    @Test
    public void complexTest() {
        RegExp regExp = RegExp.of()
                .startOfLine()
                .optional("abc")
                .group("g1", oneOrMore(digit().digit()))
                .group("g2", notOneOf("xyz"));
        Assertions.assertEquals("^abc?(\\d\\d+)([^xyz])", regExp.toString());
        Matcher matcher = regExp.toMatcher("abc1234de");
        matcher.find();
        Assertions.assertEquals("1234", matcher.group(regExp.indexOf("g1")));
    }

    @Test
    public void apacheLogTest() {
        RegExp regExp = RegExp.of()
                .group("ip", oneOrMore(nonWhitespace()))
                .exact(" ")
                .group("client", oneOrMore(nonWhitespace()))
                .exact(" ")
                .group("user", oneOrMore(nonWhitespace()))
                .exact(" ")
                .zeroOrMore(anyChar())
                ;

        // https://github.com/sgreben/regex-builder#examples
        System.out.println(regExp.toString());
        String logLine = "127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] \"GET /home.html HTTP/1.1\" 200 2048";
        Matcher matcher = regExp.toMatcher(logLine);
//        Matcher matcher = Pattern.compile("(\\S+) (\\S+) (\\S+) (.)*").matcher(logLine);
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals("127.0.0.1", matcher.group(regExp.indexOf("ip")));
        Assertions.assertEquals("-", matcher.group(regExp.indexOf("client")));
        Assertions.assertEquals("-", matcher.group(regExp.indexOf("user")));

//        Assertions.assertEquals("(\\\\S+) (\\\\S+) (\\\\S+) \\\\[([\\\\w:/]+\\\\s[+\\\\-]\\\\d{4})\\\\] \\\"(\\\\S+) (\\\\S+) (\\\\S+)\\\" (\\\\d{3}) (\\\\d+)", regExp.toString());
    }
    private int countMatches(Matcher matcher) {
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
