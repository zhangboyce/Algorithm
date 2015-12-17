package decisiontree;

/**
 * User: Boyce
 * Date: 16/12/15
 * Time: 17:44
 */
public class Main {
    public static void main(String[] args) {
        String header = "age,hasJob,ownHouse,creditRating,class";
        String r1 = "young,false,false,fair,no";
        String r2 = "young,false,false,good,no";
        String r3 = "young,true,false,good,yes";
        String r4 = "young,true,true,fair,yes";
        String r5 = "young,false,false,fair,no";
        String r6 = "middle,false,false,fair,no";
        String r7 = "middle,false,false,good,no";
        String r8 = "middle,true,true,good,yes";
        String r9 = "middle,false,true,excellent,yes";
        String r10 = "middle,false,true,excellent,yes";
        String r11 = "old,false,true,excellent,yes";
        String r12 = "old,false,true,good,yes";
        String r13 = "old,true,false,good,yes";
        String r14 = "old,true,false,excellent,yes";
        String r15 = "old,false,false,fair,no";

        String[] rs = {r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15};
        String[] headers = header.split(",");
        int s = headers.length;

        for(int i=0; i<rs.length; i++) {
            String[] values = rs[i].split(",");
            Example e = new Example(i+"", values[s-1]);
            for (int j=0;j<s-1;j++) {
                e.addAttribute(headers[j], values[j]);
            }
            Examples.addExample(e);
        }

        System.out.println(Examples.asString());
        System.out.println(Examples.entropy());
    }
}
