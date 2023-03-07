package org.tbee.regexpbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public RegExp group(String name, RegExp regExp) {
        startGroup(name);
        regExpString += regExp.regExpString;
        endGroup();
        return this;
    }
    public RegExp group(String name, String s) {
        return group(name, RegExp.of().exact(s));
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

    public RegExp exact(String match) {
        regExpString += escape(match);
        return this;
    }

    public RegExp oneOf(RegExp regExp) {
        regExpString += "[" + regExp.regExpString + "]";
        return this;
    }
    public RegExp oneOf(String match) {
        return oneOf(RegExp.of().exact(match));
    }

    public RegExp notOneOf(RegExp regExp) {
        regExpString += "[^" + regExp.regExpString + "]";
        return this;
    }
    public RegExp notOneOf(String match) {
        return notOneOf(RegExp.of().exact(match));
    }

    public RegExp optional(RegExp regExp) {
        regExpString += regExp.regExpString + "?";
        return this;
    }
    public RegExp optional(String s) {
        return optional(RegExp.of().exact(s));
    }

    public RegExp zeroOrMore(RegExp regExp) {
        regExpString += regExp.regExpString + "*";
        return this;
    }
    public RegExp zeroOrMore(String s) {
        return zeroOrMore(RegExp.of().exact(s));
    }

    public RegExp oneOrMore(RegExp regExp) {
        regExpString += regExp.regExpString + "+";
        return this;
    }
    public RegExp oneOrMore(String s) {
        return oneOrMore(RegExp.of().exact(s));
    }

    public RegExp occurs(int times, RegExp regExp) {
        regExpString += regExp.regExpString + "{" + times + "}";
        return this;
    }
    public RegExp occurs(int times, String s) {
        return occurs(times, RegExp.of().exact(s));
    }

    public RegExp occursAtLeast(int times, RegExp regExp) {
        regExpString += regExp.regExpString + "{" + times + ",}";
        return this;
    }
    public RegExp occursAtLeast(int times, String s) {
        return occursAtLeast(times, RegExp.of().exact(s));
    }

    public RegExp occursBetween(int minTimes, int maxTimes, RegExp regExp) {
        regExpString += regExp.regExpString + "{" + minTimes + "," + maxTimes + "}";
        return this;
    }
    public RegExp occursBetween(int minTimes, int maxTimes, String s) {
        return occursBetween(minTimes, maxTimes, RegExp.of().exact(s));
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
    public RegExp wordChar() {
        regExpString += "\\w";
        return this;
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    public RegExp nonWordChar() {
        regExpString += "\\W";
        return this;
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
