package training.dev.junit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import training.dev.rest.controllers.EmailController;

@RunWith(MockitoJUnitRunner.class) // Run Mockito JUnitRunner  for Mockito Framework
@SpringBootTest // Identify this class as a Test class
public class BasicApplicationTest {

	private MockMvc mockMvc; // Mock the MVC Configuration
	private PrintWriter console = new PrintWriter(System.out, true);

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
		mockMvc = MockMvcBuilders.standaloneSetup(EmailController.class).build();
	}
	
	// positive test
	
	@Test 
	public void testCreateEmailAddresses() throws Exception {
		String email = "{\"email\":\"abcd@gmail.com\"}";

		RequestBuilder request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		MvcResult result = (MvcResult) mockMvc.perform(request)
				.andExpect(status().is(201))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().string("{\"message\":\"Email address -> abcd@gmail.com <- saved\"}"))
				.andDo(print())
				.andReturn();
		
		console.println(result.getResponse().getContentAsString());
		assertEquals(201, result.getResponse().getStatus());
	}
	
	@Test 
	public void testCreateEmailAddressesWithSpecialCharacters() throws Exception {
		String email = "{\"email\":\"test-email-@test..com\"}";

		RequestBuilder request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		MvcResult result = (MvcResult) mockMvc.perform(request)
				.andExpect(status().is(201))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andReturn();
		
		console.println(result.getResponse().getContentAsString());
		assertEquals(201, result.getResponse().getStatus());
	}
	
	@Test 
	public void testGetAllEmailAddresses() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE) 
				.accept(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().is(200))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andReturn();

		console.println("Email returned: " + result.getResponse().getContentAsString());
		
		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test 
	public void testNoEmail() throws Exception {
		String email = "{\"email\":\"\"}";

		RequestBuilder request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		MvcResult result = (MvcResult) mockMvc.perform(request)
				.andExpect(status().is(400))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andReturn();
		
		assertEquals(400, result.getResponse().getStatus());
	}
}
