package com.test.citylist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.test.citylist.entity.City;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {

	Page<City> findByNameContaining(final @Param("name") String name,final Pageable pageable);
}
