package org.tbee.regexpbuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegExpBuild {
    Pattern toPattern();
    Matcher toMatcher(String text);
    int indexOf(String name);
}