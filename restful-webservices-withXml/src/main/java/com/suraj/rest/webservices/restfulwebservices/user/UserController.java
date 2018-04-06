package com.suraj.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	
	@Autowired
	private  UserDaoService userDaoService;
	
	//retrive All User
	@GetMapping(path="/userList")
	public List<User> getUserList()
	{
		return userDaoService.findAll();
	}
	
	
	//retrive User
	
	@GetMapping(path="/users/{id}")
	public Resource<User> getUser(@PathVariable int id)
	{
		User user=userDaoService.findOne(id);
		
		if(user==null)
			throw new UserNotFoundException("id-"+id);
		
		//adding a link for getUserList() 
		
		Resource<User> resource=new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserList());
		resource.add(linkTo.withRel("All-User"));
		
		//HATEOAS
		
		return resource;
	}
	
	@PostMapping(path="/users")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user)
	{
		User savedUser=userDaoService.save(user);
		
		URI location= ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable int id)
	{
		User user = userDaoService.deleteById(id);
		if(user==null)
		{
			throw new UserNotFoundException("id-"+id);
		}
	}
	

}
