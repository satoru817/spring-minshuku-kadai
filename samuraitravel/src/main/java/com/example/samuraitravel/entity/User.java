package com.example.samuraitravel.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="furigana")
	private String furigana;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@ManyToOne//多対1のリレーションシップを表す(複数のuserが１つのroleを持ちうる）
	@JoinColumn(name="role_id")//JoinColumnで外部キーを指定する//JoinColumnはreferencedColumnNameがない場合、相手の(この場合Roleの)primary keyを参照する
	private Role role;//多対一関係の相手
	
	@Column(name="enabled")
	private Boolean enabled;
	
	@Column(name="created_at",insertable = false,updatable = false)
	private Timestamp createdAt;
	
	@Column(name = "updated_at",insertable = false, updatable = false)
	private Timestamp updatedAt;
}
