package com.example.samuraitravel.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.form.HouseEditForm;
import com.example.samuraitravel.form.HouseRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.service.HouseService;

@Controller
@RequestMapping("/admin/houses")//ルートパスの基準値を設定する
public class AdminHouseController {
	private final HouseRepository houseRepository;//finalで依存先のobjectを宣言すると一度初期化されたあとは変更されない(プロキシと呼ばれる特殊なクラスを自動的に生成している)
	private final HouseService houseService;
	
	public AdminHouseController(HouseRepository houseRepository,HouseService houseService) {
		this.houseRepository = houseRepository;
		this.houseService = houseService;
	}
	
	@GetMapping//ルートパスがそのままマッピング
	public String index(Model model,@PageableDefault(page=0,size=10,sort="id",direction=Direction.ASC) Pageable pageable,@RequestParam(name="keyword",required=false)String keyword) {//Pageable型引数にアノテーションをつけてデフォルト値を指定している。
		//admin/houses/index.htmlに遷移する。その際にModelにhousesというオブジェクトを登録している
		Page<House> housePage;
		
		if(keyword != null && !keyword.isEmpty()) {
			housePage = houseRepository.findByNameLike("%"+keyword+"%", pageable);
		}else {
			housePage = houseRepository.findAll(pageable);
		}
		
		model.addAttribute("housePage",housePage);
		model.addAttribute("keyword",keyword);
		
		return "admin/houses/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name="id") Integer id, Model model) {//@PathVariableを利用することで、URL内の値をメソッドの引数とbindさせられる。
		House house = houseRepository.getReferenceById(id);//idが一致する民宿データを1件だけ取得する。
		
		model.addAttribute("house",house);
		
		return "admin/houses/show";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("houseRegisterForm",new HouseRegisterForm());//登録ページにフォームクラスのインスタンスを渡している。
		return "admin/houses/register";
	}
	
	@PostMapping("/create")//@ModelAttributeをつけることで、register.htmlのフォームから送信されたデータをbindできる。また、@Validatedをつけると、その引数に対してバリデーションが行われる。
	public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/houses/register";
		}
		
		houseService.create(houseRegisterForm);
		
		redirectAttributes.addFlashAttribute("successMessage","民宿を登録しました。");
		
		return "redirect:/admin/houses";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name="id") Integer id, Model model) {
		House house = houseRepository.getReferenceById(id);//show.htmlから送られてきたidを利用して再度テーブルから対応したレコードを得ている
		String imageName = house.getImageName();
		
		HouseEditForm houseEditForm = new HouseEditForm(house.getId(),house.getName(),null, house.getDescription(),house.getPrice(),house.getCapacity(),house.getPostalCode(),house.getAddress(),house.getPhoneNumber());
		
		model.addAttribute("imageName",imageName);
		model.addAttribute("houseEditForm",houseEditForm);
		
		return "admin/houses/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated HouseEditForm houseEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/houses/edit";
		}
		
		houseService.update(houseEditForm);
		redirectAttributes.addFlashAttribute("successMessage","民宿情報を編集しました。");
		
		return "redirect:/admin/houses";
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name="id") Integer id, RedirectAttributes redirectAttributes) {
		houseRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage","民宿を削除しました。");
		
		return "redirect:/admin/houses";
	}
	

}
