package com.ethoca.shopping.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ethoca.shopping.model.Cart;

@Repository("CartRepository")
public interface CartRepository extends JpaRepository<Cart, Long>{
	
	List<Cart> findByUserId(long userId);
	
	Cart findProductByUserId(long userId, long prodId);
}
