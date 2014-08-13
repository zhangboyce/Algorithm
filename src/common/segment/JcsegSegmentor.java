package common.segment;

import common.utils.StringUtils;
import org.lionsoul.jcseg.core.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by boyce on 2014/6/30.
 */
public class JcsegSegmentor implements Segmentor {

    private static final ThreadLocal<ISegment> I_SEGMENT_THREAD_LOCAL = new ThreadLocal<ISegment>();

    private ISegment getInstance() {
        ISegment iSegment = I_SEGMENT_THREAD_LOCAL.get();
        if (null == iSegment) {
            JcsegTaskConfig jcsegTaskConfig = new JcsegTaskConfig();
            ADictionary aDictionary = DictionaryFactory.createDefaultDictionary(jcsegTaskConfig);
            try {
                iSegment = SegmentFactory.createJcseg(JcsegTaskConfig.COMPLEX_MODE, new Object[]{jcsegTaskConfig, aDictionary});
                I_SEGMENT_THREAD_LOCAL.set(iSegment);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        return iSegment;
    }

    @Override
    public List<String> segment(String source) {

        if (StringUtils.isEmpty(source))
            return Collections.emptyList();

        long startTime = System.currentTimeMillis();

        List words = new ArrayList();
        try {
            ISegment seg = getInstance();
            seg.reset(new StringReader(source));

            // 获取分词结果
            IWord word = null;
            while ((word = seg.next()) != null) {
                words.add(word.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();  //结束时间
        System.out.println("Jcseg分词耗时" + new Float((endTime - startTime)) / 1000 + "秒!");
        System.out.println("原始文本：" + source);
        System.out.println("分词结果：" + words);

        return words;
    }


}
