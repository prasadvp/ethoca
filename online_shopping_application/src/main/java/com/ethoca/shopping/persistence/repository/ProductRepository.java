package com.ethoca.shopping.persistence.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.Product;


@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findAll();
	
	Product findByProdId(int prodId);
	
	

	
}
