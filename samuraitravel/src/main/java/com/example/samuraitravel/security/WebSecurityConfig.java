package com.example.samuraitravel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//設定用クラスとして機能するようにする
@EnableWebSecurity//spring securityによるセキュリティ機能を有効にする
@EnableMethodSecurity//メソッドレベルでのセキュリティ機能を有効にする
public class WebSecurityConfig {
	
	@Bean//メソッドの戻り値がDIコンテナに登録されるようになる。
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.authorizeHttpRequests((requests)->requests
					.requestMatchers("/css/**","/images/**","/js/**","/storage/**","/","/signup/**","/houses","/houses/{id}","/stripe/webhook").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					
			)
			.formLogin((form) -> form//ログインフォームに関連する設定を行っている
				.loginPage("/login")
				.loginProcessingUrl("/login")//spring securityの機能でここにフォームがPOST送信されると自動的にログイン処理を行ってくれる
				.defaultSuccessUrl("/?loggedIn")
				.failureUrl("/login?error")
				.permitAll()
			)
			.logout((logout)->logout
				.logoutSuccessUrl("/?loggedOut")
				.permitAll()
			)
			.csrf().ignoringRequestMatchers("/stripe/webhook");
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
