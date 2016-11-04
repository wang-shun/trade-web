package com.centaline.trans.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.centaline.trans.product.entity.Product;
import com.centaline.trans.product.repository.ProductMapper;
import com.centaline.trans.product.service.ProductService;

@Service
public class ProducServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	@Cacheable(value = "ProducServiceImpl.getProductById", key = "#id")
	public Product getProductById(String id) {
		return productMapper.getProductById(id);
	}

	@Override
	@Cacheable(value = "ProducServiceImpl.getProductByCode", key = "#productCode")
	public Product getProductByCode(String productCode) {
		return productMapper.getProductByCode(productCode);
	}

	@Override
	@Cacheable(value = "ProducServiceImpl.getAllProductList")
	public List<Product> getAllProductList() {
		return productMapper.getAllProductList();
	}

	@Override
	@Cacheable(value = "ProducServiceImpl.getProductListByProductCategoryCode", key = "#productCategoryCode")
	public List<Product> getProductListByProductCategoryCode(
			String productCategoryCode) {
		return productMapper
				.getProductListByProductCategoryCode(productCategoryCode);
	}

}
