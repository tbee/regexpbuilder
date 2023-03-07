package org.tbee.regexpbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp implements RegExpStart {

    private String regExpString = "";
    private int groupIdx = 0;
    private Map<String, Integer> groupNameToIdx = new HashMap<>();

    // -------------------------
    // FACTORY

    public static RegExpStart of() {
        return new RegExp();
    }


    // -------------------------
    // COMPLEX

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

    public RegExpCore group(String name, RegExpCore regExp) {
        startGroup(name);
        regExpString += regExp.toString();
        endGroup();
        return this;
    }
    public RegExpCore group(String name, String s) {
        return group(name, RegExp.of().text(s));
    }

    public RegExpCore referToGroup(String name) {
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

    public RegExpCore text(String match) {
        regExpString += escape(match);
        return this;
    }

    public RegExpCore oneOf(RegExpCore regExp) {
        regExpString += "[" + regExp.toString() + "]";
        return this;
    }
    public RegExpCore oneOf(String match) {
        return oneOf(RegExp.of().text(match));
    }

    public RegExpCore notOneOf(RegExpCore regExp) {
        regExpString += "[^" + regExp.toString() + "]";
        return this;
    }
    public RegExpCore notOneOf(String match) {
        return notOneOf(RegExp.of().text(match));
    }

    public RegExpCore optional(RegExpCore regExp) {
        regExpString += regExp.toString() + "?";
        return this;
    }
    public RegExpCore optional(String s) {
        return optional(RegExp.of().text(s));
    }

    public RegExpCore zeroOrMore(RegExpCore regExp) {
        regExpString += regExp.toString() + "*";
        return this;
    }
    public RegExpCore zeroOrMore(String s) {
        return zeroOrMore(RegExp.of().text(s));
    }

    public RegExpCore oneOrMore(RegExpCore regExp) {
        regExpString += regExp.toString() + "+";
        return this;
    }
    public RegExpCore oneOrMore(String s) {
        return oneOrMore(RegExp.of().text(s));
    }

    public RegExpCore occurs(int times, RegExpCore regExp) {
        regExpString += regExp.toString() + "{" + times + "}";
        return this;
    }
    public RegExpCore occurs(int times, String s) {
        return occurs(times, RegExp.of().text(s));
    }

    public RegExpCore occursAtLeast(int times, RegExpCore regExp) {
        regExpString += regExp.toString() + "{" + times + ",}";
        return this;
    }
    public RegExpCore occursAtLeast(int times, String s) {
        return occursAtLeast(times, RegExp.of().text(s));
    }

    public RegExpCore occursBetween(int minTimes, int maxTimes, RegExpCore regExp) {
        regExpString += regExp.toString() + "{" + minTimes + "," + maxTimes + "}";
        return this;
    }
    public RegExpCore occursBetween(int minTimes, int maxTimes, String s) {
        return occursBetween(minTimes, maxTimes, RegExp.of().text(s));
    }

    // -------------------------
    // LITERAL

    public RegExpCore anyChar() {
        regExpString += ".";
        return this;
    }

    public RegExpCore startOfLine() {
        regExpString += "^";
        return this;
    }

    public RegExpCore endOfLine() {
        regExpString += "$";
        return this;
    }

    /**
     * Any digit, short for [0-9]
     * @return
     */
    public RegExpCore digit() {
        regExpString += "\\d";
        return this;
    }

    /**
     * Any non-digit, short for [^0-9]
     * @return
     */
    public RegExpCore nonDigit() {
        regExpString += "\\D";
        return this;
    }

    /**
     * Any whitespace character, short for [\t\n\x0B\f\r]
     * @return
     */
    public RegExpCore whitespace() {
        regExpString += "\\s";
        return this;
    }

    /**
     * Any non-whitespace character, short for [^\s]
     * @return
     */
    public RegExpCore nonWhitespace() {
        regExpString += "\\S";
        return this;
    }

    /**
     * Any word character, short for [a-zA-Z_0-9]
     * @return
     */
    public RegExpCore wordChar() {
        regExpString += "\\w";
        return this;
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    public RegExpCore nonWordChar() {
        regExpString += "\\W";
        return this;
    }

    public RegExpCore wordBoundary() {
        regExpString += "\\b";
        return this;
    }

    public RegExpCore nonWordBoundary() {
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

    public RegExpCore and() {
        return this;
    }
    public RegExpCore or() {
        return this;
    }
    public RegExpCore followedBy() {
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
