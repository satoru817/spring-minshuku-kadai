package com.example.samuraitravel.event;

import org.springframework.context.ApplicationEvent;//ApplicationEventクラスはイベントを作成するための基本的なクラス イベントの発生源などを保持する リスナークラスにイベントが発生したことを知らせる。

import com.example.samuraitravel.entity.User;

import lombok.Getter;

@Getter
public class SignupEvent extends ApplicationEvent{
	private User user;
	private String requestUrl;
	
	public SignupEvent(Object source, User user, String requestUrl) {
		super(source);//superクラス(ApplicationEvent)のコンストラクタにsource(publisherのインスタンス)を渡している。
		
		this.user = user;
		this.requestUrl = requestUrl;
	}

}
