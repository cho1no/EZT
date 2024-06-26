package com.yedam.app.doc.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProposalController {

	@Autowired
	ProposalService ppsSerivce;

	// 견적서 단건조회
	@GetMapping("ppsInfo")
	public String ppsInfo(RequestVO requestVO, ProposalVO proposalVO, Model model, Principal principal) {
		// 견적서 정보 조회
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO);
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(findVO.getWorker());
		model.addAttribute("userInfo", usrVO);

		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);

		proposalVO.setWorker(Integer.parseInt(principal.getName()));
		List<ProposalVO> list = ppsSerivce.ppsListInfo(proposalVO);
		model.addAttribute("ppsList", list);
		return "doc/proposalInfo";
	}

	// 견적서 등록
	@GetMapping("ppsInsert")
	public String ppsInsert(ProposalVO proposalVO, RequestVO requestVO, Model model, Principal principal) {
		model.addAttribute("proposalVO", new ProposalVO());
		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(Integer.parseInt(principal.getName()));
		model.addAttribute("userInfo", usrVO);

		return "doc/proposalInsert";
	}

	@PostMapping("ppsInsert")
	public String ppsInsertProcess(ProposalVO proposalVO, RequestVO requestVO) {

		int no = ppsSerivce.ppsInsert(proposalVO);
		String url = null;

		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);

		if (no > -1) {
			url = "redirect:ppsInfo?proposalNo=" + no + "&requestNo=" + reqVO.getRequestNo();
		} else {
			url = "redirect:ppsInsert?requestNo=" + reqVO.getRequestNo();
		}

		return url;
	}

	// 견적서 수정
	@GetMapping("ppsUpdate")
	public String ppsUpdate(RequestVO requestVO, ProposalVO proposalVO, Model model) {
		// 견적서 정보 조회
		ProposalVO findVO = ppsSerivce.ppsInfo(proposalVO);
		model.addAttribute("ppsInfo", findVO);

		// 유저 정보 조회
		UserVO usrVO = ppsSerivce.userInfo(findVO.getWorker());
		model.addAttribute("userInfo", usrVO);

		// 견적서 의뢰정보조회
		RequestVO reqVO = ppsSerivce.reqInfo(requestVO);
		model.addAttribute("reqInfo", reqVO);
		return "doc/proposalUpdate";
	}

	// 견적서 수정
	@PostMapping("ppsUpdate")
	public String ppsUpdate(ProposalVO proposalVO) {
		int no = ppsSerivce.ppsUpdate(proposalVO);
		return "redirect:ppsInfo?proposalNo=" + no + "&requestNo=" + proposalVO.getRequestNo();
	}

	// 견적서 삭제
	@GetMapping("ppsDelete")
	public String ppsDelete(Integer proposalNo) {
		ppsSerivce.ppsDelete(proposalNo);
		return "redirect:main";
	}

	// 견적서 단건 전송
	@GetMapping("ppsSend")
	public String ppsSend(ProposalVO proposalVO) {
		ppsSerivce.ppsSend(proposalVO.getProposalNo());
		return "redirect:ppsInfo?proposalNo=" + proposalVO.getProposalNo() + "&requestNo=" + proposalVO.getRequestNo();
	}

	private String getForder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	

	@PostMapping("/uploadAjaxAction")
	@ResponseBody
	public void uploadAjaxPost(MultipartFile[] uploadFile) {
		// 폴더 경로
		String uploadFolder = "C:/temp";
		
		// 폴더 만들기
		File uploadPath = new File(uploadFolder, getForder());
		log.info("upload path: " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		
		
		for(MultipartFile multipartFile : uploadFile) {
			// 콘솔 출력
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			log.info("Upload reName : " + multipartFile.getName());
			log.info("Upload ContentType : " + multipartFile.getContentType());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				
				if(!checkImageType(saveFile)) {
				// 파일 저장
				multipartFile.transferTo(saveFile);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
	}

}
