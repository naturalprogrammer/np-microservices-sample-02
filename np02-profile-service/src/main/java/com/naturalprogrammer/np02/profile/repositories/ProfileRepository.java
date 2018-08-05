package com.naturalprogrammer.np02.profile.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.naturalprogrammer.np02.profile.domain.Profile;

import reactor.core.publisher.Mono;

public interface ProfileRepository extends ReactiveMongoRepository<Profile, ObjectId> {

	Mono<Profile> findByUserId(ObjectId userId);
}
