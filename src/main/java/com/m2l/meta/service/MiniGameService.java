package com.m2l.meta.service;

import com.m2l.meta.dto.GameDto;
import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.dto.RecordPageRequest;
import com.m2l.meta.exceptions.MamException;

import java.util.List;

public interface MiniGameService {
    List<GameDto> getGameInfo() throws MamException;

    PageResDto<GameDto> findBestRecordDaily(RecordPageRequest recordPageRequest) throws MamException;

    List<GameDto> getGameInfoOfOther(Long characterId) throws MamException;

    GameDto saveRecord(GameDto gameDto) throws MamException;

    GameDto getReward(Long gameId) throws MamException;
}
