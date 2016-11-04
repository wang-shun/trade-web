package com.centaline.trans.knowledge.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.KnowledgeRepoAttachmentEnum;
import com.centaline.trans.knowledge.entity.KnowledgeLike;
import com.centaline.trans.knowledge.entity.KnowledgeRepo;
import com.centaline.trans.knowledge.entity.KnowledgeRepoAttachment;
import com.centaline.trans.knowledge.repository.KnowledgeRepoAttachmentMapper;
import com.centaline.trans.knowledge.repository.KnowledgeRepoMapper;
import com.centaline.trans.knowledge.service.KnowledgeLikeService;
import com.centaline.trans.knowledge.service.KnowledgeRepoService;
import com.centaline.trans.knowledge.vo.KnowledgeRepoAddVO;

/**
 * 知识库 ServiceImpl
 * 
 * @author yumin1
 * 
 */
@Service
public class KnowledgeRepoServiceImpl implements KnowledgeRepoService {

	@Autowired
	private KnowledgeRepoMapper knowledgeRepoMapper;
	@Autowired
	private KnowledgeRepoAttachmentMapper knowledgeRepoAttachmentMapper;
	@Autowired
	private KnowledgeLikeService knowledgeLikeService;

	/**
	 * 查询详情
	 * 
	 * @return KnowledgeRepoAddVO vo对象
	 * @author yumin1
	 */
	@Override
	public KnowledgeRepoAddVO selectDetailByPkid(long knowledgePkid) {
		KnowledgeRepoAddVO knowledgeRepoAddVO = new KnowledgeRepoAddVO();

		KnowledgeRepo knowledgeRepo = knowledgeRepoMapper
				.selectByPrimaryKey(knowledgePkid);
		if (knowledgeRepo != null) {
			if (StringUtils.isNotEmpty(knowledgeRepo.getKnowledgeCode())
					&& StringUtils.isNotBlank(knowledgeRepo.getKnowledgeCode())) {
				String knowledgeCode = knowledgeRepo.getKnowledgeCode();
				List<KnowledgeRepoAttachment> attachmentList = knowledgeRepoAttachmentMapper
						.selectByKnowledgeCode(knowledgeCode);
				if (CollectionUtils.isNotEmpty(attachmentList)) {
					knowledgeRepoAddVO
							.setKnowledgeRepoAttachmentList(attachmentList);
				}
			}
		}

		knowledgeRepoAddVO.setKnowledgeRepo(knowledgeRepo);

		return knowledgeRepoAddVO;

	}

	/**
	 * 知识库发布/添加
	 * 
	 * @return boolean 是否成功
	 * @author yumin1
	 */
	@Override
	public boolean insert(KnowledgeRepoAddVO knowledgeRepoAddVO,
			SessionUser longinUser) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHmmss");
		String formatDate = sdf.format(new Date());
		boolean bool = false;
		List<KnowledgeRepoAttachment> knowledgeRepoAttachmentList = knowledgeRepoAddVO
				.getKnowledgeRepoAttachmentList();
		KnowledgeRepo knowledgeRepo = knowledgeRepoAddVO.getKnowledgeRepo();
		knowledgeRepo.setKnowledgeCode("ZS" + formatDate);
		if (knowledgeRepo != null) {
			knowledgeRepo.setPublisher(longinUser.getId());
			knowledgeRepo.setPbTime(new Date());
			int pkid = knowledgeRepoMapper.insert(knowledgeRepo);
			if (pkid > 0) {
				if (CollectionUtils.isNotEmpty(knowledgeRepoAttachmentList)) {
					for (KnowledgeRepoAttachment knowledgeRepoAttachment : knowledgeRepoAttachmentList) {
						KnowledgeRepoAttachment kra = new KnowledgeRepoAttachment();
						kra.setFileType(KnowledgeRepoAttachmentEnum.FILE_TYPE
								.getCode());
						kra.setKnowledgeFileCode(knowledgeRepoAttachment
								.getKnowledgeFileCode());
						kra.setKnowledgeCode("ZS" + formatDate);
						kra.setFileName(knowledgeRepoAttachment.getFileName());
						knowledgeRepoAttachmentMapper.insert(kra);
					}
				}
			}
			bool = true;
		}

