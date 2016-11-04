package com.centaline.trans.knowledge.entity;

/**
 * 知识库附件表 Entity
 * @author yumin1
 *
 */
public class KnowledgeRepoAttachment {
    private Long pkid;

    private String knowledgeCode;

    private String fileType;

    private String knowledgeFileCode;

    private String fileName;

    public Long getPkid() {
        return pkid;
    }

    public void setPkid(Long pkid) {
        this.pkid = pkid;
    }

    public String getKnowledgeCode() {
        return knowledgeCode;
    }

    public void setKnowledgeCode(String knowledgeCode) {
        this.knowledgeCode = knowledgeCode == null ? null : knowledgeCode.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getKnowledgeFileCode() {
        return knowledgeFileCode;
    }

    public void setKnowledgeFileCode(String knowledgeFileCode) {
        this.knowledgeFileCode = knowledgeFileCode == null ? null : knowledgeFileCode.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
}