package com.m2l.meta.service;

import com.m2l.meta.dto.CharacterInfoDto;
import com.m2l.meta.enum_class.ContactType;
import com.m2l.meta.enum_class.CostumeType;
import com.m2l.meta.exceptions.MamException;

public interface CharacterService {

    CharacterInfoDto getCurrentUserCharacterInfo() throws MamException;

    CharacterInfoDto updateCharacterAbout(String aboutMe) throws MamException;

    CharacterInfoDto getCharacterInfo(Long characterId) throws MamException;

    CharacterInfoDto addContact(Long characterId, ContactType type) throws MamException;
    CharacterInfoDto getContactList(ContactType type) throws MamException;
    void removeContact(Long characterId, ContactType type) throws MamException;

    CharacterInfoDto equipCostume(Long costumeId, CostumeType type) throws MamException;

    CharacterInfoDto createCharacter(CharacterInfoDto infoDto) throws MamException;

    void deleteCurrentUserCharacter() throws MamException;
}
