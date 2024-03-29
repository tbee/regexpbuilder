package org.tbee.regexpbuilder;

public class RE {

    static public RegExp group(String name, String s) {
        return RegExp.of().group(name, s);
    }

    static public RegExp group(String name, RegExp regExp) {
        return RegExp.of().group(name, regExp);
    }
    static public RegExp group(RegExp regExp) {
        return RegExp.of().group(regExp);
    }
    static public RegExp group(String s) {
        return RegExp.of().group(s);
    }

    // -------------------------
    // PATTERN

    static public RegExp text(String s) {
        return RegExp.of().text(s);
    }
    static public RegExp range(String fromChar, String toChar) {
        return RegExp.of().range(fromChar, toChar);
    }
    static public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1) {
        return RegExp.of().range(fromChar0, toChar0, fromChar1, toChar1);
    }
    static public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2) {
        return RegExp.of().range(fromChar0, toChar0, fromChar1, toChar1, fromChar2, toChar2);
    }
    static public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2, String fromChar3, String toChar3) {
        return RegExp.of().range(fromChar0, toChar0, fromChar1, toChar1, fromChar2, toChar2, fromChar3, toChar3);
    }
    static public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2, String fromChar3, String toChar3, String fromChar4, String toChar4) {
        return RegExp.of().range(fromChar0, toChar0, fromChar1, toChar1, fromChar2, toChar2, fromChar3, toChar3, fromChar4, toChar4);
    }
    public RegExp range(String... fromToChars) {
        return RegExp.of().range(fromToChars);
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

    static public RegExp tab() {
        return RegExp.of().tab();
    }
    public RegExp carriageReturn() {
        return RegExp.of().carriageReturn();
    }
    public RegExp lineFeed() {
        return RegExp.of().lineFeed();
    }
    public RegExp doubleQuote() {
        return RegExp.of().doubleQuote();
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
    static public RegExp word() {
        return RegExp.of().word();
    }
    /**
     * Use word()
     * @return
     */
    @Deprecated
    static public RegExp wordChar() {
        return word();
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    static public RegExp nonWord() {
        return RegExp.of().nonWord();
    }
    /**
     * Use nonWord()
     * @return
     */
    @Deprecated
    static public RegExp nonWordChar() {
        return nonWord();
    }

    static public RegExp wordBoundary() {
        return RegExp.of().wordBoundary();
    }

    static public RegExp nonWordBoundary() {
        return RegExp.of().nonWordBoundary();
    }
}
