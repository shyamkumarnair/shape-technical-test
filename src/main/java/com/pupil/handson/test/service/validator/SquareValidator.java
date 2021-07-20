package com.pupil.handson.test.service.validator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;

/**
 * SquareValidator implementation
 * 
 * @author PattathilS
 *
 */
@Component
public class SquareValidator implements ShapeValidator<Square> {

	static Logger logger = LogManager.getLogger(SquareValidator.class);

	@Override
	public boolean isValid(Square square) throws InvalidSquareException, SquareOverlapException {
		if (!SquareValidatorHelper.isValidSquare(square)) {
			logger.error("InvalidSquareException - Not a valid square shape [{}]", square);
			throw new InvalidSquareException("InvalidSquareException - Not a valid square shape [" + square + "]");
		}
		return true;
	}

	@Override
	public boolean isOverlappingWithExistingShapes(Square square, List<Square> existingSquares) {
		if (SquareValidatorHelper.isOverlappingWithExistingShapes(square, existingSquares)) {
			logger.error("SquareOverlapException - Square [{}] overlaps with existing squares", square);
			throw new SquareOverlapException(
					"SquareOverlapException - Square [" + square + "] overlaps with existing squares");
		}

		return false;
	}

}
