package com.naturalprogrammer.np02.profile.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileForm {

	@NotBlank
	private ObjectId userId;

	@URL @Size(max=1024)
	private String website;
	
	@Size(max=4096)
	private String about;
}
