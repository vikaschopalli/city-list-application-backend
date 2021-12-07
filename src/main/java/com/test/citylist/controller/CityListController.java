package com.test.citylist.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.citylist.constant.Constants;
import com.test.citylist.entity.City;
import com.test.citylist.repository.CityRepository;

@RestController
public class CityListController {
	
	private static final Logger LOG = LoggerFactory.getLogger(CityListController.class);
	
	@Autowired
	private CityRepository cityRepository;
	
	@GetMapping("/auth")
	public Principal user(Principal user) {
		return user;
	}

	@GetMapping("/city/search")
	public ResponseEntity<Page<City>> searchCityByName(@RequestParam(name = "name", defaultValue = "") String name,@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") final int pageSize) {
		Page<City> searchResults = null;
		LOG.info("inside searching city by name {}",name);
		
		try {
			PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
			searchResults = cityRepository.findByNameContaining(name,pageRequest);
			return ResponseEntity.status(HttpStatus.OK).body(searchResults);
		} catch(Exception e) {
			LOG.error("Exception occurred while searching cities by name ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PutMapping("/city/edit/{id}")
	@PreAuthorize(value = "hasRole('ROLE_ALLOW_EDIT')")
	public ResponseEntity<String> editCityById(@PathVariable final Long id,@RequestBody final City city) {
		LOG.info("inside editing city for id {}",id);
		try {
			
			if(!cityRepository.findById(id).isPresent()) {
				LOG.info("id {} does not exist",id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.FAILURE);
			}
			cityRepository.save(city);
			return ResponseEntity.status(HttpStatus.OK).body(Constants.SUCCESS);
		} catch(Exception e) {
			LOG.error("Exception occurred while searching cities by name ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.FAILURE);
		}
	}
	
	@GetMapping("/city/{id}")
	public ResponseEntity<City> getCityById(@PathVariable final Long id) {
		LOG.info("inside get city for id {}",id);
		try {
			
			if(!cityRepository.findById(id).isPresent()) {
				LOG.info("city id {} does not exist",id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.status(HttpStatus.OK).body(cityRepository.findById(id).get());
		} catch(Exception e) {
			LOG.error("Exception occurred while searching cities by name ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@GetMapping("/city/getAllCities")
	public Page<City> fetchAllCityList(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "10") final int pageSize) {
		
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		Page<City> allProducts = cityRepository.findAll(pageRequest);
		return allProducts;
	}
}
