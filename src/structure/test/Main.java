package structure.test;

import structure.list.IArrayList;
import structure.list.IDCLinkedList;
import structure.list.ILinkedList;
import structure.list.IList;

/**
 * Created by boyce on 2014/7/6.
 */
public class Main {
    public static void main(String[] args) {
        IList iList = new IArrayList();
        structure.test.IListTester tester = new structure.test.IListTester(iList);
        //tester.testAll();

        iList = new ILinkedList();
        tester = new structure.test.IListTester(iList);
        //tester.testAll();

        iList = new IDCLinkedList();
        tester = new structure.test.IListTester(iList);
        tester.testAll();
    }
}
