package com.m2l.meta.controller;

import com.m2l.meta.dto.CharacterInfoDto;
import com.m2l.meta.dto.CostumeDto;
import com.m2l.meta.enum_class.ContactType;
import com.m2l.meta.enum_class.CostumeType;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.CharacterService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/character")
@Tag(name = "character-api")
public class CharacterController extends BaseController {

    @Autowired
    CharacterService characterService;

    @Operation(summary = "Get Current Character Info")
    @GetMapping("/info")
    ResponseEntity<?> getCurrentUserCharacterInfo() {
        try {
            CharacterInfoDto infoDto = characterService.getCurrentUserCharacterInfo();
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Update Character About")
    @PutMapping("")
    ResponseEntity<?> updateCharacterAbout(@RequestBody String aboutMe) {
        try {
            CharacterInfoDto infoDto = characterService.updateCharacterAbout(aboutMe);
            return success(CommonConstants.MessageSuccess.SC003, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Other Character Info")
    @GetMapping("/{characterId}")
    ResponseEntity<?> getCharacterInfo(@PathVariable Long characterId) {
        try {
            CharacterInfoDto infoDto = characterService.getCharacterInfo(characterId);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Add Friend")
    @PostMapping("/friend/{characterId}")
    ResponseEntity<?> addFriendList(@PathVariable(name = "characterId") Long characterId) {
        try {
            CharacterInfoDto infoDto = characterService.addContact(characterId, ContactType.FRIEND);
            return success(CommonConstants.MessageSuccess.SC003, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Friend List")
    @GetMapping("/friend")
    ResponseEntity<?> getFriendList() {
        try {
            CharacterInfoDto infoDto = characterService.getContactList(ContactType.FRIEND);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Block List")
    @GetMapping("/block")
    ResponseEntity<?> getBlockList() {
        try {
            CharacterInfoDto infoDto = characterService.getContactList(ContactType.BLOCK);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Unfriended")
    @DeleteMapping("/friend/{characterId}")
    ResponseEntity<?> unfriended(@PathVariable(name = "characterId") Long characterId) {
        try {
            characterService.removeContact(characterId, ContactType.FRIEND);
            return success(CommonConstants.MessageSuccess.SC004, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Block Character")
    @PostMapping("/block/{characterId}")
    ResponseEntity<?> blockContact(@PathVariable(name = "characterId") Long characterId) {
        try {
            CharacterInfoDto infoDto = characterService.addContact(characterId, ContactType.BLOCK);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Equip Costume")
    @PostMapping("/equip")
    ResponseEntity<?> equipCostume(@RequestBody CostumeDto dto) {
        try {
            CharacterInfoDto infoDto = characterService.equipCostume(dto.getCostumeId(), CostumeType.fromValue(dto.getCostumeType()));
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Create Character")
    @PostMapping("/create")
    ResponseEntity<?> createCharacter(@RequestBody CharacterInfoDto dto) {
        try {
            CharacterInfoDto infoDto = characterService.createCharacter(dto);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Delete Character")
    @DeleteMapping("/delete")
    ResponseEntity<?> deleteCharacter() {
        try {
            characterService.deleteCurrentUserCharacter();
            return success(CommonConstants.MessageSuccess.SC004, null, null);
        }catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
