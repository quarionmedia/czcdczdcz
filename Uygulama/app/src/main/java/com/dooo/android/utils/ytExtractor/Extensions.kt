package com.dooo.android.utils.ytExtractor

import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.NewPipe

public fun initNewPipe() {
    NewPipe.init(DownloaderImpl(OkHttpClient()))
}