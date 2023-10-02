package com.m2l.meta.controller;

import com.m2l.meta.dto.ApiResponseDto;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
	@Autowired
	MessageUtils messageUtils;

	/**
	 * 
	 * @param msgCode
	 * @param data
	 * @param params
	 * @return object DTO when process successfully
	 */
	public ResponseEntity<? extends Object> success(String msgCode, Object data, Object[] params) {

		ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(msgCode)
				.message(messageUtils.getMessage(msgCode, params)).data(data)
				.status(CommonConstants.ApiStatus.STATUS_OK).build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}

	/**
	 * 
	 * @param msgCode
	 * @param params
	 * @return object DTO when process failed
	 */
	public ResponseEntity<? extends Object> failed(String msgCode, Object[] params) {

		ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(msgCode)
				.message(messageUtils.getMessage(msgCode, params)).data(null)
				.status(CommonConstants.ApiStatus.STATUS_ERROR).build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<? extends Object> failedWithError(String msgCode, Object data, Object[] params) {

		ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(msgCode)
				.message(messageUtils.getMessage(msgCode, params)).data(data)
				.status(CommonConstants.ApiStatus.STATUS_ERROR).build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.BAD_REQUEST);
	}

}
