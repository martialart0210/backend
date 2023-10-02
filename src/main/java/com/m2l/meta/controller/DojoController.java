package com.m2l.meta.controller;

import com.m2l.meta.dto.*;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.DojoService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.wrap_class.DojoDtoList;
import com.m2l.meta.wrap_class.PageDojoMember;
import com.m2l.meta.wrap_class.PageDojoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/dojo")
@Tag(name = "dojo-api")
public class DojoController extends BaseController {
    private DojoService dojoService;

    @Autowired
    public DojoController(DojoService dojoService) {
        this.dojoService = dojoService;
    }


    @Operation(summary = "Get Current User Dojo Info")
    @GetMapping("/info")
    ResponseEntity<?> getCurrentUserDojoInfo() {
        try {
            DojoDto infoDto = dojoService.getCurrentDojo();
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Dojo Info")
    @GetMapping("/{dojoId}")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DojoDto.class)))
    ResponseEntity<?> getDojoInfo(@PathVariable("dojoId") Long dojoId) {
        try {
            DojoDto infoDto = dojoService.dojoInfo(dojoId);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }

    }

    @Operation(summary = "Create Dojo")
    @PostMapping("/create")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DojoDto.class)))
    ResponseEntity<?> createDojo(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody DojoDto dto) {
        try {
            DojoDto infoDto = dojoService.createDojo(dto);
            return success(CommonConstants.MessageSuccess.SC002, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Subscription Dojo")
    @PutMapping("/sub")
    ResponseEntity<?> subscriptionAction(@RequestParam("requestId") Long requestId, @RequestParam("isApproved") Boolean isApproved) {
        try {
            dojoService.subscriptionAction(requestId, isApproved);
            return success(CommonConstants.MessageSuccess.SC002, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Close Dojo")
    @DeleteMapping("/close")
    ResponseEntity<?> closeDojo() {
        try {
            dojoService.closeDojo();
            return success(CommonConstants.MessageSuccess.SC004, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Dojo's request list pageable")
    @GetMapping("/request")
    @ApiResponse(content = @Content(schema = @Schema(implementation = PageDojoRequest.class)))
    ResponseEntity<?> getListRequest(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            PageResDto<DojoRequestDto> infoDto = dojoService.getListRequestPaginated(page, size);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Get Dojo's member list pageable")
    @GetMapping("/member")
    @ApiResponse(content = @Content(schema = @Schema(implementation = PageDojoMember.class)), description = "Return list DojoMemberDto Pagination")
    ResponseEntity<?> getListMember(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            PageResDto<DojoMemberDto> infoDto = dojoService.getListMemberPaginated(page, size);
            return success(CommonConstants.MessageSuccess.SC001, infoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Leave Dojo")
    @DeleteMapping("/leave")
    ResponseEntity<?> leaveDojo() {
        try {
            dojoService.leaveDojo();
            return success(CommonConstants.MessageSuccess.SC003, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Join Dojo")
    @PutMapping("/join")
    ResponseEntity<?> joinDojo(@RequestParam(name = "dojoId") Long dojoId) {
        try {
            dojoService.joinDojo(dojoId);
            return success(CommonConstants.MessageSuccess.SC002, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Setting Dojo")
    @PutMapping("/setting")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DojoDto.class)))
    ResponseEntity<?> joinDojo(@RequestBody SettingDojoDto dto) {
        DojoDto dojoDto;
        try {
            dojoDto = dojoService.settingDojo(dto);
            return success(CommonConstants.MessageSuccess.SC002, dojoDto, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Search Dojo")
    @GetMapping("/search")
    @ApiResponse(content = @Content(schema = @Schema(implementation = DojoDtoList.class)), description = "Return List DojoDto")
    ResponseEntity<?> searchDojo(@RequestParam(name = "dojoName", defaultValue = "") String dojoName) {
        try {
            List<DojoDto> list = dojoService.getListDojo(dojoName);
            return success(CommonConstants.MessageSuccess.SC001, list, null);
        } catch (Exception e) {
            return failed(CommonConstants.MessageError.ER033, null);
        }
    }

    @Operation(summary = "Release Dojo's member")
    @DeleteMapping("/release")
    ResponseEntity<?> releaseDojo(Long memberId) {
        try {
            dojoService.releasedMember(memberId);
            return success(CommonConstants.MessageSuccess.SC003, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @Operation(summary = "Appoint member position")
    @PostMapping("/appoint")
    ResponseEntity<?> appointMember(@RequestBody AppointDto appointDto) {
        try {
            dojoService.appointMember(appointDto);
            return success(CommonConstants.MessageSuccess.SC003, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
