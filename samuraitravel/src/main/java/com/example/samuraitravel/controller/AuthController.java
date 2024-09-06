package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.entity.VerificationToken;
import com.example.samuraitravel.event.SignupEventPublisher;
import com.example.samuraitravel.form.SignupForm;
import com.example.samuraitravel.service.UserService;
import com.example.samuraitravel.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {
	private final UserService userService;
	private final SignupEventPublisher signupEventPublisher;
	private final VerificationTokenService verificationTokenService;
	
	public AuthController(UserService userService, SignupEventPublisher signupEventPublisher ,VerificationTokenService verificationTokenService) {
		this.userService = userService;// 依存性の注入
		this.signupEventPublisher = signupEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}
	
	@GetMapping("/signup")
	public String singup(Model model) {
		model.addAttribute("signupForm",new SignupForm());
		return "auth/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes , HttpServletRequest httpServletRequest) {
		//メールアドレスが登録済であれば、BindingResultオブジェクトにエラー内容を追加する
		//この時点でbindingResult.getObjectName()はsignupFormを返す
		//HttpServletRequestオブジェクトを利用して動的にURLを取得する
		if(userService.isEmailRegistered(signupForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(),"email","すでに登録済みのメールアドレスです。");
			bindingResult.addError(fieldError);
		}
		
		//パスワードとパスワード（確認）が一致しなければBindingResultオブジェクトにエラー内容を追加
		if(!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(),"password","パスワードが一致しません。");//bindingObject.getObjectName()はbindされたルートオブジェクトの名前を返す。この場合は
			bindingResult.addError(fieldError);
		}
		
		if(bindingResult.hasErrors()) {
			return "auth/signup";
		}
		
		User createdUser = userService.create(signupForm);
		String requestUrl = new String(httpServletRequest.getRequestURL());
		signupEventPublisher.publishSignupEvent(createdUser , requestUrl);//イベント発行
		redirectAttributes.addFlashAttribute("successMessage","ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、会員登録を完了してください。");
		
		return "redirect:/";
	}
	
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name="token")String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if(verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "会員登録が完了しました。";
			model.addAttribute("successMessage",successMessage);
		}else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage",errorMessage);
		}
		
		return "auth/verify";
	}
	
}
