package com.yedam.app;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yedam.app.req.mapper.RequestMapper;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.RequestVO;
@SpringBootTest
public class ReqTest {
	@Autowired
	RequestMapper mapper;
	
	
//	@Test
//	public void testPaging() {
//		
//		Criteria cri = new Criteria();
//		
//		cri.setPageNum(3);
//		cri.setAmount(10);
//		
//		List<RequestVO> list = mapper.selectRequestAll(cri);
//		
//		list.forEach(request -> System.out.print(request.getRequestNo()));
//		
//	}
	
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("14");
		cri.setType("R");
		
		List<RequestVO> list = mapper.selectRequestAll(cri);
		
		list.forEach(request -> System.out.print(request));
	}
	
}
