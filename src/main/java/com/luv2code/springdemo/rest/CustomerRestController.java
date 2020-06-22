package com.luv2code.springdemo.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomer(){
		return customerService.getCustomers();
	}
	
	@GetMapping(value="/download")
	public ResponseEntity<?> download(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException{
		ResponseEntity<?> responseEntity = null;
		String loc = "G://React/React_project_1/confusion1/public/images" + File.separator + fileName;
		File file = new File(loc);
		if(file.exists()) {
			URL url = new URL(loc);
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			FileChannel fileChannel = fileOutputStream.getChannel();
			
			/*
			 * OutputStream s = response.getOutputStream(); FileInputStream in = new
			 * FileInputStream(file); byte[] buffer = new byte[4096]; int length;
			 * while((length = in.read(buffer)) > 0) { s.write(buffer, 0, length); }
			 */
			responseEntity = ResponseEntity.ok(fileOutputStream.getChannel()
					  .transferFrom(readableByteChannel, 0, Long.MAX_VALUE));
	
		}
		return responseEntity;
		
		
		
	}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomers(@PathVariable int customerId) {
		Customer theCustomer=customerService.getCustomer(customerId);
		if(theCustomer==null) {
			throw new CustomerNotFounException("Customer id not found"+customerId);
		}
		return theCustomer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	@DeleteMapping(value="/delete-customer")
	public int deleteCustomer(@RequestParam int customerId) {
		Customer theCustomer =customerService.getCustomer(customerId);
		if(theCustomer==null) {
			throw new CustomerNotFounException("Customer id not found"+customerId);
		}
		customerService.deleteCustomer(customerId);
		
		return customerId;
	}
	
}
