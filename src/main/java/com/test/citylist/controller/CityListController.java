package com.test.citylist.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.citylist.constant.Constants;
import com.test.citylist.entity.City;
import com.test.citylist.repository.CityRepository;

/**
 * @author Vikas
 *
 */
@RestController
@RequestMapping("/api")
public class CityListController {

	private static final Logger LOG = LoggerFactory.getLogger(CityListController.class);

	@Autowired
	private CityRepository cityRepository;

	/**
	 * @param user
	 * @return
	 */
	@GetMapping("/auth")
	public Principal user(Principal user) {
		return user;
	}

	/**
	 * @param id
	 * @param city
	 * @return
	 */

	/*
	 * this controller end point is responsible for editing an city by it id ,
	 * this method can only be accessible by user having ROLE_EDIT_ACESS or else
	 * 401 unauthorized response will be returned
	 */

	@PutMapping("/city/{id}")
	@PreAuthorize(value = "hasRole('ROLE_ALLOW_EDIT')")
	public ResponseEntity<String> editCityById(@PathVariable final Long id, @RequestBody final City city) {
		LOG.info("inside editing city for id {}", id);
		try {

			if (!cityRepository.findById(id).isPresent()) {
				LOG.info("id {} does not exist", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.FAILURE);
			}
			cityRepository.save(city);
			return ResponseEntity.status(HttpStatus.OK).body(Constants.SUCCESS);
		} catch (Exception e) {
			LOG.error("Exception occurred while searching cities by name ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.FAILURE);
		}
	}

	/**
	 * @param id
	 * @return
	 */
	/*
	 * this controller end point is responsible to fetch particular city details
	 * by it id
	 */
	@GetMapping("/city/{id}")
	public ResponseEntity<City> findCityById(@PathVariable final Long id) {
		try {

			if (!cityRepository.findById(id).isPresent()) {
				LOG.info("city id {} does not exist", id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.status(HttpStatus.OK).body(cityRepository.findById(id).get());
		} catch (Exception e) {
			LOG.error("Exception occurred while searching cities by name ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */

	/*
	 * this controller end point is responsible for service side pagination ,
	 * when page number are clicked from angular application xhr request is
	 * trigerred and hit this end point with pageNumber and pageSize as input
	 * this method uses those details calls back end with parameters received ,
	 * this is more performant when we have huge data
	 */
	@GetMapping("/city")
	public ResponseEntity<Map<String, Object>> fetchCities(@RequestParam(required = false) String name,
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "3") final int pageSize) {

		try {
			Pageable pagingRequest = PageRequest.of(pageNumber, pageSize);
			List<City> cities = new ArrayList<City>();
			Page<City> page;
			if (name != null && !name.isEmpty()) {
				page = cityRepository.findByNameContaining(name, pagingRequest);
			} else {
				page = cityRepository.findAll(pagingRequest);
			}
			cities = page.getContent();
			Map<String, Object> response = new HashMap<>();
			response.put("totalItems", page.getTotalElements());
			response.put("totalPages", page.getTotalPages());
			response.put("content", cities);
			response.put("currentPage", page.getNumber());
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
