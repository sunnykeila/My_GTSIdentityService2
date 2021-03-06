package com.gts.users.controllers;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gts.users.model.request.UserDetailsRequestModel;
import com.gts.users.model.response.JsonResponseModel;
import com.gts.users.model.response.RoleRest;
import com.gts.users.model.response.UserRest;
import com.gts.users.repositories.ResponseMessageConstants;
import com.gts.users.services.RoleService;
import com.gts.users.services.UserService;
import com.gts.users.shared.dto.RoleDto;
import com.gts.users.shared.dto.UserDto;



@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class UserController {


	  @Autowired
	  private UserService userService;
	  
	  @Autowired
	  private RoleService roleService;
	  
	   @PostMapping( produces = { MediaType.APPLICATION_JSON_VALUE })
       public JsonResponseModel  createUser(@RequestBody  UserDetailsRequestModel userDetails ) throws Exception {
		  
		     JsonResponseModel returnValue = new JsonResponseModel();
		
		     ModelMapper modelMapper = new ModelMapper();
	     	 UserDto userDto = modelMapper.map(userDetails, UserDto.class);
	     	
		      userDto =  userService.createUser(userDto);
		     
		       if(userDto == null) {
				    returnValue.setSuccess(ResponseMessageConstants.getSuccessFalse());
				    returnValue.setMessage(ResponseMessageConstants.getAccountCreatedFailure());
				    returnValue.setStatus_code(ResponseMessageConstants.getResponse500());
		       }else {
				   returnValue.setSuccess(ResponseMessageConstants.getSuccessTrue());
				   returnValue.setMessage(ResponseMessageConstants.getAccountCreatedSuccess());
				   returnValue.setStatus_code(ResponseMessageConstants.getResponse200()); 
		       }
		       
		    return returnValue;
	    }
	   
	   @PostMapping( path= "/create22" , produces = { MediaType.APPLICATION_JSON_VALUE })
       public JsonResponseModel  createUser22(@RequestBody  UserDetailsRequestModel userDetails ) throws Exception {
		  
		     JsonResponseModel returnValue = new JsonResponseModel();
		
		     
		     ModelMapper modelMapper = new ModelMapper();
	     	 UserDto userDto = modelMapper.map(userDetails, UserDto.class);
	     	
		      userDto =  userService.createUser22(userDto);
		     
		       if(userDto == null) {
				    returnValue.setSuccess(ResponseMessageConstants.getSuccessFalse());
				    returnValue.setMessage(ResponseMessageConstants.getAccountCreatedFailure());
				    returnValue.setStatus_code(ResponseMessageConstants.getResponse500());
		       }else {
				   returnValue.setSuccess(ResponseMessageConstants.getSuccessTrue());
				   returnValue.setMessage(ResponseMessageConstants.getAccountCreatedSuccess());
				   returnValue.setStatus_code(ResponseMessageConstants.getResponse200()); 
		       }
		       
		    return returnValue;
	    }


	
	   @GetMapping(path = "{id}" ,produces = { MediaType.APPLICATION_JSON_VALUE })
	   public <T> T getUser(@PathVariable long id) {
		   
		   UserRest returnValue = new UserRest();
		   JsonResponseModel returnValue2 = new JsonResponseModel();
		   ModelMapper modelMapper = new ModelMapper();
		   
		   UserDto userDto = userService.getUserByid(id);
		   
		   if(userDto == null) {
			    returnValue2.setSuccess(ResponseMessageConstants.getSuccessFalse());
			    returnValue2.setMessage("Not found user with this User ID");
			    returnValue2.setStatus_code(ResponseMessageConstants.getResponse500());
			   
			  return (T) returnValue2;  
		   }
		   
           returnValue = modelMapper.map(userDto, UserRest.class);
		   
		   
		   return (T) returnValue;
	   }
	   
	   
	   @PutMapping(path = "/{id}"  , produces = { MediaType.APPLICATION_JSON_VALUE })
	   public JsonResponseModel updateUser(@PathVariable long id , @RequestBody  UserDetailsRequestModel userDetails ) {
		   
		   JsonResponseModel returnValue = new JsonResponseModel();
		  
		   
		   ModelMapper modelMapper = new ModelMapper();
	       UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		   
		     userDto =  userService.updateUser(id, userDto);

		   if(userDto == null) {
			    returnValue.setSuccess(ResponseMessageConstants.getSuccessFalse());
			    returnValue.setMessage(ResponseMessageConstants.getAcoountUpdationFailure());
			    returnValue.setStatus_code(ResponseMessageConstants.getResponse500());
	       }else {
			   returnValue.setSuccess(ResponseMessageConstants.getSuccessTrue());
			   returnValue.setMessage(ResponseMessageConstants.getAcoountUpdationSuccess());
			   returnValue.setStatus_code(ResponseMessageConstants.getResponse200()); 
	       }
		   
		   return returnValue;
		   
	   }
	   @PreAuthorize("hasAuthority('CAN_DELETE_ACCOUNT') or # id == principal.userId")
	   @DeleteMapping(path = "/{id}"  , produces = { MediaType.APPLICATION_JSON_VALUE })
	   public JsonResponseModel deleteUser(@PathVariable  long id) {
		   
		   JsonResponseModel returnValue = new JsonResponseModel();
		   
		   UserDto userDto =  userService.deleteUser(id);
		   
		   if(userDto == null) {
			    returnValue.setSuccess(ResponseMessageConstants.getSuccessFalse());
			    returnValue.setMessage("User can not be deleted");
			    returnValue.setStatus_code(ResponseMessageConstants.getResponse500());
	       }else {
			   returnValue.setSuccess(ResponseMessageConstants.getSuccessTrue());
			   returnValue.setMessage("User successfully deleted");
			   returnValue.setStatus_code(ResponseMessageConstants.getResponse200()); 
	       }
		  
		   
		   return returnValue;
	   }
	   
	   @PreAuthorize("hasAuthority('CAN_GET_LIST_OF_USERS')")
	   @GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE })
	   public <T> T getAllUsers(@RequestParam(value = "page" , defaultValue = "0") int page,
			                             @RequestParam(value = "limit" , defaultValue = "25") int limit) {
		   
		   List<UserRest> returnValue = new ArrayList<>();
		   JsonResponseModel returnValue2 = new JsonResponseModel();
		   ModelMapper modelMapper = new ModelMapper();
		   
		   List<UserDto> allUsers = userService.getAllUsers(page, limit);
		   
		   if(allUsers == null) {
			    returnValue2.setSuccess(ResponseMessageConstants.getSuccessFalse());
			    returnValue2.setMessage("All user list can not be displayed");
			    returnValue2.setStatus_code(ResponseMessageConstants.getResponse500());
			   
			  return (T) returnValue2;  
		   }
		   
		   for(UserDto userDto : allUsers ) {
			   returnValue.add(modelMapper.map(userDto, UserRest.class));
		   }
		   
		   return (T) returnValue;
	   }
	   
	   @GetMapping(path="/getRolesById/{id}")
	   public <T> T getAllRolesByUserId(@PathVariable long id) {
		  
		   List<RoleRest> returnValue = new ArrayList<>();
		   JsonResponseModel returnValue2 = new JsonResponseModel();
		   ModelMapper modelMapper = new ModelMapper();
		   
		      List<RoleDto> roles  =  roleService.getAllRolesUserId(id);
		  
		      if(roles != null) {
		 
		       roles.forEach((roleDto) -> {
		        	                   returnValue.add(modelMapper.map(roleDto, RoleRest.class));
		              });
		         
		    	  
		    return (T) returnValue;
		      
		      }else {
		            returnValue2.setSuccess(ResponseMessageConstants.getSuccessFalse());
				    returnValue2.setMessage("No role is defined to this user");
				    returnValue2.setStatus_code(ResponseMessageConstants.getResponse500());
				   
		   return (T) returnValue2;
		      }
	   }
	   
	   
}
