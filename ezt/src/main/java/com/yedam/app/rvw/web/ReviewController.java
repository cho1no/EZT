package com.yedam.app.rvw.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.adm.service.AdminService;
import com.yedam.app.common.service.CommonCodeService;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.service.FileService;
import com.yedam.app.req.service.Criteria;
import com.yedam.app.req.service.PageDTO;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rvw.service.ReviewService;
import com.yedam.app.rvw.service.ReviewVO;
import com.yedam.app.sgi.service.LoginUserVO;
import com.yedam.app.usr.service.UserVO;
//리뷰 CRUD 및 댓글 CRUD
@RequestMapping("review")
@Controller
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	@Autowired
	CommonCodeService commonCodeService;
	@Autowired
	RequestService requestService;
	@Autowired
	FileService fileService;
	@Autowired
	AdminService admService;
	
	//리뷰 전체조회
	@GetMapping("/list")
	public String reviewList(Criteria cri, Model model) {
		List<ReviewVO> list = reviewService.reviewList(cri);
		model.addAttribute("rvList", list);
		
		//페이징
		int total = reviewService.getTotal(cri);
		model.addAttribute("page", new PageDTO(cri, total));
		
		return "rvw/reviewList";
	}
	
	//리뷰 상세조회
	@GetMapping("/info")
	public String reviewInfo(ReviewVO reviewVO,
							 Model model) {
		//리뷰 조회
		ReviewVO findVO = reviewService.reviewInfo(reviewVO);
		if(findVO.getFileVO() != null && findVO.getFileVO().isEmpty()) {
			findVO.setFileVO(null);
		}
		model.addAttribute("review",findVO);
		
		//공통코드
		model.addAttribute("categoryCode", commonCodeService.selectCommonCodeAll("0C"));
		model.addAttribute("cttPlace", commonCodeService.selectCommonCodeAll("0P"));
		model.addAttribute("regionCode", commonCodeService.selectCommonCodeAll("0B"));
		
		return "rvw/reviewInfo";
	}
	
	
	//후기 등록
	@GetMapping("/insert")
	public String reviewInsert( RequestVO requestVO, Model model,@AuthenticationPrincipal LoginUserVO user) {
		//의뢰 단건조회
		RequestVO findVO = requestService.requestInfo(requestVO);
		model.addAttribute("request",findVO);
		UserVO uvo = admService.getUser(findVO.getWorkerNo());
		model.addAttribute("workerInfo", uvo);
		//후기 등록
		model.addAttribute("review", new ReviewVO());
		
		//유저 정보보내기
		model.addAttribute("writer", user.getUserNo());
		return "rvw/reviewInsert";
	}
	
	@PostMapping("/insert")
	@ResponseBody
	public int reviewInsert(MultipartFile[] uploadFile, ReviewVO reviewVO) {
		if (uploadFile != null && uploadFile.length > 0) {
			List<FileVO> list = fileService.uploadFiles(uploadFile);
			int i = 0;
			for(FileVO e : list) {
				if( i == 0) {
					e.setBossTf("Y");
				}else {
					e.setBossTf("N");
				}
				i+= 1;
			}
			if (!list.isEmpty()) {
				reviewVO.setFileVO(list);
			}
		}
		reviewService.insertReview(reviewVO);
		return 1;
	}
	
	// 첨부 파일 업로드
	@PostMapping("/fileInsert")
	@ResponseBody
	public List<FileVO> rptFileInsert(MultipartFile[] uploadFile) {

		List<FileVO> list = fileService.uploadFiles(uploadFile);

		return list;
	}
	

	//후기 수정
	@PostMapping("/update")
	@ResponseBody
	public boolean reviewUpdate(@RequestBody ReviewVO reviewVO) {
		return reviewService.updateReview(reviewVO);
	}
	
	//후기 삭제
	@GetMapping("/delete")
	@ResponseBody
	public int reviewDelete(Integer reviewNo) {
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setReviewNo(reviewNo);
		ReviewVO findVO = reviewService.reviewInfo(reviewVO);
		
		reviewService.deleteReview(reviewNo);
		
		if(findVO.getFileId() != 0) {
			requestService.deleteFile(findVO.getFileId());
		}
		return 1;
	}
	
	// 첨부 파일 삭제
	@PostMapping("/fileDelete")
	@ResponseBody
	public ResponseEntity<String> fileDelete(@RequestBody List<FileVO> fileVO) throws UnsupportedEncodingException {
		
		fileService.deleteFile(fileVO);
		List<FileVO> sFileVO = new ArrayList<FileVO>();
		System.out.println("=======" + fileVO);
		for (FileVO file : fileVO) {
			file.setSaveName("s_" + file.getSaveName());
			sFileVO.add(file);
		}
		fileService.deleteFile(sFileVO);
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
}
