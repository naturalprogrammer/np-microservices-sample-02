package com.naturalprogrammer.np02.lib001.scan.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserMessage {
	
	private String id;
	
	@NotBlank
	@Size(max=100)
	private String name;
}
