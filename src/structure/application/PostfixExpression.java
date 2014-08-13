package structure.application;

import common.utils.StringUtils;
import structure.stack.IArrayStack;
import structure.stack.ILinkedStack;
import structure.stack.IStack;

/**
 * Created by boyce on 2014/7/27.
 */
public class PostfixExpression {

    public static void main(String[] args) {
        String expression = "2+(3+4)*5+1";
        String postfixExpression = PostfixExpression.toPostfixExpression(expression);
        System.out.println(postfixExpression);

        int result = PostfixExpression.calculatePostfixExpression(postfixExpression);
        System.out.println(result);
    }

    /**
     * 计算一个后缀表达式的值
     * @param expression 后缀表达式
     * @return 值
     */
    public static int calculatePostfixExpression(String expression) {
        if (StringUtils.isEmpty(expression))
            return 0;

        char[] chars = expression.toCharArray();
        IStack<Integer> resultStack = new IArrayStack<Integer>();
        for (char c: chars) {
            if (isOperator(c)) {
                int a1 = resultStack.pop();
                int a2 = resultStack.pop();

                if (c == '+')
                    resultStack.push(a2 + a1);
                if (c == '-')
                    resultStack.push(a2 - a1);
                if (c == '*')
                    resultStack.push(a2 * a1);
                if (c == '/')
                    resultStack.push(a2 / a1);
            } else
                resultStack.push(Integer.valueOf(c + ""));
        }

        return resultStack.pop();
    }

    /**
     * 将一个中缀表达式转换成一个后缀表达式
     * @param expression 中缀表达式
     * @return 后缀表达式
     */
    public static String toPostfixExpression(String expression) {
        if (StringUtils.isEmpty(expression))
            return expression;

        String postfixExpression  = "";
        IStack<Character> operators = new ILinkedStack<Character>();
        char[] chars = expression.toCharArray();

        for (char c: chars) {
            if (isOperator(c)) {
                if (operators.isEmpty() || isLeftBracket(c))
                    operators.push(c);
                else {
                    char pop = operators.peek();
                    //如果遇到右括号，将所有的操作符弹出直到遇到左括号
                    if (isRightBracket(c)) {
                        while (!isLeftBracket(operators.peek()))
                           postfixExpression += operators.pop();

                        //将栈中的左括号pop删除
                        operators.pop();
                    } else if (compareOperator(c, pop) > 0)
                        operators.push(c);
                    else{
                        while (!operators.isEmpty()
                                && compareOperator(c, operators.peek()) <= 0
                                && !isLeftBracket(operators.peek())) {

                            postfixExpression += operators.pop();
                        }
                        operators.push(c);
                    }

                }
            }
            else
                postfixExpression += c;
        }

        while (!operators.isEmpty())
            postfixExpression += operators.pop();

        return postfixExpression;
    }

    private static int compareOperator(char c1, char c2) {

        if ((c1 == '+' || c1 == '-') && (c2 == '*' || c2 == '/')) return -1;

        if ((c1 == '*' || c1 == '/') && (c2 == '+' || c2 == '-')) return 1;

        return 0;
    }

    private static boolean isOperator(char c) {
        return  c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/' ||
                c == '(' ||
                c == ')';
    }

    private static boolean isLeftBracket(char c) {
        return c == '(';
    }

    private static boolean isRightBracket(char c) {
        return c == ')';
    }


}
