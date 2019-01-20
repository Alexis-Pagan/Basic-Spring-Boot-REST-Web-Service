# Basic-Spring-Boot-REST-Web-Service
The following Spring Boot Project is to begin trainings showing simple concepts of REST. This will be a series from Basic to Advanced.

Version 4.5.1 is available! 

What's new: 

1] JUnit @Test methods now have: 
  				.andExpect(content().json("{\"message\":\"Email address -> JuanDelPueblo@hotmail.com <- saved\"}"))

To identify the correct response message and formatting. 

1] Handling @ExceptionHandlers: 

{
  mockMvc = MockMvcBuilders.standaloneSetup(EmailController.class)
				.setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver()).build();
}

Added ExceptionHandlerExceptionResolver() to handle default system exceptions. This is under research. 

STATUS: changes to REST service no change or SwaggerUI.
