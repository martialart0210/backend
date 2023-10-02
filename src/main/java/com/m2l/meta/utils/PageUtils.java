package com.m2l.meta.utils;

import com.m2l.meta.dto.ContentPageDto;
import com.m2l.meta.dto.PageResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    @Autowired
    BeanUtil beanUtils;

    /**
     * convert page to page response DTO
     *
     * @param page
     * @param dtoClass
     * @return PageResponseDTO<DTO>
     */
    public <D, E> PageResDto<D> convertPageEntityToDTO(Page<E> page, Class<D> dtoClass) {
        PageResDto<D> pageResDTO = new PageResDto<>();
        ContentPageDto rPageDTO = new ContentPageDto();
        rPageDTO.setTotalPages(page.getTotalPages());
        rPageDTO.setTotalElements(page.getTotalElements());
        rPageDTO.setSize(page.getSize());
        rPageDTO.setNumber(page.getNumber());

        pageResDTO.setMetaData(rPageDTO);
        pageResDTO.setContent(beanUtils.copyListToList(page.getContent(), dtoClass));

        return pageResDTO;
    }


}
