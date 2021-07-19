package com.pupil.handson.test.service.validator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;

/**
 * SquareValidator implementation
 * 
 * @author PattathilS
 *
 */
@Component
public class SquareValidator implements ShapeValidator {

	static Logger logger = LogManager.getLogger(SquareValidator.class);

	@Override
	public boolean isValid(Shape shape) throws InvalidSquareException, SquareOverlapException {
		if (!SquareValidatorHelper.isValidSquare(shape)) {
			logger.error("InvalidSquareException - Not a valid square shape [{}]", shape);
			throw new InvalidSquareException("InvalidSquareException - Not a valid square shape [" + shape + "]");
		}
		return true;
	}

	@Override
	public boolean isOverlappingWithExistingShapes(Shape shape, List<Shape> existingShapes) {
		if (SquareValidatorHelper.isOverlappingWithExistingShapes(shape, existingShapes)) {
			logger.error("SquareOverlapException - Square [{}] overlaps with existing squares", shape);
			throw new SquareOverlapException("SquareOverlapException - Square [" + shape + "] overlaps with existing squares");
		}
		return false;
	}

}
