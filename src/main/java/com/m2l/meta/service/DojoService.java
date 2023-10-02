package com.m2l.meta.service;

import com.m2l.meta.controller.AppointDto;
import com.m2l.meta.dto.*;
import com.m2l.meta.exceptions.MamException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DojoService {
    List<DojoDto> getListDojo(String dojoName) throws MamException;

    DojoDto createDojo(DojoDto dto) throws MamException;

    DojoDto dojoInfo(Long dojoId) throws MamException;

    DojoDto getCurrentDojo() throws MamException;

    void subscriptionAction(Long requestId, Boolean isApproved) throws MamException;

    void closeDojo() throws MamException;

    PageResDto<DojoRequestDto> getListRequestPaginated(int page, int size) throws MamException;

    PageResDto<DojoMemberDto> getListMemberPaginated(int page, int size) throws MamException;

    void leaveDojo() throws MamException;

    @Transactional
    void releasedMember(Long memberId) throws MamException;

    void joinDojo(Long dojoId) throws MamException;

    @Transactional
    DojoDto settingDojo(SettingDojoDto settingDojoDto) throws MamException;

    @Transactional
    void appointMember(AppointDto appointDto) throws MamException;

    List<DojoLogDto> getDojoHistory() throws MamException;
}
