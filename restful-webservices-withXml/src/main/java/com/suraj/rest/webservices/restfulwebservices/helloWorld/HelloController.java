package com.suraj.rest.webservices.restfulwebservices.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world")
	public String helloWorld()
	{
		return "Hello World";
		
	}
	
	@GetMapping(path="/hello-world-Internationalization")
	public String helloWorldInternationalization(@RequestHeader(name="Accept-Language",required=false)Locale locale)
	{
		return messageSource.getMessage("good.morning.message", null, locale);
		
	}
	

	
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean()
	{
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path="/greet/{name}")
	public String greet(@PathVariable String name)
	{
		return (String.format("Welcome %s", name));
	}
	
	@GetMapping(path="/hello-world-bean/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name)
	{
		return new HelloWorldBean(String.format("Hello World , %s", name));
	}

}
