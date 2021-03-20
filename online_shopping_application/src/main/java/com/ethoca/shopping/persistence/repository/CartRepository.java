package com.ethoca.shopping.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ethoca.shopping.model.Cart;

@Repository("CartRepository")
public interface CartRepository extends JpaRepository<Cart, Integer>{
	
	List<Cart> findByUserId(int userId);
	
	
	@Query(value="select * from cart c where c.userId = :userId and c.prodId = :prodId", nativeQuery = true)
	Cart findProductByUserId(@Param("userId")int userId, @Param("prodId")int prodId);

	@Modifying
	@Query(value="update cart c set c.active=false where c.userId = :userId", nativeQuery = true)
	void updateCart(@Param("userId")int userId) ;
}
