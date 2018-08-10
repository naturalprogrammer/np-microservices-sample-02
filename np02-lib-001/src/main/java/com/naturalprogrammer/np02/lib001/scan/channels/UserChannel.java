package com.naturalprogrammer.np02.lib001.scan.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserChannel {

	String INPUT = "user-input-channel";
	String OUTPUT = "user-output-channel";

	@Input(INPUT)
	SubscribableChannel userInputChannel();
	
	@Output(OUTPUT)
	MessageChannel userOutputChannel();
}
