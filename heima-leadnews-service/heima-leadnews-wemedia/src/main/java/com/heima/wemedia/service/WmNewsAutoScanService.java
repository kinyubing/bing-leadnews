package com.heima.wemedia.service;

import net.sourceforge.tess4j.TesseractException;

import java.io.IOException;

public interface WmNewsAutoScanService {

    /**
     * 自媒体文章审核
     * @param id  自媒体文章id
     */
    public void autoScanWmNews(Integer id) throws TesseractException, IOException;
}