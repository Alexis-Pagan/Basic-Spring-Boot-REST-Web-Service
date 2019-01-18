# Basic-Spring-Boot-REST-Web-Service
The following Spring Boot Project is to begin trainings showing simple concepts of REST. This will be a series from Basic to Advanced.

Version 4.2 is available! 

Includes: 

1] change content type of testNoEmail from String email = ""; <- this is not JSON content

from:
	@Test 
	public void testNoEmail() throws Exception {
		String email = "";
    
to: 
	@Test 
	public void testNoEmail() throws Exception {
		String email = "{\"email\":\"\"}";        <- this is JSON content, but empty (here the forward slash GitHub omitted them).
    
2] Added HttpHeaders definition for Accept and ContentType in GET and POST methods

		httpHeader.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		httpHeader.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    
3] Global variable for HttpHeaders: there was duplication code. 

4] Better output description for JUnit Tests: 

MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /users/emails-addresses/emailController
       Parameters = {}
          Headers = {Content-Type=[application/json]}
             Body = <no character encoding set>
    Session Attrs = {}

Handler:
             Type = training.dev.rest.controllers.EmailController
           Method = public org.springframework.http.ResponseEntity<training.dev.models.Response> training.dev.rest.controllers.EmailController.createEmailAddress(training.dev.models.User)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 201
    Error message = null
          Headers = {Accept=[application/json], Content-Type=[application/json], Location=[/users/emails-addresses/emailController]}
     Content type = application/json
             Body = {"message":"Email address -> abcd@gmail.com <- saved"}
    Forwarded URL = null
   Redirected URL = /users/emails-addresses/emailController
          Cookies = []
{"message":"Email address -> abcd@gmail.com <- saved"}

MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /users/emails-addresses/emailController
       Parameters = {}
          Headers = {Content-Type=[application/json], Accept=[application/json]}
             Body = <no character encoding set>
    Session Attrs = {}

Handler:
             Type = training.dev.rest.controllers.EmailController
           Method = public org.springframework.http.ResponseEntity<java.util.ArrayList<java.lang.String>> training.dev.rest.controllers.EmailController.readEmailAddress()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = {Accept=[application/json], Content-Type=[application/json]}
     Content type = application/json
             Body = []
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
Email returned: []

MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /users/emails-addresses/emailController
       Parameters = {}
          Headers = {Content-Type=[application/json]}
             Body = <no character encoding set>
    Session Attrs = {}

Handler:
             Type = training.dev.rest.controllers.EmailController
           Method = public org.springframework.http.ResponseEntity<training.dev.models.Response> training.dev.rest.controllers.EmailController.createEmailAddress(training.dev.models.User)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 201
    Error message = null
          Headers = {Accept=[application/json], Content-Type=[application/json], Location=[/users/emails-addresses/emailController]}
     Content type = application/json
             Body = {"message":"Email address -> test-email-@test..com <- saved"}
    Forwarded URL = null
   Redirected URL = /users/emails-addresses/emailController
          Cookies = []
          
{"message":"Email address -> test-email-@test..com <- saved"}

MockHttpServletRequest:
      HTTP Method = POST
      Request URI = /users/emails-addresses/emailController
       Parameters = {}
          Headers = {Content-Type=[application/json]}
             Body = <no character encoding set>
    Session Attrs = {}

Handler:
             Type = training.dev.rest.controllers.EmailController
           Method = public org.springframework.http.ResponseEntity<training.dev.models.Response> training.dev.rest.controllers.EmailController.createEmailAddress(training.dev.models.User)

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 400
    Error message = null
          Headers = {Content-Type=[application/json]}
     Content type = application/json
             Body = {"message":"Request has no email address sent in body request"}
    Forwarded URL = null
   Redirected URL = null
          Cookies = []


This is considered a final output for Basic Rest project. However, minor changes may occurr during next days.
