package com.example.samuraitravel.entity;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity//エンティティとして機能するようになる
@Table(name="houses")//対応付けるテーブルを指定する
@Data//ゲッターセッターを自動生成する。lombokが提供する

public class House {
	@Id//主キーに設定
	@GeneratedValue(strategy = GenerationType.IDENTITY)//自動採番になる
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")//対応付けるカラム名を指定する
	private String name;
	
	@Column(name="image_name")
	private String imageName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="capacity")
	private Integer capacity;

	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="created_at",insertable=false,updatable=false)
	private Timestamp createdAt;
	
	@Column(name="updated_at",insertable=false,updatable=false)
	private Timestamp updatedAt;
}
