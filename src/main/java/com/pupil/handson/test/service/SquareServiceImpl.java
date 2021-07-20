package com.pupil.handson.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.dao.SquareRepository;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;
import com.pupil.handson.test.service.validator.SquareValidator;

/**
 * 
 * @author PattathilS
 *
 */

@Service
public class SquareServiceImpl implements SquareService {

	@Autowired
	SquareRepository squareRepository;

	@Autowired
	SquareValidator squareValidator;

	@Override
	public List<Square> getAllSquares() {
		return squareRepository.findByType(ShapeType.SQUARE);
	}

	@Override
	@CacheEvict(value = "AllShapes", allEntries = true)
	public Square addSquare(Square square) {
		if (!squareValidator.isValid(square))
			throw new InvalidSquareException();
		if (squareValidator.isOverlappingWithExistingShapes(square, getAllSquares()))
			throw new SquareOverlapException();
		return squareRepository.save(square);
	}

}
