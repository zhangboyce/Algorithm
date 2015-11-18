package expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 3/11/15
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public class LogicExpressionParser {
    private ExpressionReader reader;
    private LogicExpressionParser(String expression) {
        this.reader = new ExpressionReader(expression);
    }

    public static LogicExpression parse(String expression) {
        try {
            LogicExpressionParser parser = new LogicExpressionParser(expression);
            LogicExpression le = parser.calculatePostfixExpression();
            return le;
        } catch (Exception e) {
            System.err.println("Parse expression:("+expression+") error.");
            e.printStackTrace();
        }
        return null;
    }

    private LogicExpression calculatePostfixExpression() {
        List<String> postfixExpression = new ArrayList<String>();
        Stack<Character> operators = new Stack<Character>();
        while(!reader.isEmpty()) {
            String str = reader.consumeToAny(operators());
            if (!"".equals(str)) {
                postfixExpression.add(str);
            }
            char c = reader.consume();
            if ((char)-1 == c) break;

            if (operators.isEmpty() || isLeftBracket(c))
                operators.push(c);
            else {
                char pop = operators.peek();
                //如果遇到右括号，将所有的操作符弹出直到遇到左括号
                if (isRightBracket(c)) {
                    while (!isLeftBracket(operators.peek()))
                        postfixExpression.add(String.valueOf(operators.pop()));
                    //将栈中的左括号pop删除
                    operators.pop();
                } else if (compareOperator(c, pop) > 0)
                    operators.push(c);
                else{
                    while (!operators.isEmpty()
                            && compareOperator(c, operators.peek()) <= 0
                            && !isLeftBracket(operators.peek())) {

                        postfixExpression.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                }
            }
        }

        while (!operators.isEmpty())
            postfixExpression.add(String.valueOf(operators.pop()));

        //
        Stack<LogicExpression> resultStack = new Stack<LogicExpression>();
        for (String s: postfixExpression) {
            if (isOperator(s)) {
                LogicExpression le1 = resultStack.pop();
                if ("^".equals(s))
                    resultStack.push(new LogicExpression.Not(le1));
                if ("&".equals(s)) {
                    LogicExpression le2 = resultStack.pop();
                    resultStack.push(new LogicExpression.And(le1, le2));
                }
                if ("|".equals(s)) {
                    LogicExpression le2 = resultStack.pop();
                    resultStack.push(new LogicExpression.Or(le1, le2));
                }
            } else
                resultStack.push(new LogicExpression.Primitives(s));
        }
        LogicExpression expression = resultStack.pop();
        return expression;
    }

    private static int compareOperator(char c1, char c2) {
       return "|&^".indexOf(c1) - "|&^".indexOf(c2);
    }

    private static char[] operators() {
        return new char[]{'^', '&', '|', '(', ')'};
    }

    private static boolean isOperator(String s) {
        return "|&^".indexOf(s) != -1;
    }

    private static boolean isLeftBracket(char c) {
        return c == '(';
    }

    private static boolean isRightBracket(char c) {
        return c == ')';
    }

    static class ExpressionReader {
        private char[] input;
        private int pos;
        private int length;

        public ExpressionReader(String expression) {
            if (null == expression || expression.trim().equals(""))
                throw new IllegalArgumentException("Expression cannot be null or empty.");

            this.input = expression.toCharArray();
            this.pos = 0;
            this.length = expression.length();
        }

        public boolean isEmpty() {
            return pos >= length;
        }

        public int pos() {
            return pos;
        }

        public char current() {
            char c = isEmpty()?(char)-1:input[pos];
            return c;
        }

        public char consume() {
            char c = current();
            pos ++;
            return c;
        }

        public void unConsume() {
            pos --;
        }

        public void advance() {
            pos++;
        }

        int nextIndexOf(char c) {
            for (int i = pos; i < length; i++) {
                if (c == input[i])
                    return i - pos;
            }
            return -1;
        }

        String consumeTo(char c) {
            int offset = nextIndexOf(c);
            if (offset != -1) {
                String consumed = new String(input, pos, offset);
                pos += offset;
                return consumed;
            } else {
                return "";
            }
        }

        String consumeToAny(final char... chars) {
            final int start = pos;
            final int remaining = length;

            OUTER:
            while (pos < remaining) {
                for (char c : chars) {
                    if (input[pos] == c)
                        break OUTER;
                }
                pos++;
            }
            return pos > start ? new String(input, start, pos-start) : "";
        }
    }

    public static void main(String[] args) {
        String str = "万里|^长城";
        LogicExpression expression = LogicExpressionParser.parse(str);
        System.out.println(expression);
        System.out.println(expression.matches("万里长城万里长"));
        System.out.println(expression.matches("万里喝酒抽烟电源"));
    }
}
