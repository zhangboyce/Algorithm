package algorithm.distance;

import common.utils.SegmenterUtils;
import common.utils.StringUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by boyce on 2014/6/30.
 */
public class SimHash {

    public static void main(String[] args) {
        String s = "This is a string for testing";
        List<String> words = SegmenterUtils.IKSEG.segment(s);
        SimHash simHash1 = new SimHash(words, 128);
        System.out.println(simHash1.simHash());

        s = "This a test string testing also";
        words = SegmenterUtils.IKSEG.segment(s);
        SimHash simHash2 = new SimHash(words, 128);
        System.out.println(simHash2.simHash());

        s = "This is a test string for testing als";
        words = SegmenterUtils.IKSEG.segment(s);
        SimHash simHash3 = new SimHash(words, 128);
        System.out.println(simHash3.simHash());

        System.out.println("============================");
        System.out.println(simHash1.hammingDistance(simHash2));
        System.out.println(simHash1.hammingDistance(simHash3));
    }

    private List<String> tokens;
    private int hashBits = 128;

    public SimHash(List<String> tokens) {
        this.tokens = tokens;
    }
    public SimHash(List<String> tokens, int hashBits) {
        this.tokens = tokens;
        this.hashBits = hashBits;
    }

    public BigInteger simHash() {
        int[] v = new int[this.hashBits];
        for (String token: tokens) {
            BigInteger h = this.hash(token);
            for (int i=0; i<this.hashBits; i++) {
                BigInteger bitMask = new BigInteger("1").shiftLeft(i);
                if (h.and(bitMask).signum() != 0)
                    v[i] += 1;
                else
                    v[i] -= 1;
            }
        }

        BigInteger fingerPrint = new BigInteger("0");
        for (int i=0; i<this.hashBits; i++) {
            if (v[i] >= 0)
                fingerPrint = fingerPrint.add(new BigInteger("1")).shiftLeft(i);
        }

        return fingerPrint;
    }

    public int hammingDistance(SimHash other) {
        BigInteger m = new BigInteger("1").shiftLeft(this.hashBits).subtract(
                new BigInteger("1"));
        BigInteger x = this.simHash().xor(other.simHash()).and(m);
        int tot = 0;
        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    private BigInteger hash(String source) {
        if (StringUtils.isEmpty(source))
            return new BigInteger("0");
        else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf((long)sourceArray[0] << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashBits).subtract(new BigInteger("1"));

            for (char item: sourceArray) {
                BigInteger temp = BigInteger.valueOf((long)item);
                x = x.multiply(m).xor(temp).and(mask);
            }

            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    //我觉得下面这个算法计算1的个数很高效:
   public static int count(int v)
    {
        int num = 0;
        while(v > 0)
        {
            v &= (v-1);
            num ++;
        }
        return num;
    }
}
