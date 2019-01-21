package training.dev.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import training.dev.error.handling.GlobalExceptionHandler;
import training.dev.rest.controllers.EmailController;

//@RunWith(MockitoJUnitRunner.class) // Run Mockito JUnitRunner  for Mockito Framework

@RunWith(SpringRunner.class)
@WebMvcTest(EmailController.class)
public class BasicApplicationTest {

	@Autowired
	private MockMvc mockMvc; // Mock the MVC Configuration
	
	private PrintWriter console = new PrintWriter(System.out, true); // System.out.print short version
	
	private RequestBuilder request; // contruct request components
	
	private MvcResult result; // display result in console.print()

	/*
	 * mocking controller
	 */
	@Mock 
	private EmailController controller;

	/*
	 * construct which controller to mock (test)
	 */
	@Before 
	public void setUp() throws Exception {

		/*
		 * allows to identify the controller to test and perform the 
		 * necesary operations: GET, PUT, POST, DELETE
		 * but, the most important helps to initialize the mock mvc with EmailController
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(EmailController.class)
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
		
		/*
		 * comment from: https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/#Strategy_1_MockMVC_in_Standalone_Mode
		 * 
		 * This is because you don’t have any Spring context that can inject them automatically.
		 */
	}

	@Test 
	public void testCreateEmailAddresses() throws Exception {
		String email = "{\"email\":\"JuanDelPueblo@hotmail.com\"}";

		request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		result = mockMvc.perform(request)
				.andExpect(status().is(201))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{\"message\":\"Email address -> JuanDelPueblo@hotmail.com <- saved\"}"))
				.andExpect(header().string("location", containsString("/users/emails-addresses/emailController")))
				.andDo(print())
				.andReturn();

		console.println("Message : " + result.getResponse().getContentAsString());
	}

	@Test 
	public void testCreateEmailAddressesWithSpecialCharacters() throws Exception {
		String email = "{\"email\":\"JuanDelPueblo@hotmail..com.com\"}";

		request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		result = mockMvc.perform(request)
				.andExpect(status().is(201))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{\"message\":\"Email address -> JuanDelPueblo@hotmail..com.com <- saved\"}"))
				.andDo(print())
				.andReturn();

		console.println("Message : " + result.getResponse().getContentAsString());
	}

	@Test 
	public void testGetAllEmailAddresses() throws Exception {

		request = MockMvcRequestBuilders
				.get("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE) 
				.accept(MediaType.APPLICATION_JSON_VALUE);

		result = mockMvc.perform(request)
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andReturn();

		console.println("Message : " + result.getResponse().getContentAsString());
	}

	@Test 
	public void testNoEmailToSend() throws Exception {
		String email = "{\"email\":\"\"}";

		request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		result = mockMvc.perform(request)
				.andExpect(status().is(400))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json("{\"message\":\"Request has no email address sent in body request\"}"))
				.andDo(print())
				.andReturn();

		console.println("Message : " + result.getResponse().getContentAsString());

	}
	
	@Test 
	public void testControllerAdviceHttpBadUrl() throws Exception {
		
		MockHttpServletResponse response = mockMvc
				.perform(get("/user-address")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is(404))
				.andDo(print())
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		
		/**
		 * Syntax: assertThat([value], [matcher statement]);
		 */
	}
	
	@Test 
	public void testControllerAdviceHyperMedia() throws Exception {
		
		MockHttpServletResponse response = mockMvc
				.perform(get("/users/emails-addresses/emailController")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_XML_VALUE))
				.andExpect(status().is(415))
				.andDo(print())
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
		
		/**
		 * Syntax: assertThat([value], [matcher statement]);
		 */
	}
}