		return bool;
	}

	/**
	 * 知识库 删除知识
	 * 
	 * @param knowledgeRepo
	 *            知识库内容
	 * @author yumin1
	 * @return boolean是否成功
	 */
	@Override
	public boolean delete(long knowledgePkid, String knowledgeCode) {
		boolean bool = false;

		if (knowledgePkid != 0) {
			int pkid = knowledgeRepoMapper.deleteByPrimaryKey(knowledgePkid);
			if (pkid > 0) {
				if (knowledgeCode != null && !"".equals(knowledgeCode)) {
					knowledgeRepoAttachmentMapper
							.deleteByKnowledgeCode(knowledgeCode);
				}
				bool = true;
			}
		}

		return bool;
	}

	/**
	 * 知识库 修改知识
	 * 
	 * @param knowledgeRepoAddVO
	 *            知识库和附件对象
	 * @return boolean是否成功
	 * @author yumin1
	 */
	@Override
	public boolean update(KnowledgeRepoAddVO knowledgeRepoAddVO,
			SessionUser longinUser) {
		boolean bool = false;
		if (knowledgeRepoAddVO != null) {
			List<KnowledgeRepoAttachment> lists = knowledgeRepoAddVO
					.getKnowledgeRepoAttachmentList();
			for (KnowledgeRepoAttachment knowledgeRepoAttachment : lists) {
				KnowledgeRepoAttachment kra = new KnowledgeRepoAttachment();
				kra.setFileName(knowledgeRepoAttachment.getFileName());
				kra.setFileType(KnowledgeRepoAttachmentEnum.FILE_TYPE.getCode());
				kra.setKnowledgeCode(knowledgeRepoAttachment.getKnowledgeCode());
				kra.setKnowledgeFileCode(knowledgeRepoAttachment
						.getKnowledgeFileCode());
				knowledgeRepoAttachmentMapper.insert(kra);
			}
			KnowledgeRepo knowledgeRepo = knowledgeRepoAddVO.getKnowledgeRepo();
			if (knowledgeRepo != null && knowledgeRepo.getPkid() != null) {
				knowledgeRepo.setPublisher(longinUser.getId());
				knowledgeRepo.setPbTime(new Date());
				knowledgeRepoMapper.updateByPrimaryKeyWithBLOBs(knowledgeRepo);
				bool = true;
			}
		}
		return bool;
	}

	@Override
	public int delFile(long pkid) {
		return knowledgeRepoAttachmentMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public int likeOrUnlikeKnowledgeRepo(Long pkid, String userid, String like) {
		KnowledgeRepo nrepo = knowledgeRepoMapper.selectByPrimaryKey(pkid);
		KnowledgeLike likeBean = knowledgeLikeService.selectByKnowCodeAndUser(
				nrepo.getKnowledgeCode(), userid);
		boolean t = false;
		if (likeBean == null) {
			likeBean = new KnowledgeLike();
			t = true;
		}
		likeBean.setKnowledgeCode(nrepo.getKnowledgeCode());
		likeBean.setIsLike(like);
		likeBean.setLikerId(userid);
		if ("1".equals(like)) {
			nrepo.setLikeSum((nrepo.getLikeSum() == null ? 0 : nrepo
					.getLikeSum()) + 1);
		} else {
			nrepo.setLikeSum((nrepo.getLikeSum() == null ? 1 : nrepo
					.getLikeSum()) - 1);
		}
		nrepo.setLikeSum(nrepo.getLikeSum() < 0 ? 0 : nrepo.getLikeSum());
		knowledgeRepoMapper.updateByPrimaryKeySelective(nrepo);
		if (t) {
			knowledgeLikeService.insertSelective(likeBean);
		} else {
			knowledgeLikeService.updateLike(likeBean);
		}
		return 1;
	}

	@Override
	public int knowledeClickCount(Long pkid) {
		KnowledgeRepo nrepo = knowledgeRepoMapper.selectByPrimaryKey(pkid);
		if (nrepo != null) {
			if (nrepo.getClickSum() == null) {
				nrepo.setClickSum(1);
			} else {
				nrepo.setClickSum(nrepo.getClickSum() + 1);
			}
			knowledgeRepoMapper.updateByPrimaryKeySelective(nrepo);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateIsTop(String pkid, String isTopNum) {
		KnowledgeRepo knowledgeRepo = new KnowledgeRepo();
		knowledgeRepo.setPkid(Long.parseLong(pkid));
		knowledgeRepo.setIsTop(isTopNum);
		return knowledgeRepoMapper.updateIsTop(knowledgeRepo);
	}

	@Override
	public int updateIsRecommand(String pkid, String isRecomNum) {
		KnowledgeRepo knowledgeRepo = new KnowledgeRepo();
		knowledgeRepo.setPkid(Long.parseLong(pkid));
		knowledgeRepo.setIsRecommand(isRecomNum);
		return knowledgeRepoMapper.updateIsRecommand(knowledgeRepo);
	}
}
