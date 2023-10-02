package com.m2l.meta.service.impl;

import com.m2l.meta.controller.AppointDto;
import com.m2l.meta.dto.*;
import com.m2l.meta.entity.*;
import com.m2l.meta.enum_class.*;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.*;
import com.m2l.meta.service.DojoService;
import com.m2l.meta.utils.*;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DojoServiceImpl implements DojoService {

    private final String SEPARATOR = "<>";
    private final String BLANK_SPACE = " ";

    private DojoRepo dojoRepo;
    private DojoRequestRepo dojoRequestRepo;
    private UserAuthUtils authUtils;

    private CharacterRepository characterRepository;
    private UserRepository userRepository;
    private DojoMemberRepo dojoMemberRepo;
    private DojoLogRepo dojoLogRepo;
    private PageUtils pageUtils;

    private MessageUtils messageUtils;

    private ValidateUtils validateUtils;

    @Autowired
    public DojoServiceImpl(DojoRepo dojoRepo, DojoRequestRepo dojoRequestRepo,
                           UserAuthUtils authUtils, CharacterRepository characterRepository,
                           UserRepository userRepository, DojoMemberRepo dojoMemberRepo, PageUtils pageUtils,
                           MessageUtils messageUtils, DojoLogRepo dojoLogRepo, ValidateUtils utils) {
        this.dojoRepo = dojoRepo;
        this.dojoRequestRepo = dojoRequestRepo;
        this.authUtils = authUtils;
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
        this.dojoMemberRepo = dojoMemberRepo;
        this.pageUtils = pageUtils;
        this.messageUtils = messageUtils;
        this.dojoLogRepo = dojoLogRepo;
        this.validateUtils = utils;
    }

    @Override
    public List<DojoDto> getListDojo(String dojoName) throws MamException {
        return dojoRepo.findDojoRandom(dojoName).stream().map(dojo -> convertDojoDto(dojo, true, false, false))
                .collect(Collectors.toList());
    }

    @Override
    public DojoDto createDojo(DojoDto dto) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        if (ObjectUtils.isNotEmpty(character.getDojoMember())) {
            throw new MamException(CommonConstants.MessageError.ER045, null);
        }
        if (!validateUtils.checkIfWordsValid(dto.getDojoName())) {
            throw new MamException(CommonConstants.MessageError.ER065, null);
        }
        if (dto.getDojoName().contains(BLANK_SPACE)) {
            throw new MamException(CommonConstants.MessageError.ER066, null);
        }
        Dojo dojo = createNewDojo(dto);
        //        Set current user as Dojo instructor
        createDojoMember(dojo, character, DojoPosition.INSTRUCTOR);
        saveLog(DojoLog.builder()
                .dojoLogType(DojoLogType.OPEN)
                .parameter(null)
                .dojo(dojo)
                .createdAt(LocalDateTime.now())
                .build());
        dojo = dojoRepo.findById(dojo.getId()).orElse(new Dojo());
        return convertDojoDto(dojo, false, false, false);
    }

    @Transactional
    DojoMember createDojoMember(Dojo dojo, UserCharacter character, DojoPosition dojoPosition) throws MamException {
        DojoMember member = null;
        try {
            member = DojoMember.builder()
                    .dojo(dojo)
                    .character(character)
                    .position(dojoPosition)
                    .createdAt(LocalDateTime.now())
                    .createdBy(authUtils.getCurrentUserInfo().getUsername())
                    .build();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        return dojoMemberRepo.save(member);
    }

    @Transactional
    Dojo createNewDojo(DojoDto dto) throws MamException {
        Dojo dojo = null;
        if (StringUtils.isNotEmpty(dto.getDojoName()) && dojoRepo.existsByDojoName(dto.getDojoName())) {
            throw new MamException(CommonConstants.MessageError.ER063, null);
        }
        try {
            dojo = Dojo.builder()
                    .createdAt(LocalDateTime.now())
                    .createdBy(authUtils.getCurrentUserInfo().getUsername())
                    .dojoName(dto.getDojoName())
                    .limitSub(dto.getMemberLimit())
                    .status(DojoStatus.IN_OPERATION)
                    .introduction(dto.getIntroduction())
                    .dojoColorCode(dto.getDojoColorCode())
                    .symbolId(dto.getSymbolId())
                    .subscriptionType(SubscriptionType.valueOf(dto.getSubscriptionType()))
                    .build();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        return dojoRepo.save(dojo);
    }

    @Override
    public DojoDto dojoInfo(Long dojoId) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        return convertDojoDto(dojoRepo.findById(dojoId).orElse(null), true, true, true);
    }

    @Override
    public DojoDto getCurrentDojo() throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        if (ObjectUtils.isNotEmpty(character.getDojoOutTime()) && Duration.between(character.getDojoOutTime(), LocalDateTime.now()).toMinutes() < 1) {
            throw new MamException(CommonConstants.MessageError.ER064, null);
        }
        DojoMember member = character.getDojoMember();
        Dojo dojo = member.getDojo();
        return (ObjectUtils.isEmpty(dojo) && dojo.getStatus().equals(DojoStatus.IN_OPERATION)) ?
                new DojoDto() : convertDojoDto(dojo, true, false, true);
    }

    @Override
    @Transactional(dontRollbackOn = MamException.class)
    public void subscriptionAction(Long requestId, Boolean isApproved) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (member.getPosition().equals(DojoPosition.TRAINEE)) {
            throw new MamException(CommonConstants.MessageError.ER044, new Object[]{DojoPosition.TRAINEE.name()});
        }
        Optional<DojoRequest> optional = dojoRequestRepo.findById(requestId);
        if (optional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        DojoRequest request = optional.get();
        request.setRequestStatus(isApproved ? RequestStatus.APPROVED : RequestStatus.REJECTED);
        request.setUpdatedAt(LocalDateTime.now());
        try {
            request.setUpdatedBy(authUtils.getCurrentUserInfo().getUsername());
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        request = dojoRequestRepo.save(request);
        if (isApproved) {
            if (request.getDojo().getDojoMemberList().size() >= request.getDojo().getLimitSub()) {
                throw new MamException(CommonConstants.MessageError.ER061, null);
            }
            addNewMember(request);
        }
    }

    private void addNewMember(DojoRequest request) throws MamException {
        try {
            if (ObjectUtils.isEmpty(request.getCharacter().getDojoMember())) {
                try {
                    DojoMember member = DojoMember.builder()
                            .createdAt(LocalDateTime.now())
                            .createdBy(authUtils.getCurrentUserInfo().getUsername())
                            .position(DojoPosition.TRAINEE)
                            .character(request.getCharacter())
                            .dojo(request.getDojo())
                            .build();
                    dojoMemberRepo.save(member);
                    saveLog(DojoLog.builder()
                            .dojoLogType(DojoLogType.JOIN)
                            .parameter(request.getCharacter().getCharacterName() + SEPARATOR)
                            .dojo(request.getDojo())
                            .createdAt(LocalDateTime.now())
                            .build());
                } catch (Exception e) {
                    throw new MamException(CommonConstants.MessageError.ER027, null);
                }
            } else {
                throw new MamException(CommonConstants.MessageError.ER062, null);
            }
        } finally {
            dojoRequestRepo.deleteById(request.getId());
        }
    }

    @Override
    @Transactional
    public void closeDojo() throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (!DojoPosition.INSTRUCTOR.equals(member.getPosition())) {
            throw new MamException(CommonConstants.MessageError.ER044, new Object[]{DojoPosition.INSTRUCTOR.name()});
        }
        Dojo dojo = member.getDojo();
        dojo.setStatus(DojoStatus.CLOSED);
        dojo.setClosedDate(LocalDateTime.now());
        dojo = dojoRepo.save(dojo);
        dojoMemberRepo.deleteAll(dojo.getDojoMemberList());
        dojoRequestRepo.deleteAll(dojo.getDojoRequestList());
    }

    @Override
    public PageResDto<DojoRequestDto> getListRequestPaginated(int page, int size) throws MamException {
        Pageable sizePagination = PageRequest.of(page, size);
        UserCharacter character = getCurrentUserCharacter();
        Dojo dojo = character.getDojoMember().getDojo();
        Page<DojoRequest> dojoRequestPage = dojoRequestRepo.findAllByDojo_Id(dojo.getId(), sizePagination);
        List<DojoRequestDto> requestDtos = dojoRequestPage.getContent().stream().map(dojoRequest -> DojoRequestDto.builder()
                .requestStatus(dojoRequest.getRequestStatus().name())
                .gender(dojoRequest.getCharacter().getGender().name())
                .requestId(dojoRequest.getId())
                .createdAt(dojoRequest.getCreatedAt())
                .characterName(dojoRequest.getCharacter().getCharacterName())
                .characterId(dojoRequest.getCharacter().getCharacterId())
                .build()).toList();
        PageResDto<DojoRequestDto> dtoPageResDto = pageUtils.convertPageEntityToDTO(dojoRequestRepo.findAllByDojo_Id(dojo.getId(), sizePagination), DojoRequestDto.class);
        dtoPageResDto.setContent(requestDtos);
        return dtoPageResDto;
    }

    @Override
    public PageResDto<DojoMemberDto> getListMemberPaginated(int page, int size) throws MamException {
        Pageable sizePagination = PageRequest.of(page, size);
        UserCharacter character = getCurrentUserCharacter();
        Dojo dojo = character.getDojoMember().getDojo();
        return pageUtils.convertPageEntityToDTO(dojoMemberRepo.findAllByDojo_Id(dojo.getId(), sizePagination), DojoMemberDto.class);
    }

    @Override
    @Transactional
    public void leaveDojo() throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (member.getPosition().equals(DojoPosition.INSTRUCTOR)) {
            throw new MamException(CommonConstants.MessageError.ER046, null);
        }
        UserCharacter memberCharacter = member.getCharacter();
        memberCharacter.setDojoOutTime(LocalDateTime.now());
        member.setCharacter(characterRepository.save(memberCharacter));
        dojoMemberRepo.delete(member);
        saveLog(DojoLog.builder()
                .dojoLogType(DojoLogType.LEFT)
                .parameter(character.getCharacterName() + SEPARATOR)
                .dojo(member.getDojo())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public void releasedMember(Long memberId) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (!member.getPosition().equals(DojoPosition.INSTRUCTOR)) {
            throw new MamException(CommonConstants.MessageError.ER046, null);
        }
        DojoMember dojoMember = dojoMemberRepo.findById(memberId).orElse(null);
        assert dojoMember != null;
        UserCharacter memberCharacter = dojoMember.getCharacter();
        memberCharacter.setDojoOutTime(LocalDateTime.now());
        dojoMember.setCharacter(characterRepository.save(memberCharacter));
        dojoMemberRepo.delete(dojoMember);
        saveLog(DojoLog.builder()
                .dojoLogType(DojoLogType.RELEASED)
                .parameter(dojoMember.getCharacter().getCharacterName() + SEPARATOR)
                .dojo(member.getDojo())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    @Transactional
    public void joinDojo(Long dojoId) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        Optional<Dojo> optional = dojoRepo.findById(dojoId);
        if (optional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER025, null);
        }
        DojoRequest request = dojoRequestRepo.findAllByDojo_IdAndCharacter_CharacterId(dojoId, character.getCharacterId());
        if (ObjectUtils.isNotEmpty(request) && request.getRequestStatus().equals(RequestStatus.WAITING)) {
            throw new MamException(CommonConstants.MessageError.ER060, null);
        }
        if (optional.get().getSubscriptionType().equals(SubscriptionType.FREE)) {
            if (optional.get().getDojoMemberList().size() >= optional.get().getLimitSub()) {
                throw new MamException(CommonConstants.MessageError.ER061, null);
            }
            DojoMember dojoMember = DojoMember.builder()
                    .dojo(optional.get())
                    .character(character)
                    .position(DojoPosition.TRAINEE)
                    .createdBy("SYSTEM")
                    .createdAt(LocalDateTime.now())
                    .build();
            dojoMemberRepo.save(dojoMember);
            saveLog(DojoLog.builder()
                    .dojoLogType(DojoLogType.JOIN)
                    .parameter(character.getCharacterName() + SEPARATOR)
                    .dojo(optional.get())
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        if (optional.get().getSubscriptionType().equals(SubscriptionType.APPROVED)) {
            Long id = null;
            if ( ObjectUtils.isNotEmpty(request) && ObjectUtils.isNotEmpty(request.getId())) {
                id = request.getId();
            }
            request = DojoRequest.builder()
                    .id(ObjectUtils.isEmpty(id) ? null : id)
                    .dojo(optional.get())
                    .character(character)
                    .requestStatus(RequestStatus.WAITING)
                    .createdBy(character.getUser().getUsername())
                    .createdAt(LocalDateTime.now())
                    .build();
            dojoRequestRepo.save(request);
        }
    }

    @Transactional
    @Override
    public DojoDto settingDojo(SettingDojoDto settingDojoDto) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (DojoPosition.TRAINEE.equals(member.getPosition())) {
            throw new MamException(CommonConstants.MessageError.ER044, new Object[]{DojoPosition.TRAINEE.name()});
        }
        Dojo dojo = member.getDojo();
        dojo.setSubscriptionType(SubscriptionType.valueOf(settingDojoDto.subType()));
        dojo.setDojoColorCode(settingDojoDto.dojoColorCode());
        dojo.setIntroduction(settingDojoDto.introduction());
        dojo.setSymbolId(settingDojoDto.symbolId());
        dojo.setDojoNotice(settingDojoDto.dojoNotice());
        dojo.setLimitSub(settingDojoDto.limitSub());
        dojo = dojoRepo.save(dojo);
        return convertDojoDto(dojo, true, false, false);
    }

    @Transactional
    @Override
    public void appointMember(AppointDto appointDto) throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        DojoMember member = character.getDojoMember();
        if (!DojoPosition.INSTRUCTOR.equals(member.getPosition())) {
            throw new MamException(CommonConstants.MessageError.ER044, new Object[]{DojoPosition.INSTRUCTOR.name()});
        }
        Optional<DojoMember> memberOptional = dojoMemberRepo.findById(appointDto.memberId());
        if (memberOptional.isPresent()) {
            DojoMember dojoMember = memberOptional.get();
            dojoMember.setPosition(DojoPosition.valueOf(appointDto.position()));
            dojoMember = dojoMemberRepo.save(dojoMember);
            saveLog(DojoLog.builder().dojoLogType(DojoLogType.APPOINTED)
                    .dojo(dojoMember.getDojo())
                    .createdAt(LocalDateTime.now())
                    .parameter(dojoMember.getCharacter().getCharacterName() + SEPARATOR + dojoMember.getPosition().getNameInKor())
                    .build()
            );
        }
    }

    UserCharacter getCurrentUserCharacter() throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        return character;
    }

    DojoDto convertDojoDto(Dojo dojo, boolean getMember, boolean getRequest, boolean getLogs) {
        return ObjectUtils.isNotEmpty(dojo) ? DojoDto.builder()
                .dojoId(dojo.getId())
                .dojoName(dojo.getDojoName())
                .subscriptionType(dojo.getSubscriptionType().name())
                .memberLimit(dojo.getLimitSub())
                .dojoColorCode(dojo.getDojoColorCode())
                .introduction(dojo.getIntroduction())
                .symbolId(dojo.getSymbolId())
                .dojoNotice(dojo.getDojoNotice())
                .currentMember(getMember ? dojo.getDojoMemberList().stream().map(dojoMember -> DojoMemberDto.builder()
                        .memberId(dojoMember.getId())
                        .characterGender(dojoMember.getCharacter().getGender().name())
                        .characterName(dojoMember.getCharacter().getCharacterName())
                        .memberPosition(dojoMember.getPosition().name())
                        .characterId(dojoMember.getCharacter().getCharacterId())
                        .build()).collect(Collectors.toList()) : new ArrayList<>())
                .requestDtos(getRequest ? dojo.getDojoRequestList().stream().map(dojoRequest -> DojoRequestDto.builder()
                        .requestId(dojoRequest.getId())
                        .createdAt(dojoRequest.getCreatedAt())
                        .characterId(dojoRequest.getCharacter().getCharacterId())
                        .characterName(dojoRequest.getCharacter().getCharacterName())
                        .build()
                ).collect(Collectors.toList()) : new ArrayList<>())
                .dojoLogDtos(getLogs ? dojo.getDojoLogs().stream().map(this::convertDojoLogDto).toList() : new ArrayList<>())
                .build() : null;
    }

    void saveLog(DojoLog dojoLog) {
        dojoLogRepo.save(dojoLog);
    }

    @Override
    public List<DojoLogDto> getDojoHistory() throws MamException {
        UserCharacter character = getCurrentUserCharacter();
        Optional<Dojo> optional = Optional.ofNullable(character.getDojoMember().getDojo());
        if (optional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER025, null);
        }
        List<DojoLog> dojoLogs = optional.get().getDojoLogs();
        return dojoLogs.stream().map(this::convertDojoLogDto).toList();
    }

    DojoLogDto convertDojoLogDto(DojoLog log) {
        String logType = "";
        switch (log.getDojoLogType()) {
            case JOIN -> logType = CommonConstants.LogMessage.JOINED;
            case LEFT -> logType = CommonConstants.LogMessage.LEFT;
            case OPEN -> logType = CommonConstants.LogMessage.OPEN;
            case RELEASED -> logType = CommonConstants.LogMessage.RELEASED;
            case APPOINTED -> logType = CommonConstants.LogMessage.APPOINTED;
        }
        return DojoLogDto.builder()
                .createdAt(log.getCreatedAt())
                .loggingMessage(log.getParameter() != null ? messageUtils.getMessage(logType, log.getParameter().split("<>")) : null)
                .build();
    }
}
