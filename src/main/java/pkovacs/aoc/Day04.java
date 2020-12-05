package pkovacs.aoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

public class Day04 {

    public static void main(String[] args) throws IOException {
        List<String> lines = IOUtils.readLines(Day04.class.getResourceAsStream("day04.txt"), StandardCharsets.UTF_8);

        var fields = new HashMap<String, String>();
        int cnt1 = 0;
        int cnt2 = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                // Blank line, the end of a record --> let's check it
                if (isValid(fields, false)) {
                    cnt1++;
                }
                if (isValid(fields, true)) {
                    cnt2++;
                }
                fields.clear();
            } else {
                String[] parts = line.split("[ \t]");
                for (String part : parts) {
                    String[] x = part.split(":");
                    fields.put(x[0], x[1]);
                }
            }
        }

        System.out.println("Puzzle 1: " + cnt1);
        System.out.println("Puzzle 2: " + cnt2);
    }

    private static boolean isValid(Map<String, String> fields, boolean checkValues) {
        fields.remove("cid"); // remove optional field
        if (!fields.keySet().equals(Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))) {
            return false;
        }
        if (!checkValues) {
            return true;
        }

        var byr = fields.get("byr");
        boolean valid = byr.length() == 4 && checkInteger(byr, 1920, 2002);
        var iyr = fields.get("iyr");
        valid &= iyr.length() == 4 && checkInteger(iyr, 2010, 2020);
        var eyr = fields.get("eyr");
        valid &= eyr.length() == 4 && checkInteger(eyr, 2020, 2030);
        var hgt = fields.get("hgt");
        valid &= (hgt.endsWith("cm") && checkInteger(hgt.substring(0, hgt.length() - 2), 150, 193))
                || (hgt.endsWith("in") && checkInteger(hgt.substring(0, hgt.length() - 2), 59, 76));
        var hcl = fields.get("hcl");
        valid &= Pattern.matches("#[0-9a-f]{6}", hcl);
        var ecl = fields.get("ecl");
        valid &= Arrays.stream("amb blu brn gry grn hzl oth".split(" ")).anyMatch(ecl::equals);
        var pid = fields.get("pid");
        valid &= Pattern.matches("[0-9]{9}", pid);

        return valid;
    }

    private static boolean checkInteger(String value, int min, int max) {
        try {
            int x = Integer.parseInt(value);
            return x >= min && x <= max;
        } catch (Exception e) {
            return false;
        }
    }

}
