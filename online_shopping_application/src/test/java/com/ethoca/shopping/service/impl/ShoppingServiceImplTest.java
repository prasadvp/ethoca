package com.ethoca.shopping.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ethoca.shopping.OnlineShoppingApplication;
import com.ethoca.shopping.constants.ShoppingConstants;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.model.Response;
import com.ethoca.shopping.persistence.dao.ShoppingDAO;
import com.ethoca.shopping.persistence.dao.impl.ShoppingDAOImpl;
import com.ethoca.shopping.service.ShoppingService;
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes=OnlineShoppingApplication.class)
public class ShoppingServiceImplTest {

	    
	@Autowired
	@Qualifier("shoppingDAO")
    @MockBean
	private ShoppingDAO shoppingDAO;
	    
	@Autowired
	private ShoppingService shoppingSvc;
	
	@TestConfiguration
    static class ShoppingServiceImplTestContextConfiguration {
  
        @Bean
        public ShoppingService productService() {
            return new ShoppingServiceImpl();
        }
    }

	@Test
	public  void getProductReturnEmpty() {
		when(shoppingDAO.findProductById(Mockito.anyInt())).thenReturn(null);
		
		Product prod = shoppingSvc.getProduct(123);
		
		assertThat(prod.getProdName(),is(equalTo(null)));
		
		
	}
	
	@Test
	public  void getProductReturnSuccess() {
		
		Product prod = new Product();
		prod.setProdId(123);
		prod.setProdName("Test");
		when(shoppingDAO.findProductById(Mockito.anyInt())).thenReturn(prod);
		
		Product prodt = shoppingSvc.getProduct(123);
		
		assertThat(prodt.getProdName(),is(equalTo("Test")));
		
		
	}
	
	@Test
	public  void addProductReturnQtyInvalid() {
		
		ProductRequest req = new ProductRequest();
		Product prod = new Product();
		prod.setProdId(123);
		prod.setProdName("Test");
		req.setProduct(prod);
		req.setRequiredQuantity(0);
		
		
		
		//Mockito.when(shoppingDAO.addProduct(req)).thenReturn(resp);
		
		Response resp = shoppingSvc.addProduct(req);
		
		assertThat(resp.getMessages().get(0).getDescription(),is(equalTo(ShoppingConstants.QUANTITY_INVALID_EXCEPTION.getDescription())));
		
		
	}
	
	@Test
	public  void addProductReturnProdInvalid() {
		
		ProductRequest req = new ProductRequest();
		Product prod = new Product();
		prod.setProdId(123);
		prod.setProdName("Test");
		req.setProduct(prod);
		req.setRequiredQuantity(1);
		
		
		//Mockito.when(shoppingDAO.addProduct(req)).thenReturn(resp);
		when(shoppingDAO.findProductById(Mockito.anyInt())).thenReturn(null);
		Response resp = shoppingSvc.addProduct(req);
		
		assertThat(resp.getMessages().get(0).getDescription(),is(equalTo(ShoppingConstants.PRODUCT_ID_VALIDATION_EXCEPTION.getDescription())));
		
		
	}
	
	@Test
	public  void addProductReturnQtyExceeded() {
		
		ProductRequest req = new ProductRequest();
		Product prod = new Product();
		prod.setProdId(123);
		prod.setProdName("Test");
		req.setProduct(prod);
		req.setRequiredQuantity(100);
		
		Product product = new Product();
		product.setAvailbleQuantity(10);
		product.setProdId(123);
		
		//Mockito.when(shoppingDAO.addProduct(req)).thenReturn(resp);
		when(shoppingDAO.findProductById(Mockito.anyInt())).thenReturn(product);
		Response resp = shoppingSvc.addProduct(req);
		
		assertThat(resp.getMessages().get(0).getDescription(),is(equalTo(ShoppingConstants.QUANTITY_REQUESTED_OVER_THE_LIMIT.getDescription())));
		
		
	}
	
	@Test
	public  void addProductReturnSuccess() {
		
		ProductRequest req = new ProductRequest();
		Product prod = new Product();
		prod.setProdId(456);
		prod.setProdName("Test");
		req.setProduct(prod);
		req.setRequiredQuantity(1);
		
		Product product = new Product();
		product.setAvailbleQuantity(10);
		product.setProdId(456);
		
		when(shoppingDAO.findProductById(Mockito.anyInt())).thenReturn(product);
		
		//Mockito.when(shoppingDAO.addProduct(req)).thenReturn(resp);
		ShoppingDAOImpl obj = mock(ShoppingDAOImpl.class);
		doNothing().when(obj).addProduct(req);
		Response resp = shoppingSvc.addProduct(req);
		verify(obj,times(0)).addProduct(req);
		
	}

}
