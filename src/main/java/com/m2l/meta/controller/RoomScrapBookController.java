package com.m2l.meta.controller;

import com.m2l.meta.dto.ScrapPurchaseDTO;
import com.m2l.meta.dto.VideoDTORequest;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.RoomScrapBookService;
import com.m2l.meta.utils.CommonConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scrapBook")
public class RoomScrapBookController extends BaseController {


    private final RoomScrapBookService scrapBookService;

    public RoomScrapBookController(RoomScrapBookService scrapBookService) {
        this.scrapBookService = scrapBookService;
    }


    @GetMapping
    public ResponseEntity<?> findAllScrap(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return success(CommonConstants.MessageSuccess.SC002, scrapBookService.findAll(PageRequest.of(page, size)),
                    null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @GetMapping(value = "/getVideoFromScrap/{scrapId}")
    public ResponseEntity<?> getScrap(@PathVariable Long scrapId,
                                      @RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return success(CommonConstants.MessageSuccess.SC002,
                    scrapBookService.getVideoFromScrapbook(scrapId, PageRequest.of(page, size)), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }

    @PostMapping(value = "/addVideo/{scrapId}")
    public ResponseEntity<?> addVideoToScrap(@PathVariable Long scrapId,
                                             @RequestBody VideoDTORequest videoDTO) {
        try {
            return success(CommonConstants.MessageSuccess.SC002, scrapBookService.addVideoToScrap(scrapId, videoDTO), null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }


    @PostMapping(value = "/buyScrap/{idUser}")
    public ResponseEntity<?> purchaseScrap(@PathVariable Long idUser,
                                           @RequestBody ScrapPurchaseDTO scrapPurchaseDTO) {
        try {
            scrapBookService.purchaseScrapBook(idUser, scrapPurchaseDTO);
            return success(CommonConstants.MessageSuccess.SC002, null, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), null);
        }
    }
}
