package com.centaline.trans.knowledge.service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.knowledge.vo.KnowledgeRepoAddVO;

/**
 * 知识库 Service
 * 
 * @author yumin1
 * 
 */
public interface KnowledgeRepoService {

	/**
	 * 查询详情
	 * 
	 * @return
	 * @author yumin1
	 */
	public KnowledgeRepoAddVO selectDetailByPkid(long knowledgePkid);

	/**
	 * 知识库 发布知识
	 * 
	 * @param knowledgeRepo
	 *            知识库内容
	 * @param attachmentList
	 *            上传文件
	 * @author yumin1
	 * @return boolean是否成功
	 */
	public boolean insert(KnowledgeRepoAddVO knowledgeRepoAddVO,
			SessionUser longinUser);

	/**
	 * 知识库 删除知识
	 * 
	 * @param knowledgeRepo
	 *            知识库内容
	 * @author yumin1
	 * @return boolean是否成功
	 */
	public boolean delete(long knowledgePkid, String knowledgeCode);

	/**
	 * 知识库 修改知识
	 * 
	 * @param knowledgeRepoAddVO
	 *            知识库和附件对象
	 * @author yumin1
	 * @return boolean是否成功
	 */
	public boolean update(KnowledgeRepoAddVO knowledgeRepoAddVO,
			SessionUser longinUser);

	/**
	 * 删除文件
	 * 
	 * @param parseLong
	 * @return
	 */
	public int delFile(long pkid);

	/**
	 * 获取附件信息
	 * 
	 * @param knowledgePkid
	 * @return
	 */
	// public KnowledgeRepoAttachment findKnowledgeDetailById(String
	// knowledgePkid);
	public int likeOrUnlikeKnowledgeRepo(Long pkid, String userid, String like);

	public int knowledeClickCount(Long pkid);

	/**
	 * 更改是否置顶
	 * @param pkid
	 * @param isTopNum
	 * @return
	 */
	public int updateIsTop(String pkid, String isTopNum);

	/**
	 * 更改是否推荐
	 * @param pkid
	 * @param isTopNum
	 * @return
	 */
	public int updateIsRecommand(String pkid, String isRecomNum);

}
