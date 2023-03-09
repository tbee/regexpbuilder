package org.tbee.regexpbuilder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.tbee.regexpbuilder.RE.*;

public class RegExpTest {

    @Test
    public void exactTest() {
        RegExp regExp = RegExp.of()
                .text("^foo");
        Assertions.assertEquals("\\^foo", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void rangeTest() {
        RegExp regExp = RegExp.of()
                .range("0", "9");
        Assertions.assertEquals("[0-9]", regExp.toString());
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
                .text("^foo");
        Assertions.assertEquals("^\\^foo", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void endOfLineTest() {
        RegExp regExp = RegExp.of()
                .text("^foo")
                .endOfLine();
        Assertions.assertEquals("\\^foo$", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void tabTest() {
        RegExp regExp = RegExp.of()
                .tab();
        Assertions.assertEquals("\\t", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("\tbla\t")));
    }

    @Test
    public void carriageReturnTest() {
        RegExp regExp = RegExp.of()
                .carriageReturn();
        Assertions.assertEquals("\\r", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("\rbla\r")));
    }

    @Test
    public void lineFeed() {
        RegExp regExp = RegExp.of()
                .lineFeed();
        Assertions.assertEquals("\\n", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("\nbla\n")));
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
    public void anyOfTest() {
        RegExp regExp = RegExp.of()
                .anyOf("^aaa", "$bbb", "(ccc", digit().word());
        Assertions.assertEquals("(\\^aaa|\\$bbb|\\(ccc|\\d\\w)", regExp.toString());
    }

    @Test
    public void anyOfTest2() {
        RegExp regExp = RegExp.of()
                .text("For sale: ")
                .anyOf("cat", "dog", "mouse", "snake", "bird")
                .text(" food");
        Assertions.assertEquals("For sale: (cat|dog|mouse|snake|bird) food", regExp.toString());
        Matcher matcher = regExp.toMatcher("For sale: snake food");
        Assertions.assertTrue(matcher.matches());
    }

    @Test
    public void anyOfTest3() {
        RegExp regExp = RegExp.of()
                .text("For sale: ")
                .group("animal", anyOf("cat", "dog", "mouse", "snake", "bird"))
                .text(" food");
        Assertions.assertEquals("For sale: ((cat|dog|mouse|snake|bird)) food", regExp.toString()); // TBEERNOT; can we reduce this to one group?
        Matcher matcher = regExp.toMatcher("For sale: snake food");
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals("snake", matcher.group(regExp.indexOf("animal")));
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
                .startOfLine()
                .group("ip", oneOrMore(nonWhitespace()))
                .text(" ")
                .group("client", oneOrMore(nonWhitespace()))
                .text(" ")
                .group("user", oneOrMore(nonWhitespace()))
                .text(" [")
                .group("datetime", oneOrMore(oneOf(word().or().text(":/"))))
                .text(" ")
                .group("offset", oneOf("+-").followedBy().occurs(4, digit()))
                .text("] \"")
                .group("method", oneOrMore(nonWhitespace()))
                .text(" ")
                .group("url", oneOrMore(nonWhitespace()))
                .text(" ")
                .group("http", oneOrMore(nonWhitespace()))
                .text("\" ")
                .group("status", oneOrMore(digit()))
                .whitespace()
                .group("size", oneOrMore(digit()))
                .endOfLine();

        // https://github.com/sgreben/regex-builder#examples
        System.out.println(regExp.toString());
        String logLine = "127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] \"GET /home.html HTTP/1.1\" 200 2048";
        Matcher matcher = regExp.toMatcher(logLine);
        Assertions.assertTrue(matcher.matches());
        Assertions.assertEquals("127.0.0.1", matcher.group(regExp.indexOf("ip")));
        Assertions.assertEquals("-", matcher.group(regExp.indexOf("client")));
        Assertions.assertEquals("-", matcher.group(regExp.indexOf("user")));
        Assertions.assertEquals("21/Jul/2014:9:55:27", matcher.group(regExp.indexOf("datetime")));
        Assertions.assertEquals("-0800", matcher.group(regExp.indexOf("offset")));
        Assertions.assertEquals("GET", matcher.group(regExp.indexOf("method")));
        Assertions.assertEquals("/home.html", matcher.group(regExp.indexOf("url")));
        Assertions.assertEquals("HTTP/1.1", matcher.group(regExp.indexOf("http")));
        Assertions.assertEquals("200", matcher.group(regExp.indexOf("status")));
        Assertions.assertEquals("2048", matcher.group(regExp.indexOf("size")));
    }

    private int countMatches(Matcher matcher) {
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
