package com.m2l.meta.service.impl;

import com.m2l.meta.repository.VideoRepository;
import com.m2l.meta.service.VideoService;
import com.m2l.meta.utils.UserAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    private UserAuthUtils authUtils;
    private VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(UserAuthUtils authUtils,
                            VideoRepository videoRepository) {
        this.authUtils = authUtils;
        this.videoRepository = videoRepository;
    }


}
