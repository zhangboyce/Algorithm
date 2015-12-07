package apriori;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Boyce
 * Date: 30/11/15
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class SingleItem extends Item {

    protected Object value;

    public SingleItem(Object value) {
        this(1);
        this.value = value;
    }
    private SingleItem(int length) {
        super(length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleItem)) return false;

        SingleItem that = (SingleItem) o;

        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public Object value() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
