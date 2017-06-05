package com.centaline.trans.project.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.product.entity.ProductCategory;
import com.centaline.trans.product.service.ProductCategoryService;

/**
 * 
 * <p>Project: 产品类别管理</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2017</p>
 * @author PC</a>
 */

@Controller
@RequestMapping(value="/projectCategory")
public class ProjectCategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ProductCategoryService productCategoryService;
	
	/**
	 * 查询所有的产品分类
	 */
	@RequestMapping("queryPrdCategorys")
	@ResponseBody
	public List<ProductCategory> queryPrdCategorys() {
		List<ProductCategory> prdList = productCategoryService.getAllProductCategoryList();
		return prdList;
	}
	
	/**
	 * 详情
	 * @param pkid
	 * @param model
	 * @return
	 */
	@RequestMapping("prdCategorysDetail")
	public ProductCategory PrdCategorysDetail(String pkid ,HttpServletRequest request){
		return productCategoryService.getProductCategoryById(pkid);
	}
	
	/**
	 * 添加与修改数据
	 * @param model
	 * @param proCategory
	 * @return
	 */
	@RequestMapping(value = "saveprdCategorys")
	@ResponseBody
	public AjaxResponse<String> saveprdCategorys(Model model,
			@RequestBody ProductCategory proCategory) {
		try {
			if(proCategory.getPkid()==0){
				productCategoryService.insertProCategory(proCategory);
			}else{
				productCategoryService.updateProCategory(proCategory);
			}
			return AjaxResponse.success("操作成功");
		} catch (Exception e) {
			logger.debug("操作失败", e);
			return AjaxResponse.fail("操作数据失败");
		}
	}
	
	/**
	 * 删除数据
	 * @param model
	 * @param proCategory
	 * @return
	 */
	@RequestMapping(value = "deleteprdCategorys")
	@ResponseBody
	public AjaxResponse<String> deleteprdCategorys(Model model,
			@RequestBody ProductCategory proCategory) {
		try {
			proCategory.setStatus(2);
			productCategoryService.updateProCategory(proCategory);
			return AjaxResponse.success("操作成功");
		} catch (Exception e) {
			logger.debug("操作失败", e);
			return AjaxResponse.fail("操作失败");
		}
	}

}
