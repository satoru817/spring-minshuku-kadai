
package com.example.samuraitravel.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.repository.HouseRepository;

@Controller//このクラスがコントローラーとして機能するようになる
public class HomeController {
	private final HouseRepository houseRepository;
	
	public HomeController(HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	@GetMapping("/")//HttpリクエストのGetメソッドをそのメソッドに対応付けられる(Mapping)
	public String index(Model model) {
		List<House> newHouses = houseRepository.findTop10ByOrderByCreatedAtDesc();
		model.addAttribute("newHouses",newHouses);
		return "index";//呼び出すビューを返している。.htmlは省略する。						
	}

}
