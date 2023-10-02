package com.m2l.meta.service;

import java.util.List;


/**
 * The interface FirebaseMessagingService.
 *
 * @author <a href="mailto:phuoc@dxplus.io">phuoc</a>
 */
public interface FirebaseMessagingService {

    /**
     *
     * @param tokens
     * @param title
     * @param body
     */
    void sendNotificationToBatch(List<String> tokens, String title, String body);
}
