package com.mar.meta.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAuthDetected {
	private String username;
	private List<String> roles;
}
