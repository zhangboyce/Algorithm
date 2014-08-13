package common.segment;

import common.utils.StringUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by boyce on 2014/6/30.
 */
public class IKSegmentor implements Segmentor {

    @Override
    public List<String> segment(String source) {

        if (StringUtils.isEmpty(source))
            return Collections.emptyList();

        long startTime = System.currentTimeMillis();

        List words = new ArrayList();
        IKSegmenter ik = new IKSegmenter(new StringReader(source), true);// 当为true时，分词器进行最大词长切分
        try {
            Lexeme lexeme = null;
            while ((lexeme = ik.next()) != null)
                words.add(lexeme.getLexemeText());
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();  //结束时间
        System.out.println("IK分词耗时" + new Float((endTime - startTime)) / 1000 + "秒!");
        System.out.println("原始文本：" + source);
        System.out.println("分词结果：" + words);

        return words;
    }
}
