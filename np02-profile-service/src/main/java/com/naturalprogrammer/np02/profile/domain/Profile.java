package com.naturalprogrammer.np02.profile.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter @Setter
public class Profile extends MongoDocument {

	private ObjectId userId;
	private String name;
	private String website;
	private String about;
}
