package org.tbee.regexpbuilder;

public class RE {

    public RegExpCore group(String name, String s) {
        return RegExp.of().group(name, s);
    }

    public RegExpCore group(String name, RegExpCore regExp) {
        return RegExp.of().group(name, regExp);
    }

    // -------------------------
    // PATTERN

    static public RegExpCore text(String s) {
        return RegExp.of().text(s);
    }

    static public RegExpCore oneOf(RegExpCore regExp) {
        return RegExp.of().oneOf(regExp);
    }
    static public RegExpCore oneOf(String s) {
        return RegExp.of().oneOf(s);
    }

    static public RegExpCore notOneOf(RegExpCore regExp) {
        return RegExp.of().notOneOf(regExp);
    }
    static public RegExpCore notOneOf(String s) {
        return RegExp.of().notOneOf(s);
    }

    static public RegExpCore optional(RegExpCore regExp) {
        return RegExp.of().optional(regExp);
    }
    static public RegExpCore optional(String s) {
        return RegExp.of().optional(s);
    }

    static public RegExpCore zeroOrMore(RegExpCore regExp) {
        return RegExp.of().zeroOrMore(regExp);
    }
    static public RegExpCore zeroOrMore(String s) {
        return RegExp.of().zeroOrMore(s);
    }

    static public RegExpCore oneOrMore(RegExpCore regExp) {
        return RegExp.of().oneOrMore(regExp);
    }
    static public RegExpCore oneOrMore(String s) {
        return RegExp.of().oneOrMore(s);
    }

    static public RegExpCore occurs(int times, RegExpCore regExp) {
        return RegExp.of().occurs(times, regExp);
    }
    static public RegExpCore occurs(int times, String s) {
        return RegExp.of().occurs(times, s);
    }

    static public RegExpCore occursAtLeast(int times, RegExpCore regExp) {
        return RegExp.of().occursAtLeast(times, regExp);
    }
    static public RegExpCore occursAtLeast(int times, String s) {
        return RegExp.of().occursAtLeast(times, s);
    }

    static public RegExpCore occursBetween(int minTimes, int maxTimes, RegExpCore regExp) {
        return RegExp.of().occursBetween(minTimes, maxTimes, regExp);
    }
    static public RegExpCore occursBetween(int minTimes, int maxTimes, String s) {
        return RegExp.of().occursBetween(minTimes, maxTimes, s);
    }

    // -------------------------
    // LITERAL

    static public RegExpCore anyChar() {
        return RegExp.of().anyChar();
    }

    static public RegExpCore startOfLine() {
        return RegExp.of().startOfLine();
    }

    static public RegExpBuild endOfLine() {
        return RegExp.of().endOfLine();
    }

    /**
     * Any digit, short for [0-9]
     * @return
     */
    static public RegExpCore digit() {
        return RegExp.of().digit();
    }

    /**
     * Any non-digit, short for [^0-9]
     * @return
     */
    static public RegExpCore nonDigit() {
        return RegExp.of().nonDigit();
    }

    /**
     * Any whitespace character, short for [\t\n\x0B\f\r]
     * @return
     */
    static public RegExpCore whitespace() {
        return RegExp.of().whitespace();
    }

    /**
     * Any non-whitespace character, short for [^\s]
     * @return
     */
    static public RegExpCore nonWhitespace() {
        return RegExp.of().nonWhitespace();
    }

    /**
     * Any word character, short for [a-zA-Z_0-9]
     * @return
     */
    static public RegExpCore wordChar() {
        return RegExp.of().wordChar();
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    static public RegExpCore nonWordChar() {
        return RegExp.of().nonWordChar();
    }

    static public RegExpCore wordBoundary() {
        return RegExp.of().wordBoundary();
    }

    static public RegExpCore nonWordBoundary() {
        return RegExp.of().nonWordBoundary();
    }
}
