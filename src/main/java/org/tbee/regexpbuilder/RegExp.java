package org.tbee.regexpbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

    private String regExp = "";
    private int groupIdx = 0;
    private Map<String, Integer> groupNameToIdx = new HashMap<>();

    public static RegExp of() {
        return new RegExp();
    }

    public RegExp exact(String match) {
        regExp += escape(match);
        return this;
    }

    public RegExp oneOf(String match) {
        regExp += "[" + escape(match) + "]";
        return this;
    }

    public RegExp notOneOf(String match) {
        regExp += "[^" + escape(match) + "]";
        return this;
    }

    public RegExp optional(Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "?";
        return this;
    }
    public RegExp optional(String s) {
        return optional(re -> re.exact(s));
    }

    public RegExp zeroOrMore(Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "*";
        return this;
    }
    public RegExp zeroOrMore(String s) {
        return zeroOrMore(re -> re.exact(s));
    }

    public RegExp oneOrMore(Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "+";
        return this;
    }
    public RegExp oneOrMore(String s) {
        return oneOrMore(re -> re.exact(s));
    }

    public RegExp occurs(int times, Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "{" + times + "}";
        return this;
    }
    public RegExp occurs(int times, String s) {
        return occurs(times, re -> re.exact(s));
    }

    public RegExp occursAtLeast(int times, Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "{" + times + ",}";
        return this;
    }
    public RegExp occursAtLeast(int times, String s) {
        return occursAtLeast(times, re -> re.exact(s));
    }

    public RegExp occursBetween(int minTimes, int maxTimes, Consumer<RegExp> consumer) {
        group(re -> consumer.accept(re));
        regExp += "{" + minTimes + "," + maxTimes + "}";
        return this;
    }
    public RegExp occursBetween(int minTimes, int maxTimes, String s) {
        return occursBetween(minTimes, maxTimes, re -> re.exact(s));
    }

    private RegExp startGroup(String name) {
        regExp += "(";
        groupIdx++;
        if (name != null) {
            if (groupNameToIdx.containsKey(name)) {
                throw new RuntimeException("Group '" + name + "' already exists");
            }
            groupNameToIdx.put(name, groupIdx);
        }
        return this;
    }
    private RegExp startGroup() {
        return startGroup(null);
    }

    private RegExp endGroup() {
        regExp += ")";
        return this;
    }

    public RegExp group(String name, Consumer<RegExp> consumer) {
        startGroup(name);
        consumer.accept(this);
        endGroup();
        return this;
    }
    public RegExp group(Consumer<RegExp> consumer) {
        return group(null, consumer);
    }

    public int getGroupIdx(String name) {
        return groupNameToIdx.get(name);
    }

    public RegExp referToGroup(String name) {
        regExp += "\\" + groupNameToIdx.get(name);
        return this;
    }

    public RegExp anyChar() {
        regExp += ".";
        return this;
    }

    public RegExp startOfLine() {
        regExp += "^";
        return this;
    }

    public RegExp endOfLine() {
        regExp += "$";
        return this;
    }

    /**
     * Any digit, short for [0-9]
     * @return
     */
    public RegExp digit() {
        regExp += "\\d";
        return this;
    }

    /**
     * Any non-digit, short for [^0-9]
     * @return
     */
    public RegExp nonDigit() {
        regExp += "\\D";
        return this;
    }

    /**
     * Any whitespace character, short for [\t\n\x0B\f\r]
     * @return
     */
    public RegExp whitespace() {
        regExp += "\\s";
        return this;
    }

    /**
     * Any non-whitespace character, short for [^\s]
     * @return
     */
    public RegExp nonWhitespace() {
        regExp += "\\S";
        return this;
    }

    /**
     * Any word character, short for [a-zA-Z_0-9]
     * @return
     */
    public RegExp wordChar() {
        regExp += "\\w";
        return this;
    }

    /**
     * Any non-word character, short for [^\w]
     * @return
     */
    public RegExp nonWordChar() {
        regExp += "\\W";
        return this;
    }

    public RegExp wordBoundary() {
        regExp += "\\b";
        return this;
    }

    public RegExp nonWordBoundary() {
        regExp += "\\B";
        return this;
    }

    public String toString() {
        return regExp;
    }

    public Pattern toPattern() {
        Pattern pattern = Pattern.compile(regExp);
        return pattern;
    }

    public Matcher toMatcher(String text) {
        Matcher matcher = toPattern().matcher(text);
        return matcher;
    }

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
