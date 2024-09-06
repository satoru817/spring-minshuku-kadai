package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.samuraitravel.entity.User;

@Component
public class SignupEventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;//ApplicationEventPublisherはインターフェース
	
	public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishSignupEvent(User user, String requestUrl) {
		applicationEventPublisher.publishEvent(new SignupEvent(this, user, requestUrl));
		//default void publishEvent(ApplicationEvent event)を使っている。https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationEventPublisher.html
	}

}
