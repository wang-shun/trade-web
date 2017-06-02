package com.centaline.trans.product.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.product.entity.ProductCategory;

@MyBatisRepository
public interface ProductCategoryMapper {

	/**
	 * 根据产品类别id查询产品类别信息
	 * 
	 * @param id
	 *            产品类别id
	 * @return 产品类别对象
	 */
	public ProductCategory getProductCategoryById(String id);

	/**
	 * 根据产品代码查询产品类别信息
	 * 
	 * @param code
	 *            产品类别代码
	 * @return 产品类别对象
	 */
	public ProductCategory getProductCategoryByCode(String code);

	/**
	 * 查询所有的产品类别列表
	 * 
	 * @return 产品类别信息列表
	 */
	public List<ProductCategory> getAllProductCategoryList();
	
	
	public void insertProCategory(ProductCategory proCategory);
	
	public void updateProCategory(ProductCategory record);
}