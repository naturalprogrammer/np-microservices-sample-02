package com.naturalprogrammer.np02.profile.domain;

import org.bson.types.ObjectId;

import com.naturalprogrammer.spring.lemon.commonsmongo.AbstractDocument;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Profile extends AbstractDocument<ObjectId> {

	private ObjectId userId;
	private String name;
	private String website;
	private String about;
}
