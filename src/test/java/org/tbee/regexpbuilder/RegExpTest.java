package org.tbee.regexpbuilder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

public class RegExpTest {

    @Test
    public void exact() {
        RegExp regExp = RegExp.of()
                .exact("^foo");
        Assertions.assertEquals("\\^foo", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void oneOf() {
        RegExp regExp = RegExp.of()
                .oneOf("abc");
        Assertions.assertEquals("[abc]", regExp.toString());
        Assertions.assertEquals(6, countMatches(regExp.toMatcher("bcabca")));
        Assertions.assertEquals(3, countMatches(regExp.toMatcher("adbec")));
    }

    @Test
    public void notOneOf() {
        RegExp regExp = RegExp.of()
                .notOneOf("abc");
        Assertions.assertEquals("[^abc]", regExp.toString());
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("bcabca")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("adbec")));
    }

    @Test
    public void startOfLine() {
        RegExp regExp = RegExp.of()
                .startOfLine()
                .exact("^foo");
        Assertions.assertEquals("^\\^foo", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void endOfLine() {
        RegExp regExp = RegExp.of()
                .exact("^foo")
                .endOfLine();
        Assertions.assertEquals("\\^foo$", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void optionally() {
        RegExp regExp = RegExp.of()
                .optional("^foo");
        Assertions.assertEquals("(\\^foo)?", regExp.toString());
        Assertions.assertEquals(3, countMatches(regExp.toMatcher("^foo^foo"))); // TBEERNOT why?
        Assertions.assertEquals(6, countMatches(regExp.toMatcher("^foobar^foo")));  // TBEERNOT why?
    }

    @Test
    public void zeroOrMore() {
        RegExp regExp = RegExp.of()
                .zeroOrMore("^foo");
        Assertions.assertEquals("(\\^foo)*", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foo^foo"))); // TBEERNOT why?
        Assertions.assertEquals(6, countMatches(regExp.toMatcher("^foobar^foo"))); // TBEERNOT why?
    }

    @Test
    public void oneOrMore() {
        RegExp regExp = RegExp.of()
                .oneOrMore("^foo");
        Assertions.assertEquals("(\\^foo)+", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("^foo^foo")));
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("^foobar^foo")));
    }

    @Test
    public void group() {
        RegExp regExp = RegExp.of()
                .group("g1", e -> e.digit().digit())
                .referToGroup("g1")
                .referToGroup("g1");
        Assertions.assertEquals("(\\d\\d)\\1\\1", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("123456")));
    }

    @Test
    public void getGroup() {
        RegExp regExp = RegExp.of()
                .group("g1", e -> e.digit().digit())
                .group("g2", e -> e.digit().digit())
                .group("g3", e -> e.digit().digit());
        Assertions.assertEquals("(\\d\\d)(\\d\\d)(\\d\\d)", regExp.toString());
        Matcher matcher = regExp.toMatcher("123456");
        Assertions.assertEquals(2, regExp.getGroupIdx("g2"));
        Assertions.assertEquals(true, matcher.find());
        Assertions.assertEquals("34", matcher.group(regExp.getGroupIdx("g2")));
    }

    @Test
    public void occurs() {
        RegExp regExp = RegExp.of()
                .occurs(3, e -> e.digit().digit());
        Assertions.assertEquals("(\\d\\d){3}", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("1234")));
    }

    @Test
    public void occursAtLeast() {
        RegExp regExp = RegExp.of()
                .occursAtLeast(2, e -> e.digit().digit());
        Assertions.assertEquals("(\\d\\d){2,}", regExp.toString());
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("123456")));
    }

    @Test
    public void occursBetween() {
        RegExp regExp = RegExp.of()
                .occursBetween(1, 2, e -> e.digit().digit());
        Assertions.assertEquals("(\\d\\d){1,2}", regExp.toString());
        Assertions.assertEquals(2, countMatches(regExp.toMatcher("121212")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("1234")));
        Assertions.assertEquals(1, countMatches(regExp.toMatcher("12")));
        Assertions.assertEquals(0, countMatches(regExp.toMatcher("1")));
    }

    @Test
    public void complex() {
        RegExp regExp = RegExp.of()
                .startOfLine()
                .optional("abc")
                .group("g1", re -> re.oneOrMore(re2 -> re2.digit().digit()))
                .group("g2", re -> re.notOneOf("xyz"));
        Matcher matcher = regExp.toMatcher("abc1234def");
        matcher.find();
        String g1 = matcher.group(regExp.getGroupIdx("g1"));
        Assertions.assertEquals("1234", g1);
    }

    private int countMatches(Matcher matcher) {
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
}
