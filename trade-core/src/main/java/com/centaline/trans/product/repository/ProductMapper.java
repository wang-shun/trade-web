package com.centaline.trans.product.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.product.entity.Product;

@MyBatisRepository
public interface ProductMapper {

	/**
	 * 根据产品id获取产品信息
	 * 
	 * @param id
	 *            产品标识
	 * @return 产品信息
	 */
	public Product getProductById(String id);

	/**
	 * 根据产品代码获取产品信息
	 * 
	 * @param productCode
	 *            产品代码
	 * @return 产品信息
	 */
	public Product getProductByCode(String productCode);

	/**
	 * 获取所有的产品列表信息
	 * 
	 * @return 返回产品信息列表
	 */
	public List<Product> getAllProductList();

	/**
	 * 根据产品类别代码获取对应的产品信息列表
	 * 
	 * @param productCategoryCode
	 *            产品类别代码
	 * @return 返回产品信息列表
	 */
	public List<Product> getProductListByProductCategoryCode(
			String productCategoryCode);

}