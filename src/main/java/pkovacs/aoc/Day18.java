package pkovacs.aoc;

import java.util.ArrayDeque;
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
        // return lines.stream().mapToLong(expr -> evaluateSimple(expr, advanced)).sum();
    }

    /**
     * Evaluates the expression by transforming it to "reverse Polish notation" (aka. postfix notation).
     */
    private static long evaluate(String expression, boolean advanced) {
        var rpn = new ArrayList<Character>();
        var opStack = new ArrayDeque<Character>();
        var expr = expression.replace(" ", "").toCharArray();
        for (char c : expr) {
            if (c == '(') {
                opStack.push(c);
            } else if (c == ')') {
                while (opStack.getFirst() != '(') {
                    rpn.add(opStack.pop());
                }
                opStack.pop();
            } else if (c == '+' || c == '*') {
                while (!opStack.isEmpty() && getPrecedence(opStack.getFirst(), advanced) <= getPrecedence(c,
                        advanced)) {
                    rpn.add(opStack.pop());
                }
                opStack.push(c);
            } else {
                rpn.add(c);
            }
        }
        while (!opStack.isEmpty()) {
            rpn.add(opStack.pop());
        }

        var valStack = new ArrayDeque<Long>();
        for (Character c : rpn) {
            if (c == '+' || c == '*') {
                var v2 = valStack.pop();
                var v1 = valStack.pop();
                valStack.push(c == '*' ? v1 * v2 : v1 + v2);
            } else {
                valStack.push((long) (c - '0'));
            }
        }

        return valStack.getFirst();
    }

    private static int getPrecedence(char ch, boolean advanced) {
        return ch == '(' ? 2 : (advanced && ch == '*' ? 1 : 0);
    }

    /**
     * Evaluates the expression using a simple method: assigns a precedence score to each operator,
     * also considering parentheses. Then evaluates the operators in decreasing order of their precedence.
     */
    private static long evaluateSimple(String expression, boolean advanced) {
        var list = new ArrayList<Token>();
        int prec = 0;
        int precStep = advanced ? 2 : 1;
        var expr = expression.replace(" ", "").toCharArray();
        for (char c : expr) {
            if (c == '(') {
                prec += precStep;
            } else if (c == ')') {
                prec -= precStep;
            } else {
                list.add(new Token(c, prec + (advanced && c == '+' ? 1 : 0)));
            }
        }

        int maxPrec = list.stream().mapToInt(t -> t.precedence).max().getAsInt();
        for (int p = maxPrec; p >= 0; p--) {
            var newList = new ArrayList<Token>();
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1 && list.get(i).precedence == p) {
                    int j = newList.size() - 1;
                    newList.set(j, Token.eval(newList.get(j), list.get(i), list.get(i + 1)));
                    i++; // skip additional element
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
