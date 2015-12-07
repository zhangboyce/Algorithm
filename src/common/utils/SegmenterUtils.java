package common.utils;


import org.lionsoul.jcseg.core.*;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import java.io.*;
import java.util.*;

/**
 * Created by boyce on 2014/6/30.
 */
public abstract class SegmenterUtils {

    private static final ThreadLocal<ISegment> I_SEGMENT_THREAD_LOCAL = new ThreadLocal<ISegment>();

    static {
        Configuration configuration = DefaultConfig.getInstance();
        Dictionary.initial(configuration);
    }

    public interface Segmentor {
        public List<String> segment(String source);
        public void addDicWords (Collection<String> dic);
        public void addDisableWords(Collection<String> stopWords);
        public void addStopWords(Collection<String> stopWords);
        public void removeStopWords(Set<String> stopWords);
    }

    /**
     * Jcseg Segmenter
     */
    public final static Segmentor JCSEG = new Segmentor() {

        private Set<String> stopWordList = new HashSet<String>();
        @Override
        public void addDicWords(Collection<String> dic) {
            throw new UnsupportedOperationException("");
        }

        @Override
        public void addDisableWords(Collection<String> stopWords) {
            throw new UnsupportedOperationException("");
        }

        public void addStopWords(Collection<String> stopWords){
            if (stopWords == null || stopWords.isEmpty()){
                return;
            }
            stopWordList.addAll(stopWords);
        }

        public void removeStopWords(Set<String> stopWords){
            if (stopWords == null || stopWords.isEmpty()){
                return;
            }
            for (String word: stopWords){
                stopWordList.remove(word);
            }
        }

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
    };

    public final static Segmentor IKSEG = new Segmentor() {

        private Set<String> stopWordList = new HashSet<String>();
        public List<String> segment(String source) {

            if (StringUtils.isEmpty(source))
                return Collections.emptyList();

            long startTime = System.currentTimeMillis();

            List words = new ArrayList();
            IKSegmenter ik = new IKSegmenter(new StringReader(source), true);// 当为true时，分词器进行最大词长切分
            try {
                Lexeme lexeme;
                while ((lexeme = ik.next()) != null) {
                    String word = lexeme.getLexemeText();
                    if (stopWordList.contains(word)){
                        continue;
                    }
                    words.add(word);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();  //结束时间
            System.out.println("IK分词耗时" + new Float((endTime - startTime)) / 1000 + "秒!");
            System.out.println("原始文本：" + source);
            System.out.println("分词结果：" + words);

            return words;
        }

        public void addDicWords (Collection<String> dic){
            if (dic == null || dic.isEmpty()){
                return;
            }
            Dictionary dictionary = Dictionary.getSingleton();
            dictionary.addWords(dic);
        }

        public void addDisableWords(Collection<String> stopWords){
            if (stopWords == null || stopWords.isEmpty()){
                return;
            }
            Dictionary dictionary = Dictionary.getSingleton();
            dictionary.disableWords(stopWords);
        }

        public void addStopWords(Collection<String> stopWords){
            if (stopWords == null || stopWords.isEmpty()){
                return;
            }
            stopWordList.addAll(stopWords);
        }

        public void removeStopWords(Set<String> stopWords){
            if (stopWords == null || stopWords.isEmpty()){
                return;
            }
            for (String word: stopWords){
                stopWordList.remove(word);
            }
        }
    };

    public static void main(String[] args) {
        String str = "好评，物美价廉, 态度一如既往的好";

        List<String> dic = new ArrayList<String>();
        dic.add("态度一如既往的好");

        List<String> stop = new ArrayList<String>();
        stop.add("好评");

        SegmenterUtils.IKSEG.addDicWords(dic);
        SegmenterUtils.IKSEG.addStopWords(stop);
//        SegmenterUtils.IKSEG.addDisableWords(stop);

        SegmenterUtils.IKSEG.segment(str);

    }
}
