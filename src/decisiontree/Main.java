package decisiontree;

import java.util.List;
import common.utils.FileUtils;

import java.io.File;

/**
 * User: Boyce
 * Date: 16/12/15
 * Time: 17:44
 */
public class Main {
    public static void main(String[] args) {

        List<String> lines = FileUtils.readLines(Main.class.getResourceAsStream("examples.csv"));
        String[] headers = lines.get(0).split(",");
        int s = headers.length;
        Examples examples = new Examples();
        for(int i=1; i<lines.size(); i++) {
            String[] values = lines.get(i).split(",");
            Example e = new Example(i+"", values[s-1].trim(), examples);
            for (int j=0;j<s-1;j++) {
                e.addAttribute(headers[j].trim(), values[j].trim());
            }
            examples.addExample(e);
        }

        System.out.println(examples);
        System.out.println(examples.entropy());

        DecisionTree decisionTree = new DecisionTree(examples);
        decisionTree.build();

        System.out.println(decisionTree);
    }
}
