package org.tbee.regexpbuilder;

public interface RegExpCore extends RegExpBuild {

    RegExpCore group(String name, RegExpCore regExp);
    RegExpCore group(String name, String s);
    RegExpCore referToGroup(String name);

    // -------------------------
    // PATTERN

    RegExpCore text(String match);

    RegExpCore oneOf(RegExpCore regExp);
    RegExpCore oneOf(String match);

    RegExpCore notOneOf(RegExpCore regExp);
    RegExpCore notOneOf(String match);

    RegExpCore optional(RegExpCore regExp);
    RegExpCore optional(String s);

    RegExpCore zeroOrMore(RegExpCore regExp);
    RegExpCore zeroOrMore(String s);

    RegExpCore oneOrMore(RegExpCore regExp);
    RegExpCore oneOrMore(String s);

    RegExpCore occurs(int times, RegExpCore regExp);
    RegExpCore occurs(int times, String s);

    RegExpCore occursAtLeast(int times, RegExpCore regExp);
    RegExpCore occursAtLeast(int times, String s);

    RegExpCore occursBetween(int minTimes, int maxTimes, RegExpCore regExp);
    RegExpCore occursBetween(int minTimes, int maxTimes, String s);

    // -------------------------
    // LITERAL

    RegExpCore anyChar();
    RegExpBuild endOfLine();
    RegExpCore digit();
    RegExpCore nonDigit();
    RegExpCore whitespace();
    RegExpCore nonWhitespace();
    RegExpCore wordChar();
    RegExpCore nonWordChar();
    RegExpCore wordBoundary();
    RegExpCore nonWordBoundary();

    // -------------------------
    // READABILITY

    RegExpCore and();
    RegExpCore or();
    RegExpCore followedBy();
}
