package com.naturalprogrammer.np02.profile.domain;

import java.time.Instant;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class MongoDocument {

	@Id
	protected ObjectId id;
	
	@CreatedBy
	protected ObjectId createdBy;
	
	@CreatedDate
	protected Instant createdDate;
	
	@LastModifiedBy
	protected ObjectId lastModifiedBy;
	
	@LastModifiedDate
	protected Instant lastModifiedDate;
	
	@Version
	protected long version;

}
