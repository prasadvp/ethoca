package com.ethoca.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import com.ethoca.shopping.OnlineShoppingApplication;
import com.ethoca.shopping.constants.ShoppingConstants;
import com.ethoca.shopping.model.Message;
import com.ethoca.shopping.model.Order;
import com.ethoca.shopping.model.Product;
import com.ethoca.shopping.model.ProductRequest;
import com.ethoca.shopping.model.Response;
import com.ethoca.shopping.model.User;
import com.ethoca.shopping.persistence.repository.CartRepository;
import com.ethoca.shopping.persistence.repository.ProductRepository;
import com.ethoca.shopping.service.ShoppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
//@SpringBootTest(
  //SpringBootTest.WebEnvironment.MOCK,
 // classes = Application.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes=OnlineShoppingApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class ShoppingControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ProductRepository prodRepo;
    
    @Autowired
    private CartRepository cartRepo;
    
    @MockBean
    @Qualifier("shoppingService")
	private ShoppingService shoppingSvc;
    
    private  List<Product> productList;
   /* 
    @BeforeAll
    public void beforeAll() {
    		productList = new ArrayList<>();
    		Product prod = new Product();
    		prod.setAvailbleQuantity(10);
    		prod.setPrice(BigDecimal.valueOf(1.00));
    		prod.setProdDescription("Product description");
    		prod.setProdId(123);
    		prod.setProdName("Test Prod");
    		productList.add(prod);
    		
    }*/
   public String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
     }
    public <T> T mapFromJson(String json, Class<T> clazz)
    	      throws JsonParseException, JsonMappingException, IOException {
    	      
    	      ObjectMapper objectMapper = new ObjectMapper();
    	      return objectMapper.readValue(json, clazz);
    	   }
    @Test
    public void shopgSvcReturnProdList() throws Exception {
    	productList = new ArrayList<>();
		Product prod = new Product();
		prod.setAvailbleQuantity(10);
		prod.setPrice(BigDecimal.valueOf(1.00));
		prod.setProdDescription("Product description");
		prod.setProdId(123);
		prod.setProdName("Test Prod");
		productList.add(prod);
    		when(shoppingSvc.getProducts()).thenReturn(productList);
    		
    		this.mvc.perform(get("/retail/v1/products")).andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("Test Prod")));
    		
    }
    
    @Test
    public void shopgSvcReturnProduct() throws Exception {
    //	productList = new ArrayList<>();
		Product prod = new Product();
		prod.setAvailbleQuantity(10);
		prod.setPrice(BigDecimal.valueOf(1.00));
		prod.setProdDescription("Product description");
		prod.setProdId(123);
		prod.setProdName("Test Prod");
		//productList.add(prod);
    		when(shoppingSvc.getProduct(123)).thenReturn(prod);
    		
    		this.mvc.perform(get("/retail/v1/products/123")).andDo(print()).andExpect(status().isOk())
    		.andExpect(content().string(containsString("Test Prod")));
    		
    }
    
    @Test
    public void shopgSvcReturnErrorOnInvalidProduct() throws Exception {
   
    		when(shoppingSvc.getProduct(123)).thenReturn(null);
    		
    		this.mvc.perform(get("/retail/v1/products/123")).andDo(print()).andExpect(status().is4xxClientError())
    		.andExpect(content().string(containsString("Invalid Product ID")));
    		
    }
    
    @Test
    public void shopgSvcReturnErrorOnAddProduct() throws Exception {
   
    		ProductRequest request = new ProductRequest();
		//	when(shoppingSvc.addProduct(request)).thenReturn(null);
    		String inputJson = mapToJson(request);
    		this.mvc.perform(post("/retail/v1/product") 
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(inputJson)).andDo(print()).andExpect(status().is4xxClientError())
    		.andExpect(content().string(containsString("Invalid Input")));
    		
    }
    
    @Test
    public void shopgSvcReturnOkOnAddProduct() throws Exception {
   
    		ProductRequest request = new ProductRequest();
    		Product prod = new Product();
    		prod.setProdId(123);
    		User user = new User();
    		user.setUserId(307011);
    		request.setProduct(prod);
    		request.setUser(user);
    		request.setRequiredQuantity(1);
    		Response resp = new Response();
    		resp.getMessages().add(new Message(ShoppingConstants.PRODUCT_ADDED_TO_CART.getCode(), ShoppingConstants.PRODUCT_ADDED_TO_CART.getDescription()));
		
    		when(shoppingSvc.addProduct(request)).thenReturn(resp);
    		String inputJson = mapToJson(request);
    		this.mvc.perform(post("/retail/v1/product") 
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(inputJson)).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void shopgSvcReturnErrorOnUpdateCart() throws Exception {
   
    		ProductRequest request = new ProductRequest();
		//	when(shoppingSvc.addProduct(request)).thenReturn(null);
    		String inputJson = mapToJson(request);
    		this.mvc.perform(put("/retail/v1/product") 
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(inputJson)).andDo(print()).andExpect(status().is4xxClientError())
    		.andExpect(content().string(containsString("Invalid Input")));
    		
    }
    
    @Test
    public void shopgSvcReturnOkOnUpdateCart() throws Exception {
   
    		ProductRequest request = new ProductRequest();
    		Product prod = new Product();
    		prod.setProdId(123);
    		User user = new User();
    		user.setUserId(307011);
    		request.setProduct(prod);
    		request.setUser(user);
    		request.setRequiredQuantity(1);
    		Response resp = new Response();
    		resp.getMessages().add(new Message(ShoppingConstants.PRODUCT_UPDATE_SUCCESSFUL.getCode(), ShoppingConstants.PRODUCT_UPDATE_SUCCESSFUL.getDescription()));
		
    		when(shoppingSvc.updateCart(request)).thenReturn(resp);
    		String inputJson = mapToJson(request);
    		this.mvc.perform(post("/retail/v1/product") 
    				.contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(inputJson)).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void shopgSvcReturnOkOnSubmitOrder() throws Exception{
    		Order order = new Order();
    		order.setOrderId(123);
    		ProductRequest request = new ProductRequest();
    		Product prod = new Product();
    		prod.setProdId(123);
    		User user = new User();
    		user.setUserId(307011);
    		request.setProduct(prod);
    		request.setUser(user);
    		request.setRequiredQuantity(1);
    		String inputJson = mapToJson(request);
    		
    		when(shoppingSvc.submitOrder(request)).thenReturn(order.getOrderId());
    		this.mvc.perform(post("/retail/v1/order").contentType(MediaType.APPLICATION_JSON_VALUE)
    				.content(inputJson)).andDo(print()).andExpect(status().isOk());
    }
}
