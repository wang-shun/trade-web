package com.centaline.trans.attachment.entity;

import java.util.List;

public class ToAttachment {
    private Long pkid;
    /*交易单编号*/
    private String caseCode;
    /*附件存储地址 */
    private String preFileAdress;
    /*附件编码*/
    private String preFileCode;
    /*附件编码*/
    private String preFileName;
    /*环节编码 */
    private String partCode;
    /*文件类型  */
    private String fileCat;
    /*文件名称*/
    private String fileName;
    
    private List<Pic> pic;
    /*产调编号*/
    private String prCode;
   
    
    
	public String getPrCode() {
		return prCode;
	}

	public void setPrCode(String prCode) {
		this.prCode = prCode;
	}

	public String getPreFileCode() {
		return preFileCode;
	}

	public void setPreFileCode(String preFileCode) {
		this.preFileCode = preFileCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode == null ? null : caseCode.trim();
    }

    public String getPreFileAdress() {
        return preFileAdress;
    }

    public void setPreFileAdress(String preFileAdress) {
        this.preFileAdress = preFileAdress == null ? null : preFileAdress.trim();
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode == null ? null : partCode.trim();
    }

    public String getFileCat() {
        return fileCat;
    }

    public void setFileCat(String fileCat) {
        this.fileCat = fileCat == null ? null : fileCat.trim();
    }

	public List<Pic> getPic() {
		return pic;
	}

	public void setPic(List<Pic> pic) {
		this.pic = pic;
	}

	public String getPreFileName() {
		return preFileName;
	}

	public void setPreFileName(String preFileName) {
		this.preFileName = preFileName;
	}

}