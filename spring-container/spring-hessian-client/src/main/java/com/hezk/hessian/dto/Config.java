package com.hezk.hessian.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Config implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name, value;
}