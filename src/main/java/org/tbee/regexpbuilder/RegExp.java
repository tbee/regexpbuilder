package org.tbee.regexpbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: lookbehind etc https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions/Cheatsheet

public class RegExp {

    private String regExpString = "";
    private int groupIdx = 0;
    private Map<String, Integer> groupNameToIdx = new HashMap<>();

    // -------------------------
    // FACTORY

    public static RegExp of() {
        return new RegExp();
    }


    // -------------------------
    // GROUP

    private RegExp startGroup(String name) {
        regExpString += "(";
        groupIdx++;
        if (name != null) {
            if (groupNameToIdx.containsKey(name)) {
                throw new RuntimeException("Group '" + name + "' already exists");
            }
            groupNameToIdx.put(name, groupIdx);
        }
        return this;
    }

    private RegExp endGroup() {
        regExpString += ")";
        return this;
    }

    public RegExp group(String name, RegExp regExp) {
        startGroup(name);
        regExpString += regExp.toString();
        endGroup();
        return this;
    }
    public RegExp group(String name, String s) {
        return group(name, RegExp.of().text(s));
    }
    public RegExp group(RegExp regExp) {
        return group(null, regExp);
    }
    public RegExp group(String s) {
        return group(null, s);
    }

    public RegExp referToGroup(String name) {
        regExpString += "\\" + groupNameToIdx.get(name);
        return this;
    }

    /**
     * This method is not part of the building, but is to be used in matcher.group(...)
     * @param name group name
     * @return index of the group in the regular expression
     */
    public int indexOf(String name) {
        return groupNameToIdx.get(name);
    }

    // -------------------------
    // PATTERN

    /**
     * Match a specific text.
     * Any characters with special meaning in regular expressions will be escape.
     * @param s the text to match, it does not need to be (regexp) escaped.
     * @return
     */
    public RegExp text(String s) {
        regExpString += escape(s);
        return this;
    }

