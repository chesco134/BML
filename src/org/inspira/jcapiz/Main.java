package org.inspira.jcapiz;

public class Main {

	public static void main(String[] args) {

        java.util.regex.Pattern NUM_PATTERN = java.util.regex.Pattern.compile("[0-9]+");
        java.util.regex.Matcher numPatternMatcher = NUM_PATTERN.matcher("mm_04");
        numPatternMatcher.find();
        System.out.println(numPatternMatcher.group());
	}
}
