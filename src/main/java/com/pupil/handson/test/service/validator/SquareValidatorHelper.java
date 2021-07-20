package com.pupil.handson.test.service.validator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;

/**
 * SquareValidatorHelper
 * 
 * @author PattathilS
 *
 */
public class SquareValidatorHelper {

	static Logger logger = LogManager.getLogger(SquareValidatorHelper.class);

	/**
	 * Validate the square by checking all sides are equal and not zero
	 * 
	 * @param shape
	 * @return
	 */
	public static boolean isValidSquare(Square square) {
		double leftSideLength = findDistance(square.getXTopLeft(), square.getYTopLeft(), square.getXBottomLeft(), square.getYBottomLeft());
		double rightSideLength = findDistance(square.getXTopRight(), square.getYTopRight(), square.getXBottomRight(), square.getYBottomRight());
		double bottomSideLength = findDistance(square.getXBottomRight(), square.getYBottomRight(), square.getXBottomLeft(), square.getYBottomLeft());
		double topSideLength = findDistance(square.getXTopRight(), square.getYTopRight(), square.getXTopLeft(), square.getYTopLeft());

		return leftSideLength != 0 && leftSideLength == rightSideLength && rightSideLength == bottomSideLength && bottomSideLength == topSideLength;

	}

	/**
	 * Find distance between two coordinates
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static double findDistance(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
		return Math.sqrt(Math.pow(x2.subtract(x1).doubleValue(), Double.valueOf("2"))
				+ Math.pow(y2.subtract(y1).doubleValue(), Double.valueOf("2")));
	}

	/**
	 * Validate the square by checking any of the coordinates are not overlapping
	 * with existing squares
	 * 
	 * @param shape
	 * @param existingShapes
	 * @return
	 */
	public static boolean isOverlappingWithExistingShapes(Square square, List<Square> existingSquares) {
		return existingSquares.parallelStream().filter(existingSquare -> ShapeType.SQUARE.equals(existingSquare.getType()))
				.anyMatch(existingShape -> isOverlapping(square, existingShape));
	}

