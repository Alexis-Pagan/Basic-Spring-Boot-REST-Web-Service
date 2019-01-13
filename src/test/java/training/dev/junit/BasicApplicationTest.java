package training.dev.junit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import training.dev.rest.controllers.EmailController;

@RunWith(MockitoJUnitRunner.class) // Run Mockito JUnitRunner  for Mockito Framework
@WebMvcTest(EmailController.class) // Testing one controller at the time
@SpringBootTest // Identify this class as a Test class
public class BasicApplicationTest {

	private MockMvc mockMvc; 

	/*
	 * mocking controller instance
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
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(EmailController.class).build();
	}

	@Test 
	public void testCreateEmailAddresses() throws Exception {
		String email = "{\"email\":\"abcd@gmail.com\"}";

		RequestBuilder request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);

		MvcResult result = (MvcResult) mockMvc.perform(request)
				.andExpect(status().is(201))
				.andDo(print())
				.andReturn();
		/*
		 * assumptions
		 */
		assertEquals(201, result.getResponse().getStatus());
	}

	/*
	 * test readEmailAddress() method
	 */
	@Test 
	public void testGetAllEmailAddresses() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE) 
				.accept(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().is(200))
				.andDo(print())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
		/*
		 * assumptions
		 */
		//assertEquals(200, result.getResponse().getStatus());
	}

	/*
	 * negativa test
	 */
	@Test
	public void testNoHandlerFound() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/emails-addresses") // bad uri
				.contentType(MediaType.APPLICATION_JSON_VALUE) 
				.accept(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().is(404))
				.andDo(print())
				.andReturn();

		System.out.println(result.getResponse().getContentAsString());
	}
}
