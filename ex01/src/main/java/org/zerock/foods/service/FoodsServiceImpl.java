package org.zerock.foods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.category.vo.CategoryVO;
import org.zerock.foods.mapper.FoodsMapper;
import org.zerock.foods.vo.FoodsOptionsVO;
import org.zerock.foods.vo.FoodsImageVO;
import org.zerock.foods.vo.FoodsPriceVO;
import org.zerock.foods.vo.FoodsSearchVO;
import org.zerock.foods.vo.FoodsSizeVO;
import org.zerock.foods.vo.FoodsVO;
import org.zerock.util.page.PageObject;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Qualifier("foodsServiceImpl")
public class FoodsServiceImpl implements FoodsService {

	@Setter(onMethod_ = @Autowired)
	private FoodsMapper mapper;
	@Override
	public List<FoodsVO> list(PageObject pageObject, FoodsSearchVO foodsSearchVO) {
		// TODO Auto-generated method stub
		pageObject.setTotalRow(mapper.getTotalRow(pageObject, foodsSearchVO));
		return mapper.list(pageObject, foodsSearchVO);
	}
	// 상품 정보 보기
	@Override
	public FoodsVO view(Long foods_no) {
		// TODO Auto-generated method stub
		return mapper.view(foods_no);
	}
	// 상품 사이즈 리스트
	@Override
	public List<FoodsSizeVO> sizeList(Long foods_no) {
		// TODO Auto-generated method stub
		return mapper.sizeList(foods_no);
	}
	// 상품 컬러 리스트
	@Override
	public List<FoodsOptionsVO> optionsList(Long foods_no) {
		// TODO Auto-generated method stub
		return mapper.optionsList(foods_no);
	}
	// 상품 이미지 리스트
	@Override
	public List<FoodsImageVO> imageList(Long foods_no) {
		// TODO Auto-generated method stub
		return mapper.imageList(foods_no);
	}
	
	@Override
	@Transactional // 쿼리중 하나라도 문제가 생기거나 처리되지 않으면 자동 Rollback 되는 기능
	public Integer write(FoodsVO vo, List<String> imageFileNames, List<String> size_names, List<String> options_names) {
		// TODO Auto-generated method stub
		// 1. goods 테이블에 상품등록 (필수) 순서는 이게 제일 먼저 나머지는 상관없음
		log.info("+++++쿼리 실행전 foodsVO.foods_no: " + vo.getFoods_no());
		mapper.write(vo);
		log.info("+++++쿼리 실행후 foodsVO.foods_no: " + vo.getFoods_no());
		// 2. 등록한 goods테이블의 goods_no를 가져온다.
		// <selectkey>로 goods_no를 세팅하면 보내지는 vo객체에 goods_no가 저장된다. 저장된 vo객체에서 goods_no를 꺼내온다.
		Long foods_no = vo.getFoods_no();
		// goods_price 테이블에 가격정보 등록 (필수)
		// vo.setGoods_no(goods_no);
				
		mapper.writePrice(vo);
		// goods_image 테이블에 등록 (선택: imageFileName에 자료가 있으면)
		for (String imageName : imageFileNames) {
			FoodsImageVO imageVO = new FoodsImageVO();
			imageVO.setFoods_no(foods_no);
			imageVO.setImage_name(imageName);
			mapper.writeImage(imageVO);
		}
		// goods_size 테이블에 등록 (선택: size_names에 자료가 있으면)
		for (String sizeName : size_names) {
			FoodsSizeVO sizeVO = new FoodsSizeVO();
			sizeVO.setFoods_no(foods_no);
			sizeVO.setSize_name(sizeName);
			mapper.writeSize(sizeVO);
		}
		// goods_color 테이블에 등록 (선택: color_names에 자료가 있으면)
		List<FoodsOptionsVO> optionsList = null;
		for (String optionsName : options_names) {
			if (optionsList == null) optionsList = new ArrayList<>();	
			FoodsOptionsVO optionsVO = new FoodsOptionsVO();
			optionsVO.setFoods_no(foods_no);
			optionsVO.setOptions_name(optionsName);
			
			optionsList.add(optionsVO);
		}
		if (optionsList != null) mapper.writeOptions(optionsList);
		return 1;
	}

	@Override
	@Transactional
	public Integer update(FoodsVO vo, List<String> size_names, List<String> options_names) {
		// TODO Auto-generated method stub
		Integer result = mapper.update(vo);
		result = mapper.updatePrice(vo);
		// 사이즈 리스트 삭제 및 등록
		Long foods_no = vo.getFoods_no();
		mapper.deleteSize(foods_no);
		for (String sizeName : size_names) {
			FoodsSizeVO sizeVO = new FoodsSizeVO();
			sizeVO.setFoods_no(foods_no);
			sizeVO.setSize_name(sizeName);
			mapper.writeSize(sizeVO);
		}
		// 컬러 리스트 삭제 및 등록
		mapper.deleteOptions(foods_no);
		List<FoodsOptionsVO> optionsList = null;
		for (String optionsName : options_names) {
			if (optionsList == null) optionsList = new ArrayList<>();	
			FoodsOptionsVO optionsVO = new FoodsOptionsVO();
			optionsVO.setFoods_no(foods_no);
			optionsVO.setOptions_name(optionsName);
			
			optionsList.add(optionsVO);
		}
		
		return result;
	}

	// 5. 일반게시판 글삭제
	@Override
	public Integer delete(FoodsVO vo) {
		log.info("delete() 실행 =====");
		return mapper.delete(vo);
	}

	@Override
	public List<CategoryVO> listCategory(Integer cate_code1) {
		// TODO Auto-generated method stub
		return mapper.getCategory(cate_code1);
	}

	





}
