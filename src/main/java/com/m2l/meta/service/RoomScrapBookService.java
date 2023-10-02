package com.m2l.meta.service;

import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.dto.ScrapBookAllDTO;
import com.m2l.meta.dto.ScrapPurchaseDTO;
import com.m2l.meta.dto.VideoDTORequest;
import com.m2l.meta.dto.VideoDTOResponse;
import com.m2l.meta.exceptions.MamException;
import org.springframework.data.domain.Pageable;

public interface RoomScrapBookService {

    PageResDto<ScrapBookAllDTO> findAll(Pageable pageable) throws MamException;

    VideoDTORequest addVideoToScrap(Long scrapId, VideoDTORequest videoDTO) throws MamException;


    PageResDto<VideoDTOResponse> getVideoFromScrapbook(Long scrapId, Pageable pageable) throws MamException;


    void purchaseScrapBook(Long idUser,ScrapPurchaseDTO scrapPurchaseDTO) throws MamException;
}
