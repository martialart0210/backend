package com.mar.meta.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import com.mar.meta.utils.MessageUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class MamException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MessageUtils messageUtils;

	private String msgCode;

	private Object param[];

	/**
	 * Constructor
	 * 
	 * @param String   msgCode
	 * @param Object[] param
	 */
	public MamException(String msgCode, Object[] param) {
		this.msgCode = msgCode;
		this.param = param;
	}

}
