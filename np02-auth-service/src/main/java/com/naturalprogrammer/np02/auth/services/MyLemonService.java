package com.naturalprogrammer.np02.auth.services;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.np02.auth.domain.User;
import com.naturalprogrammer.np02.lib001.scan.channels.UserChannel;
import com.naturalprogrammer.np02.lib001.scan.forms.UserMessage;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commons.util.LecUtils;
import com.naturalprogrammer.spring.lemon.commonsreactive.util.LecrUtils;
import com.naturalprogrammer.spring.lemonreactive.LemonReactiveService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@AllArgsConstructor
public class MyLemonService extends LemonReactiveService<User, ObjectId> {

	private static final Log log = LogFactory.getLog(MyLemonService.class);
	public static final String ADMIN_NAME = "Administrator";	

	private UserChannel userChannel;

	@Override
	public User newUser() {
		return new User();
	}

    @Override
    protected User createAdminUser() {
    	
    	User user = super.createAdminUser(); 
    	user.setName(ADMIN_NAME);
    	return user;
    }
    
	@Override
    protected void updateUserFields(User user, User updatedUser, UserDto currentUser) {

        super.updateUserFields(user, updatedUser, currentUser);
        user.setName(updatedUser.getName());
    }

	@Override
	protected ObjectId toId(String id) {
		return new ObjectId(id);
	}

	@PreAuthorize("isAuthenticated()")
	public Mono<Void> updateName(String id, Mono<UserMessage> userForm) {

		return userForm.zipWith(LecrUtils.currentUser())
				.doOnSuccess(tuple ->
				this.publishUserMessage(tuple, id))
				.then();
	}

	private void publishUserMessage(Tuple2<UserMessage, Optional<UserDto>> tuple, String userId) {
		
		UserMessage form = tuple.getT1();
		UserDto user = tuple.getT2().get();
		
		LecUtils.ensureAuthority(
				user.isGoodAdmin() || user.getId().equals(userId),
				"com.naturalprogrammer.spring.notGoodAdminOrSameUser");
		
		form.setId(userId);
		
		log.debug("Publishing " + form);		
		userChannel.userOutputChannel().send(MessageBuilder.withPayload(form).build());
	}
	
	@StreamListener(UserChannel.INPUT)
	public void subscribeUserMessage(UserMessage userMessage) {
		
		log.debug("Received " + userMessage);

		userRepository.findById(new ObjectId(userMessage.getId()))
			.doOnNext(user -> user.setName(userMessage.getName()))
			.flatMap(userRepository::save)
			.subscribe();
	}	

	/**
	 * Streaming from one topic to another example
	 */
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public String handle(String value) {
		log.debug("Received " + value);
		return value.toUpperCase();
	}
}
