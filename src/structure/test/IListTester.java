package structure.test;

import structure.list.IList;

/**
 * Created by boyce on 2014/7/6.
 */
public class IListTester {
    private IList iList;

    public IListTester(IList iList) {
        this.iList = iList;
    }

    public void testAll() {
        this.testAdd();
        this.testGet();
        this.testInsert();
        this.testRemove();
        this.testIndexOf();
    }

    public void testAdd() {
        iList.clear();

        iList.add("a");

        System.out.println(iList.isEmpty());
        System.out.println(iList.size());
        System.out.println(iList);

        assert iList.size() == 1;
        assert !iList.isEmpty();
        assert iList.toString().equals("[a]");

        System.out.println("------------------------------------");
    }

    public void testInsert() {
        iList.clear();

        iList.add("a");
        iList.add("b");
        iList.add("c");
        iList.add("e");
        iList.add("f");

        System.out.println(iList.size());
        System.out.println(iList);

        assert iList.size() == 5;
        assert iList.toString().equals("[a, b, c, e, f]");

        System.out.println("------------------------------------");

        iList.insert(3, "d");

        assert iList.size() == 6;
        assert iList.toString().equals("[a, b, c, e, f]");

        System.out.println(iList.size());
        System.out.println(iList);
        System.out.println("------------------------------------");
    }

    public void testIndexOf() {
        iList.clear();

        iList.add("a");
        iList.add("b");
        iList.add("c");
        iList.add("d");
        iList.add("e");
        iList.add("f");

        assert iList.indexOf("e") == 4;
        assert iList.indexOf("h") == -1;
        assert iList.indexOf("a") == 0;
        assert iList.indexOf("f") == 5;

        System.out.println(iList.indexOf("e"));
        System.out.println(iList.indexOf("h"));
        System.out.println(iList.indexOf("a"));
        System.out.println(iList.indexOf("f"));
        System.out.println("------------------------------------");
    }

    public void testGet() {
        iList.clear();

        iList.add("a");
        iList.add("b");
        iList.add("c");
        iList.add("d");
        iList.add("e");
        iList.add("f");

        assert iList.get(0).equals("a");
        assert iList.get(iList.size()-1).equals("f");
        assert iList.get(3).equals("d");

        System.out.println(iList.get(0));
        System.out.println(iList.get(iList.size()-1));
        System.out.println(iList.get(3));
        System.out.println("------------------------------------");
    }

    public void testRemove() {
        iList.clear();

        iList.add("a");
        iList.add("b");
        iList.add("c");
        iList.add("d");
        iList.add("e");
        iList.add("f");

        iList.remove(3);
        iList.remove("a");
        iList.remove("f");

        assert iList.size() == 3;
        assert iList.toString().equals("[b, c, e]");

        System.out.println(iList.size());
        System.out.println(iList);
        System.out.println("------------------------------------");
    }
}
