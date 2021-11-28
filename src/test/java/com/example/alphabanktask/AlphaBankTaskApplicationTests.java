package com.example.alphabanktask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.Assert.*;

@SpringBootTest
class AlphaBankTaskApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void endpointRates() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/rates?base=eur")).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
		assertTrue(result.getResponse().getContentAsString().contains("<img src="));
	}

	@Test
	void badCodeException() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/rates?base=soe")).andReturn();
		assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
		assertTrue(result.getResponse().getContentAsString().equals("soe - bad base code"));
	}
}
