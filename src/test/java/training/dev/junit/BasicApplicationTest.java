package training.dev.junit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import training.dev.rest.controllers.EmailController;

@RunWith(MockitoJUnitRunner.class) // Run Mockito JUnitRunner  for Mockito Framework
@WebMvcTest(EmailController.class) // Web Mvc Test specify the controller to test
public class BasicApplicationTest {

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
		 * initilize fields with Mockito Annotations
		 */
		MockitoAnnotations.initMocks(BasicApplicationTest.class);
		/*
		 * allows to identify the controller to test and perform the 
		 * necesary operations: GET, PUT, POST, DELETE
		 * but, the most important helps to initialize the mock mvc with EmailController
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(EmailController.class)
				.setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
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

	@Test() //
	public void testBaduri() throws Exception {
		
		request = MockMvcRequestBuilders
				.post("/Controller");

		result = mockMvc.perform(request)
				.andExpect(status().is(404))
				.andDo(print())
				.andReturn();

		console.println("Message : " + result.getResponse().getContentAsString());
	}
}
