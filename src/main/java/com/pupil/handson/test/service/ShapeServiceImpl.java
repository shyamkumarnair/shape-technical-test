package com.pupil.handson.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.dao.ShapeRepository;

/**
 * Shape service implementation
 * 
 * @author PattathilS
 *
 */
@Service
public class ShapeServiceImpl implements ShapeService {

	@Autowired
	private ShapeRepository shapeRepository;

	@Override
	@Cacheable("AllShapes")
	public List<Shape> getAllShapes() {
		return shapeRepository.findAll();
	}

}
