package pkovacs.aoc.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

/**
 * Provides simple utility methods for processing puzzle input files.
 */
public final class InputUtils {

    private InputUtils() {
    }

    /**
     * Reads all lines of the given input file.
     */
    public static List<String> readLines(String fileName) {
        try {
            return Files.readAllLines(getPath(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Reads the given input file as an array of long integer values.
     */
    public static long[] readLongs(String fileName) {
        try {
            return scanLongs(Files.readString(getPath(fileName)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Collects long integer values from the given string.
     */
    public static long[] scanLongs(String input) {
        return Arrays.stream(input.split("[^\\d]+"))
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong).toArray();
    }

    /**
     * Reads the given input file as an array of integer values.
     */
    public static int[] readInts(String fileName) {
        try {
            return scanInts(Files.readString(getPath(fileName)));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Collects integer values from the given string.
     */
    public static int[] scanInts(String input) {
        return Arrays.stream(input.split("[^\\d]+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt).toArray();
    }

    /**
     * Reads blocks of lines, separated by blank line(s) from the given input file.
     */
    public static List<String[]> readLineBlocks(String fileName) {
        try {
            var str = Files.readString(getPath(fileName), StandardCharsets.UTF_8);
            return collectLineBlocks(str);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Collects blocks of lines, separated by blank lines from the given string.
     */
    public static List<String[]> collectLineBlocks(String input) {
        return Arrays.stream(input.replaceAll("\r\n", "\n").split("\n\n"))
                .map(block -> block.split("\n"))
                .collect(toList());
    }

    /**
     * Reads the lines of the given input file as a char matrix.
     */
    public static char[][] readCharMatrix(String fileName) {
        var lines = readLines(fileName);
        var matrix = new char[lines.size()][];
        for (int i = 0, n = matrix.length; i < n; i++) {
            matrix[i] = new char[lines.get(i).length()];
            for (int j = 0, m = matrix[i].length; j < m; j++) {
                matrix[i][j] = lines.get(i).charAt(j);
            }
        }
        return matrix;
    }

    private static Path getPath(String fileName) {
        return Path.of("input", fileName);
    }

    /**
     * Scans the given input string according to the given pattern (similarly to scanf method in C) and
     * returns the parsed values.
     * <p>
     * The given pattern may contain "%d", "%c", "%s". Otherwise it is considered as a RegEx, so be aware
     * of escaping special characters like '(', ')', '[', ']', '.', '*', '?' etc.
     * The returned list contains the parsed values in the order of their occurrence as {@link ParsedValue}s.
     *
     * @param str input string
     * @param pattern pattern string: a RegEx that may contain "%d", "%c", "%s", but must not contain capturing
     *         groups (unescaped '(' and ')')
     * @return the list of {@link ParsedValue} objects, which can be obtained as int, long, char, or String
     */
    public static List<ParsedValue> scan(String str, String pattern) throws IllegalArgumentException {
        var groupFinder = Pattern.compile("%.").matcher(pattern);
        var groupPatterns = new ArrayList<String>();
        while (groupFinder.find()) {
            groupPatterns.add(groupFinder.group());
        }

        var regex = pattern.replaceAll("%d", "(\\\\d+)")
                .replaceAll("%c", "(.)")
                .replaceAll("%s", "(.*)");

        var result = new ArrayList<ParsedValue>();
        var matcher = Pattern.compile(regex).matcher(str);
        if (matcher.matches()) {
            if (matcher.groupCount() == groupPatterns.size()) {
                for (int i = 0; i < groupPatterns.size(); i++) {
                    var groupPattern = groupPatterns.get(i);
                    var group = matcher.group(i + 1); // 0-th group is the entire match
                    result.add(ParsedValue.parse(group, groupPattern));
                }
            } else {
                throw new IllegalArgumentException(String.format(
                        "Input string '%s' has %d groups instead of expected %d for RegEx '%s'"
                                + " (created from pattern '%s').",
                        str, matcher.groupCount(), groupPatterns.size(), regex, pattern));
            }
        } else {
            throw new IllegalArgumentException(String.format(
                    "Input string '%s' does not match the RegEx '%s' (created from pattern '%s').",
                    str, regex, pattern));
        }

        return result;
    }

    /**
     * Represents a value parsed by {@link #scan(String, String)}.
     */
    public final static class ParsedValue {

        private final Object value;

        private ParsedValue(Object value) {
            this.value = value;
        }

        private static ParsedValue parse(String s, String pattern) {
            if ("%d".equals(pattern)) {
                return new ParsedValue(Long.parseLong(s));
            } else if ("%c".equals(pattern)) {
                return new ParsedValue(s.charAt(0));
            } else {
                return new ParsedValue(s);
            }
        }

        public int asInt() {
            return (int) (long) value;
        }

        public long asLong() {
            return (long) value;
        }

        public char asChar() {
            return (char) value;
        }

        public String asString() {
            return value.toString();
        }

        public String get() {
            return value.toString();
        }

        public boolean isInteger() {
            return value.getClass().equals(Long.class);
        }

        public boolean isChar() {
            return value.getClass().equals(Character.class);
        }

        public boolean isString() {
            return value.getClass().equals(String.class);
        }

        @Override
        public String toString() {
            return "ParsedValue(" + value.getClass().getSimpleName() + ": " + value + ")";
        }

    }

}
