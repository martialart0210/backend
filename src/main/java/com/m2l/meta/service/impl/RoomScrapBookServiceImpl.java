package com.m2l.meta.service.impl;

import com.m2l.meta.dto.PageResDto;
import com.m2l.meta.dto.ScrapBookAllDTO;
import com.m2l.meta.dto.ScrapPurchaseDTO;
import com.m2l.meta.dto.VideoDTORequest;
import com.m2l.meta.dto.VideoDTOResponse;
import com.m2l.meta.entity.MyRoomEntity;
import com.m2l.meta.entity.RoomScrapBookEntity;
import com.m2l.meta.entity.UserCharacter;
import com.m2l.meta.entity.Video;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.MyRoomRepository;
import com.m2l.meta.repository.RoomScrapBookRepository;
import com.m2l.meta.repository.UserCharacterRepository;
import com.m2l.meta.repository.VideoRepository;
import com.m2l.meta.service.RoomScrapBookService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.PageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class RoomScrapBookServiceImpl implements RoomScrapBookService {

    private final RoomScrapBookRepository repository;
    private final PageUtils pageUtils;
    private final ModelMapper modelMapper;
    private final VideoRepository videoRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final MyRoomRepository myRoomRepository;

    public RoomScrapBookServiceImpl(RoomScrapBookRepository repository, PageUtils pageUtils, ModelMapper modelMapper, VideoRepository videoRepository, UserCharacterRepository userCharacterRepository, MyRoomRepository myRoomRepository) {
        this.repository = repository;
        this.pageUtils = pageUtils;
        this.modelMapper = modelMapper;
        this.videoRepository = videoRepository;
        this.userCharacterRepository = userCharacterRepository;
        this.myRoomRepository = myRoomRepository;
    }

    @Override
    public PageResDto<ScrapBookAllDTO> findAll(Pageable pageable) throws MamException {
        Page<RoomScrapBookEntity> roomScrapBookEntityList = repository.findAll(pageable);
        if (roomScrapBookEntityList.isEmpty()) {
            throw new MamException("ER055", null);
        }
        return pageUtils.convertPageEntityToDTO(roomScrapBookEntityList, ScrapBookAllDTO.class);
    }

    @Override
    public VideoDTORequest addVideoToScrap(Long scrapId, VideoDTORequest videoDTO) throws MamException {
        RoomScrapBookEntity bookEntity = repository.findById(scrapId).orElseThrow(() ->
                new MamException("ER056", null)
        );
        if (bookEntity.getVideoList().size() < 10) {
            return saveVideoDTO(videoDTO, bookEntity);
        }
        List<RoomScrapBookEntity> roomScrapBookEntityList = repository.findAll();
        RoomScrapBookEntity targetScrapbook = findAvailableScrapBook(roomScrapBookEntityList);
        if (null == targetScrapbook) {
            throw new MamException("ER059", null);
        }
        return saveVideoDTO(videoDTO, targetScrapbook);
    }

    private RoomScrapBookEntity findAvailableScrapBook(List<RoomScrapBookEntity> scrapBooks) {
        return scrapBooks.stream()
                .filter(scrapBook -> scrapBook.getVideoList().size() < 10)
                .findFirst()
                .orElse(null);
    }

    private VideoDTORequest saveVideoDTO(VideoDTORequest videoDTO, RoomScrapBookEntity bookEntity) {
        Video newVideo = modelMapper.map(videoDTO, Video.class);
        newVideo.setScrapBook(bookEntity);
        newVideo.setCreatedDate(LocalDateTime.now());
        bookEntity.getVideoList().add(newVideo);
        videoRepository.save(newVideo);
        repository.save(bookEntity);
        return videoDTO;
    }

    @Override
    public PageResDto<VideoDTOResponse> getVideoFromScrapbook(Long scrapId, Pageable pageable) throws MamException {
        repository.findById(scrapId).orElseThrow(() ->
                new MamException("ER056", null)
        );
        Page<Video> videos = videoRepository.findAllByScrapBook_Id(scrapId,pageable);
        return pageUtils.convertPageEntityToDTO(videos, VideoDTOResponse.class);
    }

    @Override
    public void purchaseScrapBook(Long idUser, ScrapPurchaseDTO scrapPurchaseDTO) throws MamException {
        UserCharacter userCharacter = userCharacterRepository.findByUserId(idUser);
        MyRoomEntity myRoomEntity = myRoomRepository.findByCharacterCharacterId(userCharacter.getCharacterId());
        scrapPurchaseDTO.setRoom(myRoomEntity);
        scrapPurchaseDTO.setUserCharacter(userCharacter);
        List<RoomScrapBookEntity> list = repository.findAllByUserCharacterCharacterId(userCharacter.getCharacterId());
        if (list.size() < 10) {
            if (userCharacter.getGold().subtract(scrapPurchaseDTO.getPrice()).compareTo(BigInteger.ZERO) > 0) {
                RoomScrapBookEntity roomScrapBookEntity
                        = modelMapper.map(scrapPurchaseDTO, RoomScrapBookEntity.class);
                BigInteger currentGold = userCharacter.getGold().subtract(scrapPurchaseDTO.getPrice());
                Integer scrapbookNumber = userCharacter.getScrapbookNumber() + 1;
                userCharacter.setGold(currentGold);
                userCharacter.setScrapbookNumber(scrapbookNumber);
                repository.save(roomScrapBookEntity);
                userCharacterRepository.save(userCharacter);
                return;
            }
            throw new MamException("ER058", null);
        }
        throw new MamException("ER057", null);
    }
}
