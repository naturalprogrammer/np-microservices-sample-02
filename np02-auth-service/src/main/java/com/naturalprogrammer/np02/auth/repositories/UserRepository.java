package com.naturalprogrammer.np02.auth.repositories;
import org.bson.types.ObjectId;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.spring.lemonreactive.domain.AbstractMongoUserRepository;

public interface UserRepository extends AbstractMongoUserRepository<User, ObjectId> {

}
