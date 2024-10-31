package org.zerock.foods.service;

import java.util.List;

import org.zerock.category.vo.CategoryVO;
import org.zerock.foods.vo.FoodsOptionsVO;
import org.zerock.foods.vo.FoodsImageVO;
import org.zerock.foods.vo.FoodsSearchVO;
import org.zerock.foods.vo.FoodsSizeVO;
import org.zerock.foods.vo.FoodsVO;
import org.zerock.util.page.PageObject;

public interface FoodsService {

	// 상품 리스트
	public List<FoodsVO> list(PageObject pageObject, FoodsSearchVO foodsSearchVO);
	
	// 대분류/중분류 리스트 가져오기
	public List<CategoryVO> listCategory (Integer cate_code1);
	
	// 상품 정보 보기
	public FoodsVO view(Long foods_no);
	// 상품 사이즈 리스트
	public List<FoodsSizeVO> sizeList(Long foods_no);
	// 상품컬러 리스트
	public List<FoodsOptionsVO> optionsList(Long foods_no);
	
	// 상품이미지 리스트
	public List<FoodsImageVO> imageList(Long foods_no);
	
	// 상품 등록
	public Integer write(FoodsVO vo, List<String> imageFileNames, List<String> size_names, List<String> options_names);
	// 상품 수정
	public Integer update(FoodsVO vo, List<String> size_names, List<String> options_names);
	// 상품 삭제
	public Integer delete(FoodsVO vo);
	
	// 상품 이미지 추가
	// 상품 이미지 변경
	// 상품 이미지 삭제
	
	// 상품 사이즈 추가
	// 상품 사이즈 변경
	// 상품 사이즈 삭제
	
	// 상품 색상 추가
	// 상품 색상 변경
	// 상품 색상 삭제
	
	// 상품 현재 가격 + 기간 변경
	// 상품 예정 가격 추가
}
