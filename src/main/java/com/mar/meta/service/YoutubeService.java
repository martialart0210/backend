package com.mar.meta.service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.mar.meta.dto.YouTubeItemDto;
import com.mar.meta.enum_class.Youtube;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YoutubeService {
    private static Logger log = LoggerFactory.getLogger(YoutubeService.class);
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String YOUTUBE_API_APPLICATION = "google-youtube-api-search";
    private static final String YOUTUBE_ORDER = "viewCount";

    private static YouTube youtube;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.api.search.fields}")
    private String searchFields;

    @Value("${youtube.api.number_videos}")
    private Long numberVideos;

    static {
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                        // intentionally left empty
                    }
                }).setApplicationName(YOUTUBE_API_APPLICATION).build();
    }


    /**
     * Search youtube
     *
     * @return
     * @throws IOException
     */
    public List<YouTubeItemDto> youTubeSearch(String channelId, Youtube recommend) throws IOException {
        List<YouTubeItemDto> rvalue = new ArrayList<YouTubeItemDto>();

        if (youtube != null) {
            log.info("Start searching youtube");
            YouTube.Search.List search = youtube.search().list(Collections.singletonList("id, snippet"));

            switch (recommend) {
                case RECOMMEND ->  search.setChannelId(channelId);
                case PLAYLIST -> search.setType(Collections.singletonList("playlist"));
            }

            search.setKey(apiKey);
            search.setOrder(YOUTUBE_ORDER);
            search.setMaxResults(numberVideos);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            rvalue = searchResultList.stream().map(value -> YouTubeItemDto.builder().url("https://www.youtube.com/watch?v=" + value.getId().getVideoId()).title(value.getSnippet().getTitle()).description(value.getSnippet().getDescription()).videoId(value.getId().getVideoId()).playListId(value.getId().getPlaylistId()).build()).collect(Collectors.toList());
            log.info("Finish searching youtube");
        }

        return rvalue;
    }


}
