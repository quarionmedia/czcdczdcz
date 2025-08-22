package com.dooo.android.utils.ytExtractor;

import java.lang.reflect.Array;
import java.util.List;

public interface VideoInfoCallback {
    void onVideoUrlReceived(List<org.schabi.newpipe.extractor.stream.VideoStream> videoStreams);
    void onError(Exception e);
}
