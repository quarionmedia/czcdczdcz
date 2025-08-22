package com.dooo.android.utils.ytExtractor;

import static com.dooo.android.utils.ytExtractor.ExtensionsKt.initNewPipe;

import android.util.Log;

import org.schabi.newpipe.extractor.ServiceList;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.services.youtube.YoutubeService;
import org.schabi.newpipe.extractor.stream.StreamExtractor;

import java.io.IOException;

public class YouTubeExtractor {

    public static void getVideoInfo(final String url, final VideoInfoCallback callback) {
        new Thread(() -> {
            initNewPipe();
            YoutubeService service = ServiceList.YouTube;
            StreamExtractor extractor;

            try {
                extractor = service.getStreamExtractor(url);
                extractor.fetchPage();
                callback.onVideoUrlReceived(extractor.getVideoStreams());
            } catch (ExtractionException | IOException e) {
                Log.e("YouTubeExtractor", "Error fetching video info", e);
                callback.onError(e);
            }
        }).start();
    }

    public static void getLiveVideoInfo(final String url, final LiveVideoInfoCallback callback) {
        new Thread(() -> {
            initNewPipe();
            YoutubeService service = ServiceList.YouTube;
            StreamExtractor extractor;

            try {
                extractor = service.getStreamExtractor(url);
                extractor.fetchPage();
                callback.onVideoUrlReceived(extractor.getHlsUrl());
            } catch (ExtractionException | IOException e) {
                Log.e("YouTubeExtractor", "Error fetching video info", e);
                callback.onError(e);
            }
        }).start();
    }

}

