package com.ethoca.shopping.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ethoca.shopping.model.CartProducts;
import com.ethoca.shopping.model.Product;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findAll();
	
	Product findByProdId(long prodId);
	
	@Query(value = "select p.prodId, p.prodName, p.prodDescription, p.price, c.quantity from cart c join product p  on c.prodId = p.prodId where c.userId = :userId)", nativeQuery = true)
	List<CartProducts> findProductsByUser(@Param("userId") long userId);
}
