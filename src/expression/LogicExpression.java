package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 3/11/15
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public abstract class LogicExpression {

    public abstract boolean matches(String source);

    public static class Primitives extends LogicExpression {
        private String target;
        public Primitives(String target) {
            this.target = target;
        }

        @Override
        public boolean matches(String source) {
            return null != source && source.toLowerCase().contains(target.toLowerCase());
        }

        @Override
        public String toString() {
            return target;
        }
    }

    public static class Regex extends LogicExpression {
        private Pattern pattern;

        public Regex(Pattern pattern) {
            this.pattern = pattern;
        }

        @Override
        public boolean matches(String source) {
            Matcher m = pattern.matcher(source);
            return m.find();
        }
    }

    public static class Not extends LogicExpression {
        private LogicExpression expression;

        public Not(LogicExpression expression) {
            this.expression = expression;
        }

        @Override
        public boolean matches(String source) {
            return !expression.matches(source);
        }

        @Override
        public String toString() {
            return "^" + expression.toString();
        }
    }

    public static class And extends LogicExpression {
        private LogicExpression expression1;
        private LogicExpression expression2;

        public And(LogicExpression expression1, LogicExpression expression2) {
            this.expression1 = expression1;
            this.expression2 = expression2;
        }

        @Override
        public boolean matches(String source) {
            return expression1.matches(source)
                    && expression2.matches(source);
        }

        @Override
        public String toString() {
            return expression1.toString() + "&" + expression2.toString();
        }
    }

    public static class Or extends LogicExpression {
        private LogicExpression expression1;
        private LogicExpression expression2;

        public Or(LogicExpression expression1, LogicExpression expression2) {
            this.expression1 = expression1;
            this.expression2 = expression2;
        }

        @Override
        public boolean matches(String source) {
            return expression1.matches(source)
                    || expression2.matches(source);
        }

        @Override
        public String toString() {
            return expression1.toString() + "|" + expression2.toString();
        }
    }

}
