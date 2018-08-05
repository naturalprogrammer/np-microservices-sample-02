package com.naturalprogrammer.np02.auth.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;
import com.naturalprogrammer.np02.lib001.scan.security.UserTag;
import com.naturalprogrammer.spring.lemon.commons.util.UserUtils;
import com.naturalprogrammer.spring.lemonreactive.domain.AbstractMongoUser;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@TypeAlias("User")
@Document(collection = "usr")
public class User extends AbstractMongoUser<ObjectId> {

    public static final int NAME_MIN = 1;
    public static final int NAME_MAX = 50;

	@JsonView(UserUtils.SignupInput.class)
	@NotBlank(message = "{blank.name}", groups = {UserUtils.SignUpValidation.class, UserUtils.UpdateValidation.class})
    @Size(min=NAME_MIN, max=NAME_MAX, groups = {UserUtils.SignUpValidation.class, UserUtils.UpdateValidation.class})
    private String name;
	
	@Override
	public UserTag toTag() {
		
		UserTag tag = new UserTag();
		tag.setName(name);
		return tag;
	}
}
