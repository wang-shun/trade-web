package com.centaline.trans.knowledge.vo;

import java.util.List;

import com.centaline.trans.knowledge.entity.KnowledgeRepo;
import com.centaline.trans.knowledge.entity.KnowledgeRepoAttachment;

/**
 * 知识库操作 VO
 * @author yumin1
 *
 */
public class KnowledgeRepoAddVO {

	private KnowledgeRepo knowledgeRepo;
	
	private List<KnowledgeRepoAttachment> knowledgeRepoAttachmentList;

	public KnowledgeRepo getKnowledgeRepo() {
		return knowledgeRepo;
	}

	public void setKnowledgeRepo(KnowledgeRepo knowledgeRepo) {
		this.knowledgeRepo = knowledgeRepo;
	}

	public List<KnowledgeRepoAttachment> getKnowledgeRepoAttachmentList() {
		return knowledgeRepoAttachmentList;
	}

	public void setKnowledgeRepoAttachmentList(
			List<KnowledgeRepoAttachment> knowledgeRepoAttachmentList) {
		this.knowledgeRepoAttachmentList = knowledgeRepoAttachmentList;
	}
	
	
	
}
