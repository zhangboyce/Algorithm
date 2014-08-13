package common.utils;


import common.segment.IKSegmentor;
import common.segment.JcsegSegmentor;
import common.segment.Segmentor;

/**
 * Created by boyce on 2014/6/30.
 */
public abstract class SegmenterUtils {

    /**
     * Jcseg Segmenter
     */
    public final static Segmentor JCSEG = new JcsegSegmentor();

    public final static Segmentor IKSEG = new IKSegmentor();
}
