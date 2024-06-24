package com.yedam.app.common.service;

import lombok.Data;

@Data
public class FileVO {
	private Integer fileId;
	private int fileNo;
	private String savePath;
	private String saveName;
	private String originalFileName;
	private String ext;
	private int fileSize;
	private String bossTf;
}
