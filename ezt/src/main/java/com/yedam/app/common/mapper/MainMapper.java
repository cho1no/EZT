package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.wkr.service.PortfolioVO;

public interface MainMapper {
	public List<ReviewVO> select4Review();
	public List<PortfolioVO> select4Port();
}
