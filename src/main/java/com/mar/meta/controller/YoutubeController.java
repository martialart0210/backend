package com.mar.meta.controller;

import com.mar.meta.dto.YouTubeItemDto;
import com.mar.meta.enum_class.Youtube;
import com.mar.meta.service.YoutubeService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/youtube")
public class YoutubeController {
    private static Logger log = LoggerFactory.getLogger(YoutubeController.class);

    private final YoutubeService youtubeService;

    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/recommend-videos")
    public ResponseEntity<List<YouTubeItemDto>> getRecommendVideos(@RequestParam("channel_id") String channelId) throws IOException {

        log.info("Calling API authorize-youtube");
        List<YouTubeItemDto> list = youtubeService.youTubeSearch(channelId, Youtube.RECOMMEND);


        return ResponseEntity.ok(list);
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<YouTubeItemDto>> getPlaylist() throws IOException {

        log.info("Calling API playlists-youtube");
        List<YouTubeItemDto> list = youtubeService.youTubeSearch(Strings.EMPTY, Youtube.PLAYLIST);
        return ResponseEntity.ok(list);
    }
}