	/**
	 * Checks any coordinates of the square provided is overlapping with any of
	 * existing squares or exactly overlapping with any of existing squares
	 * 
	 * @param square
	 * @param existingSquares
	 * @return
	 */
	private static boolean isOverlapping(Square square, Square existingSquares) {

		if (isInside(square.getXBottomLeft(), square.getYBottomLeft(), existingSquares)) {
			logOverlapError(square.getXBottomLeft(), square.getYBottomLeft(), existingSquares);
			return true;
		} else if (isInside(square.getXBottomRight(), square.getYBottomRight(), existingSquares)) {
			logOverlapError(square.getXBottomRight(), square.getYBottomRight(), existingSquares);
			return true;
		} else if (isInside(square.getXTopLeft(), square.getYTopLeft(), existingSquares)) {
			logOverlapError(square.getXTopLeft(), square.getYTopLeft(), existingSquares);
			return true;
		} else if (isInside(square.getXTopRight(), square.getYTopRight(), existingSquares)) {
			logOverlapError(square.getXTopRight(), square.getYTopRight(), existingSquares);
			return true;
		} else if (isExactlyOverlapping(square, existingSquares)) {
			logOverlapError(null, null, existingSquares);
			return true;
		} else if (isExactlyInside(existingSquares, square)) {
			logOverlapError(null, null, existingSquares);
			return true;
		} else if (isAnySideInside(existingSquares, square)) {
			logOverlapError(null, null, existingSquares);
			return true;
		} else if (isAnySideInside(square, existingSquares)) {
			logOverlapError(null, null, existingSquares);
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the square exactly overlaps with any of existing square
	 * 
	 * @param square
	 * @param existingSquares
	 * @return
	 */
	private static boolean isExactlyOverlapping(Square square, Square existingSquares) {
		return square.getXBottomLeft().compareTo(existingSquares.getXBottomLeft()) == 0
				&& square.getXBottomRight().compareTo(existingSquares.getXBottomRight()) == 0
				&& square.getXTopRight().compareTo(existingSquares.getXTopRight()) == 0
				&& square.getXTopLeft().compareTo(existingSquares.getXTopLeft()) == 0
				&& square.getYBottomLeft().compareTo(existingSquares.getYBottomLeft()) == 0
				&& square.getYBottomRight().compareTo(existingSquares.getYBottomRight()) == 0
				&& square.getYTopRight().compareTo(existingSquares.getYTopRight()) == 0
				&& square.getYTopLeft().compareTo(existingSquares.getYTopLeft()) == 0;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param existingSquares
	 */
	private static void logOverlapError(BigDecimal x, BigDecimal y, Square existingSquares) {
		String errorMessage = "";
		if (x == null && y == null)
			errorMessage = "Given coordinates are exactly overlapping " + "with existing Square {"
					+ existingSquares.getXBottomLeft() + "," + existingSquares.getYBottomLeft() + "}, " + "{"
					+ existingSquares.getXTopRight() + "," + existingSquares.getYTopRight() + "}";
		else
			errorMessage = "Overlap detected for coordinates {" + x + "," + y + "}  " + "with {"
					+ existingSquares.getXBottomLeft() + "," + existingSquares.getYBottomLeft() + "} " + "and  {"
					+ existingSquares.getXTopRight() + "," + existingSquares.getYTopRight() + "}";
		logger.error(errorMessage);
	}

	/**
	 * Checks whether the provided coordinates are inside the existing square
	 * 
	 * @param x
	 * @param y
	 * @param existingShape
	 * @return
	 */
	private static boolean isInside(BigDecimal x, BigDecimal y, Square existingSquare) {
		return (isBetween(x, existingSquare.getXBottomLeft(), existingSquare.getXBottomRight())
				&& isEqualOrBetween(y, existingSquare.getYBottomLeft(), existingSquare.getYTopLeft()))
				|| (isBetween(x, existingSquare.getXBottomLeft(), existingSquare.getXBottomRight())
				&& isBetween(y, existingSquare.getYBottomLeft(), existingSquare.getYTopLeft()));
	}


	private static boolean isAnySideInside(Square existingSquare, Square square) {
		return ((isEqualOrBetween(existingSquare.getXBottomLeft(), square.getXBottomLeft(), square.getXBottomRight())
				&& isBetween(existingSquare.getYBottomLeft(), square.getYBottomLeft(), square.getYTopLeft()))
				|| (isEqualOrBetween(existingSquare.getXBottomRight(), square.getXBottomLeft(), square.getXBottomRight())
				&& isBetween(existingSquare.getYTopLeft(), square.getYBottomLeft(), square.getYTopLeft())))			
				|| ((isBetween(existingSquare.getXBottomLeft(), square.getXBottomLeft(), square.getXBottomRight())
				&& isEqualOrBetween(existingSquare.getYBottomLeft(), square.getYBottomLeft(), square.getYTopLeft()))
				|| (isBetween(existingSquare.getXBottomRight(), square.getXBottomLeft(), square.getXBottomRight())
				&& isEqualOrBetween(existingSquare.getYTopLeft(), square.getYBottomLeft(), square.getYTopLeft())));
	}

	private static boolean isExactlyInside(Square existingSquare, Square square) {
		return (isEqualOrBetween(existingSquare.getXBottomLeft(), square.getXBottomLeft(), square.getXBottomRight())
				&& isEqualOrBetween(existingSquare.getXBottomRight(), square.getXBottomLeft(), square.getXBottomRight())
				&& isEqualOrBetween(existingSquare.getYBottomLeft(), square.getYBottomLeft(), square.getYTopLeft())
				&& isEqualOrBetween(existingSquare.getYTopLeft(), square.getYBottomLeft(), square.getYTopLeft()));
	}

	private static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
		return (value.compareTo(min) == 1 && value.compareTo(max) == -1);
	}

	private static boolean isEqualOrBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
		return (value.compareTo(min) >= 0 && value.compareTo(max) <= 0);
	}
}
