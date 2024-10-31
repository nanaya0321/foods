package org.zerock.foods.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.category.vo.CategoryVO;
import org.zerock.foods.service.FoodsService;
import org.zerock.foods.vo.FoodsOptionsVO;
import org.zerock.foods.vo.FoodsImageVO;
import org.zerock.foods.vo.FoodsSearchVO;
import org.zerock.foods.vo.FoodsSizeVO;
import org.zerock.foods.vo.FoodsVO;
import org.zerock.util.file.FileUtil;
import org.zerock.util.page.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/foods")
public class FoodsController {

	@Autowired
	@Qualifier("foodsServiceImpl")
	private FoodsService service;
	
	// 파일이 저장될 경로
	String path = "/upload/foods";

	
	@GetMapping("/list.do")
	// 검색을 위한 데이터를 별ㄷ로 받아서 처리
	// @ModelAttribute() - 전달받은 데이터를 Model에 담아서 JSP까지 전달합니다.
	// 1. 변수선언(생성) 2. DB에서 받은 데이터 변수에 저장 3. 변수에 담긴것은 model로 넘겨서 JSP사용
	public String list(Model model, @ModelAttribute(name="foodsSearchVO") FoodsSearchVO foodsSearchVO, HttpServletRequest request) {
		PageObject pageObject = PageObject.getInstance(request);		
		List<CategoryVO> listBig = new ArrayList<CategoryVO>(); // 1.
		
		String perPageNum = request.getParameter("perPageNum");
		
		if (perPageNum == null) {
			pageObject.setPerPageNum(8);
		}
		else {
			pageObject.setPerPageNum(Integer.parseInt(perPageNum));			
		}
		List<FoodsVO> list = new ArrayList<FoodsVO>();
		
		listBig = service.listCategory(0); // 2.
		list = service.list(pageObject, foodsSearchVO);
		
		if(foodsSearchVO.getCate_code1() != null && foodsSearchVO.getCate_code1() != 0) {
			List<CategoryVO> listMid = new ArrayList<CategoryVO>();
			listMid = service.listCategory(foodsSearchVO.getCate_code1());
			model.addAttribute("listMid", listMid);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("listBig",listBig); // 3.
		model.addAttribute("pageObject", pageObject);
		// model.addAttribute("goodsSearchVO", goodsSearchVO); // 이걸쓰던가 위에 @ModelAttribute(name="goodsSearchVO") GoodsSearchVO goodsSearchVO 이걸쓰던가 하면됨
		return "foods/list";
	}
	
	// 상품 상세보기
	// @Controller(컨트롤러)에서는 리턴할때 jsp로 이동하거나 다른uri로 이동
	@GetMapping("/view.do")
	// @ModelAttribute()를 선언하면 선언된 객체를 model에 담는다.
	public String view(Long foods_no, PageObject pageObject, @ModelAttribute(name="foodsSearchVO") FoodsSearchVO foodsSearchVO, Model model) {
	
		// 상품의 상세정보 가져오기 (상품정보 + 가격정보)
		model.addAttribute("vo", service.view(foods_no));
		// 사이즈 정보 리스트
		model.addAttribute("sizeList", service.sizeList(foods_no));
		// 색상 정보 리스트
		model.addAttribute("optionsList", service.optionsList(foods_no));
		// 추가 이미지 정보 리스트
		model.addAttribute("imageList", service.imageList(foods_no));
		
		// JSP EL 객체
		//  ${goodsSearchVO.cate_code1} 
		// => goodsSearchVO.getCate_code1(); 둘이 같은의미
		// ${goodsSearchVO.searchQuery} => goodsSearchVO.getSearchQuery();
		
		return "foods/view";
	}
	
	// 상품등록 폼
	@GetMapping("/writeForm.do")
	public String writeForm(Model model) {
		
		List<CategoryVO> listBig = new ArrayList<CategoryVO>();
		List<CategoryVO> listMid = new ArrayList<CategoryVO>();
		
		listBig = service.listCategory(0);
		// 대분류 첫번째에 있는 중분류를 가져온다.
		listMid = service.listCategory(listBig.get(0).getCate_code1());
		
		model.addAttribute("listBig",listBig);
		model.addAttribute("listMid",listMid);
		
		return "foods/write";
	}
	// 중분류 가져오기 
	@GetMapping(value = "/getCategory.do",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<CategoryVO>> getCategory(Integer cate_code1) {
		
		List<CategoryVO> listMid = new ArrayList<CategoryVO>();
		
		listMid = service.listCategory(cate_code1);
		
		return new ResponseEntity<List<CategoryVO>>(listMid, HttpStatus.OK);
	}
	
	// 상품 등록 처리
	@PostMapping("/write.do")
	public String write(
			FoodsVO vo,
			// 대표이미지
			MultipartFile imageMain,
			// 추가이미지
			@RequestParam("imageFiles") ArrayList<MultipartFile> imageFiles,
			// 옵션 - 사이즈, 색상
			@RequestParam("size_names") ArrayList<String> size_names,
			@RequestParam("options_names") ArrayList<String> options_names,
			HttpServletRequest request,
			RedirectAttributes rttr
			) throws Exception {
		
		log.info("=============== write.do ===============");
		log.info(vo);
		log.info("대표이미지: " + imageMain.getOriginalFilename());
		log.info("<<추가이미지>>");
		for (MultipartFile file : imageFiles) {
			log.info(file.getOriginalFilename());			
		}
		log.info("size: " + size_names);
		log.info("options: " + options_names);
		log.info("========================================");
		
		// 추가이미지, size, color를 담을 리스트들을 만든다.
		
		vo.setImage_name(FileUtil.upload(path, imageMain, request));
		
		List<String> imageFileNames = new ArrayList<String>();
		for (MultipartFile file : imageFiles) {
			imageFileNames.add(FileUtil.upload(path, file, request));			
		}
		
		vo.setSale_price(vo.sale_price());
		Integer result = service.write(vo, imageFileNames, size_names, options_names);
		
		rttr.addFlashAttribute("msg", "상품이 등록되었습니당.");
		
		return "redirect:list.do";
	}
	
	// 상품수정 폼
	@GetMapping("/updateForm.do")
	public String updateForm(Long foods_no, @ModelAttribute(name="pageObject")PageObject pageObject, 
			@ModelAttribute(name="foodsSearchVO") FoodsSearchVO foodsSearchVO, Model model) {
		List<CategoryVO> listBig = new ArrayList<CategoryVO>();
		List<CategoryVO> listMid = new ArrayList<CategoryVO>();
		
		listBig = service.listCategory(0);
		// 대분류 첫번째에 있는 중분류를 가져온다.
		listMid = service.listCategory(listBig.get(0).getCate_code1());
		
		// 상품의 상세정보 가져오기 (상품정보 + 가격정보)
		model.addAttribute("vo", service.view(foods_no));
		// 사이즈 정보 리스트
		model.addAttribute("sizeList", service.sizeList(foods_no));
		// 색상 정보 리스트
		model.addAttribute("optionsList", service.optionsList(foods_no));
		// 추가 이미지 정보 리스트
		model.addAttribute("imageList", service.imageList(foods_no));
		
		model.addAttribute("listBig",listBig);
		model.addAttribute("listMid",listMid);
		
		return "foods/update";
	}
	// 상품 수정 처리
	@PostMapping("/update.do")
	public String update(FoodsVO vo, ArrayList<String> size_names, ArrayList<String> options_names
			, FoodsSearchVO foodsSearchVO, PageObject pageObject, RedirectAttributes rttr) {
		// 상품 상세 정보 수정
		// 상품 사이즈 수정 => 기존 정보 삭제 후 추가
		// 상품 컬러 수정 => 기존 정보 삭제 후 추가
		// 할인가격 세팅
		service.update(vo, size_names, options_names);
		vo.setSale_price(vo.sale_price());
		return "redirect:view.do?foods_no=" + vo.getFoods_no();
	}
	// 이미지 수정 처리
	@PostMapping("/updateImage.do")
	public String updateImage() {
		
		return "redirect:update.do?foods_no=";
	}
	// 이미지 삭제 처리
	@PostMapping("/deleteImage.do")
	public String deleteImage() {
		
		return "redirect:update.do?foods_no=";
	}
	
	// 글 삭제 처리
	@PostMapping("/delete.do")
	public String delete(FoodsVO vo, RedirectAttributes rttr) {
		log.info("delete() =====");
		if (service.delete(vo) == 1) {
			// 삭제가 잘 되었을때
			rttr.addFlashAttribute("msg", "일반게시판 " + vo.getFoods_no() + "번글이 삭제되었습니다.");
		}
		else {
			// 삭제가 안되었을때
			rttr.addFlashAttribute("msg", "글삭제 오류" + "비밀번호를 확인하시고 다시 시도해 주세요.");
			return "redirect:view.do?no=" + vo.getFoods_no() + "&inc=0";
		}
		return "redirect:list.do";
	}
}
