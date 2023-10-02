package com.m2l.meta.controller;

import com.m2l.meta.dto.YouTubeItemDto;
import com.m2l.meta.enum_class.Youtube;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.YoutubeService;
import com.m2l.meta.utils.CommonConstants;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/youtube")
public class YoutubeController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(YoutubeController.class);

    private final YoutubeService youtubeService;

    public YoutubeController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @GetMapping("/recommend-videos")
    public ResponseEntity<?> getRecommendVideos(@RequestParam("channel_id") String channelId) {

        log.info("Calling API authorize-youtube");
        try {
            List<YouTubeItemDto> list = youtubeService.youTubeSearch(channelId, Youtube.RECOMMEND);
            return success(CommonConstants.MessageSuccess.SC001, list, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/playlists")
    public ResponseEntity<?> getPlaylist() {

        log.info("Calling API playlists-youtube");
        try {
            List<YouTubeItemDto> list = youtubeService.youTubeSearch(Strings.EMPTY, Youtube.PLAYLIST);
            return success(CommonConstants.MessageSuccess.SC001, list, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
