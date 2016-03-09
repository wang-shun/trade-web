package com.centaline.trans.remote.vo;

public class UploadFile {
	//文件id
	private String id;
	
	//文件类型
	private String file_type;
	
	//文件名称
	private String file_name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	
}
