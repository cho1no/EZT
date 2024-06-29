package com.yedam.app.fie.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService{
	// 폴더 저장 경로
	private String getForder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	// 이미지 체크
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<FileVO> uploadFiles(MultipartFile[] uploadFile) {
		// 폴더 경로
				String uploadFolder = "C:\\temp";
				List<FileVO> list = new ArrayList<>();

				String uploadFolderPath = getForder();
				// 폴더 만들기
				File uploadPath = new File(uploadFolder, uploadFolderPath);
				log.info("upload path: " + uploadPath);

				if (uploadPath.exists() == false) {
					uploadPath.mkdirs();
				}

				for (MultipartFile multipartFile : uploadFile) {

					FileVO fileVO = new FileVO();
					// 콘솔 출력
					log.info("Upload File Name : " + multipartFile.getOriginalFilename());
					log.info("Upload File Size : " + multipartFile.getSize());
					log.info("Upload reName : " + multipartFile.getName());
					log.info("Upload ContentType : " + multipartFile.getContentType());
					// filVO에 값 넣기
					String uploadFileName = multipartFile.getOriginalFilename();
					int nameindex = uploadFileName.indexOf('.');
					fileVO.setOriginalFileName(uploadFileName.substring(0, nameindex));

					UUID uuid = UUID.randomUUID();
					uploadFileName = uuid.toString() + "_" + uploadFileName;

					int nameidx = uploadFileName.indexOf("_");
					fileVO.setSaveName(uploadFileName.substring(0, nameidx));

					fileVO.setSavePath(uploadFolderPath);
					fileVO.setFileSize((int) multipartFile.getSize());

					int index = uploadFileName.indexOf(".");
					fileVO.setExt(uploadFileName.substring(index + 1));

					try {
						File saveFile = new File(uploadPath, uploadFileName);

						if (!checkImageType(saveFile)) {
							// 파일 저장
							multipartFile.transferTo(saveFile);
							list.add(fileVO);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

		return list;
	}

}
