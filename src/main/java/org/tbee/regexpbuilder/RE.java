package org.tbee.regexpbuilder;

public class RE {

    public RegExp group(String name, String s) {
        return RegExp.of().group(name, s);
    }

    public RegExp group(String name, RegExp regExp) {
        return RegExp.of().group(name, regExp);
    }

    // -------------------------
    // PATTERN

    static public RegExp text(String s) {
        return RegExp.of().text(s);
    }
    static public RegExp range(String fromChar, String toChar) {
        return RegExp.of().range(fromChar, toChar);
    }

    static public RegExp oneOf(RegExp regExp) {
        return RegExp.of().oneOf(regExp);
    }
    static public RegExp oneOf(String s) {
        return RegExp.of().oneOf(s);
    }

    static public RegExp notOneOf(RegExp regExp) {
        return RegExp.of().notOneOf(regExp);
    }
    static public RegExp notOneOf(String s) {
        return RegExp.of().notOneOf(s);
    }

    static public RegExp optional(RegExp regExp) {
        return RegExp.of().optional(regExp);
    }
    static public RegExp optional(String s) {
        return RegExp.of().optional(s);
    }

    static public RegExp zeroOrMore(RegExp regExp) {
        return RegExp.of().zeroOrMore(regExp);
    }
    static public RegExp zeroOrMore(String s) {
        return RegExp.of().zeroOrMore(s);
    }

    static public RegExp oneOrMore(RegExp regExp) {
        return RegExp.of().oneOrMore(regExp);
    }
    static public RegExp oneOrMore(String s) {
        return RegExp.of().oneOrMore(s);
    }

    static public RegExp anyOf(RegExp... regExps) {
        return RegExp.of().anyOf(regExps);
    }
    static public RegExp anyOf(String... ss) {
        return RegExp.of().anyOf(ss);
    }
    static public RegExp anyOf(Object... objects) {
        return RegExp.of().anyOf(objects);
    }

    static public RegExp occurs(int times, RegExp regExp) {
        return RegExp.of().occurs(times, regExp);
    }
    static public RegExp occurs(int times, String s) {
        return RegExp.of().occurs(times, s);
    }

    static public RegExp occursAtLeast(int times, RegExp regExp) {
        return RegExp.of().occursAtLeast(times, regExp);
    }
    static public RegExp occursAtLeast(int times, String s) {
        return RegExp.of().occursAtLeast(times, s);
    }

    static public RegExp occursBetween(int minTimes, int maxTimes, RegExp regExp) {
        return RegExp.of().occursBetween(minTimes, maxTimes, regExp);
    }
    static public RegExp occursBetween(int minTimes, int maxTimes, String s) {
        return RegExp.of().occursBetween(minTimes, maxTimes, s);
    }

    // -------------------------
    // LITERAL

    static public RegExp anyChar() {
        return RegExp.of().anyChar();
    }

    static public RegExp startOfLine() {
        return RegExp.of().startOfLine();
    }

    static public RegExp endOfLine() {
        return RegExp.of().endOfLine();
    }

    /**
     * Any digit, short for [0-9]
     * @return
     */
    static public RegExp digit() {
        return RegExp.of().digit();
    }

    /**
     * Any non-digit, short for [^0-9]
     * @return
     */
    static public RegExp nonDigit() {
        return RegExp.of().nonDigit();
    }

    /**
     * Any whitespace character, short for [\t\n\x0B\f\r]
     * @return
     */
    static public RegExp whitespace() {
        return RegExp.of().whitespace();
    }

    /**
     * Any non-whitespace character, short for [^\s]
     * @return
     */
    static public RegExp nonWhitespace() {
        return RegExp.of().nonWhitespace();
    }

    /**
     * Any word character, short for [a-zA-Z_0-9]
     * @return
     */
    static public RegExp wordChar() {
        return RegExp.of().wordChar();
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    static public RegExp nonWordChar() {
        return RegExp.of().nonWordChar();
    }

    static public RegExp wordBoundary() {
        return RegExp.of().wordBoundary();
    }

    static public RegExp nonWordBoundary() {
        return RegExp.of().nonWordBoundary();
    }
}
