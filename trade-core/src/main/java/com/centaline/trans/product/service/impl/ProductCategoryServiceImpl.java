package com.centaline.trans.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centaline.trans.product.entity.ProductCategory;
import com.centaline.trans.product.repository.ProductCategoryMapper;
import com.centaline.trans.product.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryMapper productCategoryMapper;

	@Override
	@Cacheable(value = "ProductCategoryServiceImpl.getProductCategoryById", key = "#id")
	public ProductCategory getProductCategoryById(String id) {
		return productCategoryMapper.getProductCategoryById(id);
	}

	@Override
	@Cacheable(value = "ProductCategoryServiceImpl.getProductCategoryByCode", key = "#code")
	public ProductCategory getProductCategoryByCode(String code) {
		return productCategoryMapper.getProductCategoryByCode(code);
	}

	@Override
	@Cacheable(value = "ProductCategoryServiceImpl.getAllProductCategoryList")
	public List<ProductCategory> getAllProductCategoryList() {
		return productCategoryMapper.getAllProductCategoryList();
	}

}
