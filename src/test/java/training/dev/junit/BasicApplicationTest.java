package training.dev.junit;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import training.dev.rest.controllers.EmailController;

@RunWith(SpringRunner.class) 
@SpringBootTest 
public class BasicApplicationTest {

	/**
	 * use mockito
	 * add negative 
	 * add couple of more positive test
	 */
	private MockMvc mockMvc; 

	/*
	 * mocking controller
	 */
	@Mock private EmailController controller;

	/*
	 * construct which controller to mock (test)
	 */
	@Before public void setUp() throws Exception {
		/*
		 * only testing one controller
		 */
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	
	}

	@Test public void testCreateEmailAddresses() throws Exception {
		String email = "{\"email\":\"abcd@gmail.com\"}";
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/users/emails-addresses/emailController")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email);
		
		MvcResult result = (MvcResult) mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andDo(print())
				.andReturn();
		/*
		 * assumptions
		 */
		assertEquals(200, result.getResponse().getStatus());
	}

	/*
	 * test readEmailAddress() method
	 */
	@Test public void testGetAllEmailAddresses() throws Exception {
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/users/emails-addresses/emailController")
				.accept(MediaType.APPLICATION_JSON_VALUE);

		MvcResult result = mockMvc.perform(request)
				.andExpect(status().is(200)) // hint: check methods
				.andDo(print())
				.andReturn();
		
		/*
		 * assumptions
		 */
		assertEquals(200, result.getResponse().getStatus());
	}

}
