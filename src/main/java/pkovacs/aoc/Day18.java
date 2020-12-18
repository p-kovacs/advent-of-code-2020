package pkovacs.aoc;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day18 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day18.txt");

        System.out.println("Part 1: " + solve(lines, false));
        System.out.println("Part 2: " + solve(lines, true));
    }

    private static long solve(List<String> lines, boolean advanced) {
        return lines.stream().mapToLong(expr -> evaluate(expr, advanced)).sum();
    }

    private static long evaluate(String expression, boolean advanced) {
        var list = new ArrayList<Token>();
        int prec = 0;
        int precStep = advanced ? 2 : 1;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c == '(') {
                prec += precStep;
            } else if (c == ')') {
                prec -= precStep;
            } else {
                list.add(new Token(c, prec + ((advanced && c == '+') ? 1 : 0)));
            }
        }

        int maxPrec = list.stream().mapToInt(t -> t.precedence).max().getAsInt();
        for (int k = maxPrec; k >= 0; k--) {
            var newList = new ArrayList<Token>();
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1 && list.get(i).precedence == k) {
                    int j = newList.size() - 1;
                    newList.set(j, Token.eval(newList.get(j), list.get(i), list.get(i + 1)));
                    i++; // skip one element
                } else {
                    newList.add(list.get(i));
                }
            }
            list = newList;
        }

        return list.get(0).value; // list should be 1-length
    }

    private static class Token {

        Character operator;
        long value;
        int precedence;

        Token(char ch, int prec) {
            if (ch >= '0' && ch <= '9') {
                value = ch - '0';
                precedence = -1;
            } else {
                operator = ch;
                precedence = prec;
            }
        }

        Token(long val) {
            value = val;
            precedence = -1;
        }

        static Token eval(Token a, Token op, Token b) {
            if (op.operator == '+') {
                return new Token(a.value + b.value);
            } else if (op.operator == '*') {
                return new Token(a.value * b.value);
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public String toString() {
            return precedence < 0 ? String.valueOf(value) : (operator + ":" + precedence);
        }

    }

}
