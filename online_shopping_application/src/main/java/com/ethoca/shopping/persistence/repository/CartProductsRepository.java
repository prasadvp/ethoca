package com.ethoca.shopping.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ethoca.shopping.model.CartProducts;

@Repository("CartProductsRepository")
public interface CartProductsRepository extends JpaRepository<CartProducts, Integer> {

	//@NamedNativeQuery(query = "select p.prodId, p.name as prodName, p.description as prodDescription, p.price, c.quantity from cart c join product p  on c.prodId = p.prodId where c.userId = :userId and c.active = true", nativeQuery = true)
	List<CartProducts> findProductsByUserId(@Param("userId") int userId);
	
}
