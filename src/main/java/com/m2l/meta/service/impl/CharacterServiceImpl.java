package com.m2l.meta.service.impl;

import com.m2l.meta.dto.CharacterInfoDto;
import com.m2l.meta.dto.ContactInfoDto;
import com.m2l.meta.entity.*;
import com.m2l.meta.enum_class.ContactType;
import com.m2l.meta.enum_class.CostumeType;
import com.m2l.meta.enum_class.GenderEnum;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.*;
import com.m2l.meta.service.CharacterService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.UserAuthUtils;
import com.m2l.meta.utils.ValidateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private UserAuthUtils authUtils;

    private CharacterRepository characterRepository;
    private UserRepository userRepository;
    private ContactRepository contactRepository;
    private WardrobeItemRepo wardrobeItemRepo;

    private CostumeRepository costumeRepository;

    private ValidateUtils validateUtils;

    @Autowired
    public CharacterServiceImpl(UserAuthUtils authUtils, CharacterRepository characterRepository, UserRepository userRepository, ContactRepository contactRepository, ValidateUtils utils) {
        this.authUtils = authUtils;
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.validateUtils = utils;
    }

    @Override
    public CharacterInfoDto getCurrentUserCharacterInfo() throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        return getCharacterInfoDto(character, ContactType.ALL);
    }

    @Override
    public CharacterInfoDto updateCharacterAbout(String aboutMe) throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
            character.setAboutMe(aboutMe);
            characterRepository.save(character);
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        return getCurrentUserCharacterInfo();
    }

    @Override
    public CharacterInfoDto getCharacterInfo(Long characterId) throws MamException {
        Optional<UserCharacter> character = null;
        try {
            character = characterRepository.findById(characterId);
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        if (character.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER051, null);
        }
        return getCharacterInfoDto(character.get(), ContactType.ALL);
    }

    @Override
    public CharacterInfoDto addContact(Long characterId, ContactType type) throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        Optional<UserCharacter> optional = characterRepository.findById(characterId);
        if (optional.isEmpty()) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        Contact contact = Contact.builder()
                .contactId(optional.get())
                .type(type)
                .characterId(character)
                .build();
        contactRepository.save(contact);
        character = characterRepository.findById(character.getCharacterId()).orElse(character);
        return getCharacterInfoDto(character, type);
    }

    private CharacterInfoDto getCharacterInfoDto(UserCharacter character, ContactType type) throws MamException {
        if (ObjectUtils.isEmpty(character)) {
            throw new MamException(CommonConstants.MessageError.ER051, null);
        }
        return ObjectUtils.isEmpty(character) ? null : CharacterInfoDto.builder()
                .characterGender(character.getGender().name())
                .characterId(character.getCharacterId())
                .characterName(character.getCharacterName())
                .characterModel(character.getCharacterModel())
                .costumeHair(ObjectUtils.isEmpty(character.getCostumeHair()) ? null : character.getCostumeHair().getCostumeId())
                .costumeTop(ObjectUtils.isEmpty(character.getCostumeTop()) ? null : character.getCostumeTop().getCostumeId())
                .costumeBottom(ObjectUtils.isEmpty(character.getCostumeBottom()) ? null : character.getCostumeBottom().getCostumeId())
                .costumeShoe(ObjectUtils.isEmpty(character.getCostumeShoe()) ? null : character.getCostumeShoe().getCostumeId())
                .costumeAccessory(ObjectUtils.isEmpty(character.getCostumeAccessory()) ? null : character.getCostumeAccessory().getCostumeId())
                .gold(character.getGold().intValue())
                .roomId(character.getRoom().getId())
                .roomLevel(character.getRoom().getLevel())
                .expandCoupon(character.getExpansionCouponNumber() - character.getRoom().getLevel())
                .dojoName(ObjectUtils.isEmpty(character.getDojoMember()) ? null : character.getDojoMember().getDojo().getDojoName())
                .dojoPosition(ObjectUtils.isEmpty(character.getDojoMember()) ? null : character.getDojoMember().getPosition().name())
                .isCompletedQuests(ObjectUtils.isNotEmpty(character.getQuestDetailList()) && character.getQuestDetailList().stream().allMatch(characterQuestDetail -> characterQuestDetail.isCompleted() && characterQuestDetail.getUpdatedAt().equals(LocalDate.now())))
                .isCompletedGames(ObjectUtils.isNotEmpty(character.getGameRecords()) && character.getGameRecords().stream().allMatch(miniGameRecord -> miniGameRecord.getIsClaimed() && miniGameRecord.getUpdatedAt().toLocalDate().equals(LocalDateTime.now().toLocalDate())))
                .aboutMe(character.getAboutMe())
                .listFriend(!type.equals(ContactType.BLOCK) ? CollectionUtils.isEmpty(character.getContactList()) ? null : character.getContactList().stream().filter(contact -> contact.getType().equals(ContactType.FRIEND))
                        .toList().stream().map(
                                contact -> ContactInfoDto.builder()
                                        .characterName(contact.getContactId().getCharacterName())
                                        .characterGender(contact.getContactId().getGender().toString())
                                        .characterId(contact.getContactId().getCharacterId())
                                        .build()
                        ).toList() : null
                )
                .listBlock(!type.equals(ContactType.FRIEND) ? CollectionUtils.isEmpty(character.getContactList()) ? null : character.getContactList().stream().filter(contact -> contact.getType().equals(ContactType.BLOCK))
                        .toList().stream().map(
                                contact -> ContactInfoDto.builder()
                                        .characterName(contact.getContactId().getCharacterName())
                                        .characterGender(contact.getContactId().getGender().toString())
                                        .characterId(contact.getContactId().getCharacterId())
                                        .build()
                        ).toList() : null)
                .build();
    }

    @Override
    public CharacterInfoDto getContactList(ContactType type) throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        return getCharacterInfoDto(character, type);
    }

    @Override
    public void removeContact(Long characterId, ContactType type) throws MamException {
        UserCharacter character = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        contactRepository.deleteAllByCharacterId_CharacterIdAndAndContactId_CharacterId(character.getCharacterId(), characterId);
    }

    @Override
    public CharacterInfoDto equipCostume(Long costumeId, CostumeType type) throws MamException {
        UserCharacter character = null;
        CostumeEntity costume = null;
        try {
            character = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername()).orElseThrow().getCharacter();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER012, null);
        }
        if (costumeId != null) {
            WardrobeItem item = wardrobeItemRepo.findAllByItem_CostumeIdAndRoom_Id(costumeId, character.getRoom().getId());
            if (ObjectUtils.isEmpty(item)) {
                throw new MamException(CommonConstants.MessageError.ER0042, new Object[]{costumeId});
            }
            Optional<CostumeEntity> optionalCostume = costumeRepository.findAllByCostumeIdAndAndType(costumeId, type);
            if (optionalCostume.isPresent()) {
                costume = optionalCostume.get();
            }
        }
        switch (type) {
            case HAIR -> character.setCostumeHair(costume);
            case TOP -> character.setCostumeTop(costume);
            case BOTTOM -> character.setCostumeBottom(costume);
            case SHOE -> character.setCostumeShoe(costume);
            case ACCESSORY -> character.setCostumeAccessory(costume);
        }
        return getCharacterInfoDto(characterRepository.save(character), ContactType.ALL);


    }

    @Override
    public CharacterInfoDto createCharacter(CharacterInfoDto infoDto) throws MamException {
        User user = null;
        try {
            Optional<User> currentUser = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername());
            if (currentUser.isEmpty()) {
                throw new RuntimeException();
            }
            user = currentUser.get();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        if (ObjectUtils.isNotEmpty(user.getCharacter())) {
            throw new MamException(CommonConstants.MessageError.ER043, null);
        }
        if (characterRepository.countUserCharacterByCharacterName(infoDto.getCharacterName()) > 0) {
            throw new MamException(CommonConstants.MessageError.ER048, null);
        }
        if (!validateUtils.checkIfWordsValid(infoDto.getCharacterName())) {
            throw new MamException(CommonConstants.MessageError.ER067, null);
        }
        if (isUnvalidCharacterName(infoDto.getCharacterName())) {
            throw new MamException(CommonConstants.MessageError.ER068, null);
        }
        UserCharacter character = UserCharacter.builder()
                .user(user)
                .characterModel(infoDto.getCharacterModel())
                .characterName(infoDto.getCharacterName())
                .gender(GenderEnum.valueOf(infoDto.getCharacterGender()))
                .gold(BigInteger.valueOf(300L))
                .expansionCouponNumber(0)
                .scrapbookNumber(0)
                .build();
        character = characterRepository.save(character);
//        Create Data For New Character
        MyRoomEntity myRoom = MyRoomEntity.builder()
                .character(character)
                .build();

        character.setRoom(myRoom);
        character = characterRepository.save(character);
        infoDto = this.getCharacterInfoDto(character, ContactType.ALL);
        return infoDto;
    }

    @Override
    public void deleteCurrentUserCharacter() throws MamException {
        User user;
        UserCharacter character;
        try {
            Optional<User> currentUser = userRepository.findUsersByUsername(authUtils.getCurrentUserInfo().getUsername());
            if (currentUser.isEmpty()) {
                throw new RuntimeException();
            }
            user = currentUser.get();
            character = characterRepository.findById(user.getCharacter().getCharacterId()).orElseThrow();
        } catch (Exception e) {
            throw new MamException(CommonConstants.MessageError.ER027, null);
        }
        user.setCharacter(null);
        characterRepository.deleteById(character.getCharacterId());
    }

    private boolean isUnvalidCharacterName(String name) {
        return  (StringUtils.isEmpty(name) || name.contains(" ") ||
                name.getBytes(StandardCharsets.UTF_8).length < 4 ||
                name.getBytes(StandardCharsets.UTF_8).length > 12);
    }
}