    /**
     * Match a range of text.
     * Any characters with special meaning in regular expressions will be escape.
     * @param fromChar the beginning character
     * @param toChar the beginning character
     * @return
     */
    public RegExp range(String fromChar, String toChar) {
        regExpString += "["
                + escape(checkRangeValue(fromChar)) + "-" + escape(checkRangeValue(toChar))
                + "]";
        return this;
    }
    public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1) {
        regExpString += "["
                + escape(checkRangeValue(fromChar0)) + "-" + escape(checkRangeValue(toChar0))
                + escape(checkRangeValue(fromChar1)) + "-" + escape(checkRangeValue(toChar1))
                + "]";
        return this;
    }
    public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2) {
        regExpString += "["
                + escape(checkRangeValue(fromChar0)) + "-" + escape(checkRangeValue(toChar0))
                + escape(checkRangeValue(fromChar1)) + "-" + escape(checkRangeValue(toChar1))
                + escape(checkRangeValue(fromChar2)) + "-" + escape(checkRangeValue(toChar2))
                + "]";
        return this;
    }
    public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2, String fromChar3, String toChar3) {
        regExpString += "["
                + escape(checkRangeValue(fromChar0)) + "-" + escape(checkRangeValue(toChar0))
                + escape(checkRangeValue(fromChar1)) + "-" + escape(checkRangeValue(toChar1))
                + escape(checkRangeValue(fromChar2)) + "-" + escape(checkRangeValue(toChar2))
                + escape(checkRangeValue(fromChar3)) + "-" + escape(checkRangeValue(toChar3))
                + "]";
        return this;
    }
    public RegExp range(String fromChar0, String toChar0, String fromChar1, String toChar1, String fromChar2, String toChar2, String fromChar3, String toChar3, String fromChar4, String toChar4) {
        regExpString += "["
                + escape(checkRangeValue(fromChar0)) + "-" + escape(checkRangeValue(toChar0))
                + escape(checkRangeValue(fromChar1)) + "-" + escape(checkRangeValue(toChar1))
                + escape(checkRangeValue(fromChar2)) + "-" + escape(checkRangeValue(toChar2))
                + escape(checkRangeValue(fromChar3)) + "-" + escape(checkRangeValue(toChar3))
                + escape(checkRangeValue(fromChar4)) + "-" + escape(checkRangeValue(toChar4))
                + "]";
        return this;
    }
    public RegExp range(String... fromToChars) {
        if (fromToChars.length == 0) {
            throw new IllegalArgumentException("You need to provide at least 2 values");
        }
        if (fromToChars.length % 2 != 0) {
            throw new IllegalArgumentException("You need to provide always pairs of 2");
        }
        regExpString += "[";
        for (int i = 0; i < fromToChars.length; i += 2) {
            regExpString += escape(checkRangeValue(fromToChars[i])) + "-" + escape(checkRangeValue(fromToChars[i + 1]));
        }
        regExpString += "]";
        return this;
    }
    private String checkRangeValue(String s) {
        if (s.length() != 1) {
            throw new IllegalArgumentException("The range character parameters must be exactly 1 in length");
        }
        return s;
    }

    /**
     * Match one of the characters
     * @param regExp
     * @return
     */
    public RegExp oneOf(RegExp regExp) {
        regExpString += "[" + regExp.toString() + "]";
        return this;
    }
    /**
     * Match one of the characters
     * @param s
     * @return
     */
    public RegExp oneOf(String s) {
        return oneOf(RegExp.of().text(s));
    }

    /**
     * Match any character but the specified ones
     * @param regExp
     * @return
     */
    public RegExp notOneOf(RegExp regExp) {
        regExpString += "[^" + regExp.toString() + "]";
        return this;
    }
    /**
     * Match any character but the specified ones
     * @param s
     * @return
     */
    public RegExp notOneOf(String s) {
        return notOneOf(RegExp.of().text(s));
    }

    /**
     * These characters may be present, or not.
     * @param regExp
     * @return
     */
    public RegExp optional(RegExp regExp) {
        regExpString += regExp.toString() + "?";
        return this;
    }
    /**
     * These characters may be present, or not.
     * @param s
     * @return
     */
    public RegExp optional(String s) {
        return optional(RegExp.of().text(s));
    }

    /**
     * These characters can not be present, or once, or many times
     * @param regExp
     * @return
     */
    public RegExp zeroOrMore(RegExp regExp) {
        regExpString += regExp.toString() + "*";
        return this;
    }

    /**
     * These characters can not be present, or once, or many times
     * @param s
     * @return
     */
    public RegExp zeroOrMore(String s) {
        return zeroOrMore(RegExp.of().text(s));
    }

    /**
     * These characters must be present once, but can be many times
     * @param regExp
     * @return
     */
    public RegExp oneOrMore(RegExp regExp) {
        regExpString += regExp.toString() + "+";
        return this;
    }
    /**
     * These characters must be present once, but can be many times
     * @param s
     * @return
     */
    public RegExp oneOrMore(String s) {
        return oneOrMore(RegExp.of().text(s));
    }

    /**
     * Match any of the blocks of characters
     * @param regExps
     * @return
     */
    public RegExp anyOf(RegExp... regExps) {
        boolean first = true;
        for (RegExp regExp : regExps) {
            regExpString += (first ? "(" : "|") + regExp.toString();
            first = false;
        }
        regExpString += ")";
        return this;
    }
    /**
     * Match any of the blocks of characters
     * @param ss
     * @return
     */
    public RegExp anyOf(String... ss) {
        RegExp[] regExps = new RegExp[ss.length];
        for (int i = 0; i < ss.length; i++) {
            regExps[i] = RegExp.of().text(ss[i]);
        }
        return anyOf(regExps);
    }
    /**
     * Match any of the blocks of characters
     * @param objects must be a RegExp, and otherwise it will be converted to a string
     * @return
     */
    public RegExp anyOf(Object... objects) {
        RegExp[] regExps = new RegExp[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof RegExp regExp) {
                regExps[i] = regExp;
            }
            else {
                regExps[i] = RegExp.of().text(objects[i].toString());
            }
        }
        return anyOf(regExps);
    }

    /**
     * These characters are present N times
     * @param regExp
     * @return
     */
    public RegExp occurs(int times, RegExp regExp) {
        regExpString += regExp.toString() + "{" + times + "}";
        return this;
    }
    /**
     * These characters are present N times
     * @param s
     * @return
     */
    public RegExp occurs(int times, String s) {
        return occurs(times, RegExp.of().text(s));
    }

    /**
     * These characters are present at least N times
     * @param regExp
     * @return
     */
    public RegExp occursAtLeast(int times, RegExp regExp) {
        regExpString += regExp.toString() + "{" + times + ",}";
        return this;
    }
    /**
     * These characters are present at least N times
     * @param s
     * @return
     */
    public RegExp occursAtLeast(int times, String s) {
        return occursAtLeast(times, RegExp.of().text(s));
    }

    /**
     * These characters are present N to M times
     * @param regExp
     * @return
     */
    public RegExp occursBetween(int minTimes, int maxTimes, RegExp regExp) {
        regExpString += regExp.toString() + "{" + minTimes + "," + maxTimes + "}";
        return this;
    }
    /**
     * These characters are present N to M times
     * @param s
     * @return
     */
    public RegExp occursBetween(int minTimes, int maxTimes, String s) {
        return occursBetween(minTimes, maxTimes, RegExp.of().text(s));
    }

    // -------------------------
    // LITERAL

    public RegExp anyChar() {
        regExpString += ".";
        return this;
    }

    public RegExp startOfLine() {
        regExpString += "^";
        return this;
    }

    public RegExp endOfLine() {
        regExpString += "$";
        return this;
    }

    public RegExp tab() {
        regExpString += "\\t";
        return this;
    }
    public RegExp carriageReturn() {
        regExpString += "\\r";
        return this;
    }
    public RegExp lineFeed() {
        regExpString += "\\n";
        return this;
    }
    public RegExp doubleQuote() {
        regExpString += "\\";
        return this;
    }

    /**
     * Any digit, short for [0-9]
     * @return
     */
    public RegExp digit() {
        regExpString += "\\d";
        return this;
    }

    /**
     * Any non-digit, short for [^0-9]
     * @return
     */
    public RegExp nonDigit() {
        regExpString += "\\D";
        return this;
    }

    /**
     * Any whitespace character, short for [\t\n\x0B\f\r]
     * @return
     */
    public RegExp whitespace() {
        regExpString += "\\s";
        return this;
    }

    /**
     * Any non-whitespace character, short for [^\s]
     * @return
     */
    public RegExp nonWhitespace() {
        regExpString += "\\S";
        return this;
    }

    /**
     * Any word character, short for [a-zA-Z_0-9]
     * @return
     */
    public RegExp word() {
        regExpString += "\\w";
        return this;
    }
    /**
     * Use word()
     * @return
     */
    @Deprecated
    public RegExp wordChar() {
        return word();
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    public RegExp nonWord() {
        regExpString += "\\W";
        return this;
    }
    /**
     * Use nonWord()
     * @return
     */
    @Deprecated
    public RegExp nonWordChar() {
        return nonWord();
    }

    public RegExp wordBoundary() {
        regExpString += "\\b";
        return this;
    }

    public RegExp nonWordBoundary() {
        regExpString += "\\B";
        return this;
    }

    // -------------------------
    // BUILD

    public String toString() {
        return regExpString;
    }

    public Pattern toPattern() {
        Pattern pattern = Pattern.compile(regExpString);
        return pattern;
    }

    public Matcher toMatcher(String text) {
        Matcher matcher = toPattern().matcher(text);
        return matcher;
    }

    // -------------------------
    // READABILITY

    public RegExp and() {
        return this;
    }
    public RegExp or() {
        return this;
    }
    public RegExp followedBy() {
        return this;
    }

    // -------------------------
    // SUPPORT

    private String escape(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("^", "\\^");
        s = s.replace("$", "\\$");
        s = s.replace(".", "\\.");
        s = s.replace("*", "\\*");
        s = s.replace("+", "\\+");
        s = s.replace("(", "\\(");
        s = s.replace("[", "\\[");
        s = s.replace("{", "\\{");
        return s;
    }

}
