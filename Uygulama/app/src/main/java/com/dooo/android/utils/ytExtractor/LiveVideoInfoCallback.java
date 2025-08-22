package com.dooo.android.utils.ytExtractor;

import org.schabi.newpipe.extractor.stream.VideoStream;

import java.util.List;

public interface LiveVideoInfoCallback {
    void onVideoUrlReceived(String videoStream);
    void onError(Exception e);
}
