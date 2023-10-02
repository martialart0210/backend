package com.m2l.meta.repository;

import com.m2l.meta.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findAllByCharacterId_CharacterIdAndAndContactId_CharacterId(Long characterId, Long contactId);

    void deleteAllByCharacterId_CharacterIdAndAndContactId_CharacterId(Long characterId, Long contactId);
}
