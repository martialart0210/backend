package com.m2l.meta.controller;

import com.m2l.meta.dto.GameDto;
import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.dto.RecordPageRequest;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.MiniGameService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@Tag(name = "Mini game api")
public class MiniGameController extends BaseController {

    private MiniGameService miniGameService;

    @Autowired
    public MiniGameController(MiniGameService miniGameService) {
        this.miniGameService = miniGameService;
    }


    @GetMapping("/info")
    @Operation(summary = "Mini game info of current user")
    public ResponseEntity<?> getGameInfo() {
        try {
            List<GameDto> dtoList = miniGameService.getGameInfo();
            return success(CommonConstants.MessageSuccess.SC001, dtoList, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping("/info/{characterId}")
    @Operation(summary = "Mini game info of other user")
    public ResponseEntity<?> getGameInfoOfUser(@PathVariable Long characterId) {
        try {
            List<GameDto> dtoList = miniGameService.getGameInfoOfOther(characterId);
            return success(CommonConstants.MessageSuccess.SC001, dtoList, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/list")
    @Operation(summary = "List record today")
    public ResponseEntity<?> getListRecord(@RequestBody RecordPageRequest pageDto) {
        try {
            PageResDto<GameDto> dtoList = miniGameService.findBestRecordDaily(pageDto);
            return success(CommonConstants.MessageSuccess.SC001, dtoList, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping("/record")
    @Operation(summary = "Save best record")
    public ResponseEntity<?> saveGameRecord(@RequestBody GameDto dto) {
        try {
            dto = miniGameService.saveRecord(dto);
            return success(CommonConstants.MessageSuccess.SC001, dto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), e.getParam());
        }
    }

    @PostMapping("/reward")
    @Operation(summary = "Get Reward")
    public ResponseEntity<?> getReward(@RequestParam Long gameId) {
        try {
            GameDto dto = miniGameService.getReward(gameId);
            return success(CommonConstants.MessageSuccess.SC003, dto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), e.getParam());
        }
    }
}
