package com.ethoca.shopping.model;

import java.math.BigDecimal;
import java.time.Instant;
/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="order")*/
public class Order {
	private int orderId;
	
	private long orderDate;
	
	public int getOrderId() {
		return orderId;
	}
	

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public long getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(long epoch) {
		this.orderDate = epoch;
	}

	/*@Id
	@GeneratedValue
	private long orderId;
	
	@Column(name="userId")
	private long userId;
	
	@Column(name="orderDate")
	private Instant orderDate;

	@Column(name="totalAmt")
	private BigDecimal totalAmt;
	
	
	//private List<OrderDetails> orderDetails = new ArrayList<>();
	
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}



	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
/*
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}
*/
	
	
}
