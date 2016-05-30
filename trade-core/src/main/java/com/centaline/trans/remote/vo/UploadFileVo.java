package com.centaline.trans.remote.vo;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileVo {
	
	//询价编号
	private String code;
	
	//文件业务类型
	private Integer type;
	
	//上传文件
	private MultipartFile file;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
