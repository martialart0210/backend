package com.m2l.meta.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.m2l.meta.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * The class FirebaseMessagingServiceImpl.
 *
 * @author <a href="mailto:phuoc@dxplus.io">an</a>
 */
@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {


    @Value("${firebase.serverKey}")
    private String serverKey;

//    private final TopicRepository topicRepository;
//
//    @Autowired
//    public FirebaseMessagingServiceImpl(TopicRepository topicRepository) {
//        this.topicRepository = topicRepository;
//    }

    @Override
    public void sendNotificationToBatch(List<String> tokens, String title, String body) {
//        Map<String, List<String>> batches = splitTokensIntoBatches(tokens);

        sendNotificationsInBatches(null, title, body);
    }

//    private Map<String, List<String>> splitTokensIntoBatches(List<String> tokens) {
//        Map<String, List<String>> batches = new HashMap<>();
//
//        for (String token : tokens) {
//            List<Topic> topicsToken = topicRepository.findAllByUserDeviceToken(token);
//            List<String> topics = topicsToken.stream()
//                    .map(Topic::getTopicName).toList();
//            for (String topic : topics) {
//                batches.computeIfAbsent(topic, k -> new ArrayList<>()).add(token);
//            }
//        }
//
//        return batches;
//    }

    private void sendNotificationsInBatches(Map<String, List<String>> batches, String title, String body) {
        System.out.println("start sendNotificationsInBatches...");
        for (String topic : batches.keySet()) {
            List<String> batchTokens = batches.get(topic);
            if (!batchTokens.isEmpty()) {
                sendNotificationToTopic(batchTokens, title, body, topic);
            }
        }
    }

    private void sendNotificationToTopic(List<String> tokens, String title, String body, String topic) {
        System.out.println("sendNotificationToTopic...");
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + serverKey);
            conn.setRequestProperty("Content-Type", "application/json");

            JsonObject payload = new JsonObject();
            payload.addProperty("title", title);
            payload.addProperty("body", body);

            JsonObject request = new JsonObject();
            request.add("notification", payload);
            request.add("registration_ids", new Gson().toJsonTree(tokens));
            request.addProperty("condition", "'" + topic + "' in topics");

            conn.setDoOutput(true);
            try (OutputStream outputStream = conn.getOutputStream()) {
                outputStream.write(request.toString().getBytes());
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Successs...");
            } else {
                System.out.println("Error...");
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
