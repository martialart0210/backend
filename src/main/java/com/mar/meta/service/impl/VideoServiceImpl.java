package com.mar.meta.service.impl;

import com.mar.meta.repository.VideoCategoryRepository;
import com.mar.meta.repository.VideoRepository;
import com.mar.meta.service.VideoService;
import com.mar.meta.utils.UserAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    private UserAuthUtils authUtils;
    private VideoRepository videoRepository;
    private VideoCategoryRepository videoCategoryRepository;

    @Autowired
    public VideoServiceImpl(UserAuthUtils authUtils,
                            VideoRepository videoRepository,
                            VideoCategoryRepository videoCategoryRepository) {
        this.authUtils = authUtils;
        this.videoRepository = videoRepository;
        this.videoCategoryRepository = videoCategoryRepository;
    }


}
