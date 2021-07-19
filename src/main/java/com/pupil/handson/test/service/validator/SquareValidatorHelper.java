package com.pupil.handson.test.service.validator;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pupil.handson.test.Entity.Shape;
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
	public static boolean isValidSquare(Shape shape) {
		if (shape instanceof Square) {
			Square square = (Square) shape;
			double leftSideLength = findDistance(square.getXTopLeft(), square.getYTopLeft(), square.getXBottomLeft(), square.getYBottomLeft());
			double rightSideLength = findDistance(square.getXTopRight(), square.getYTopRight(), square.getXBottomRight(), square.getYBottomRight());
			double bottomSideLength = findDistance(square.getXBottomRight(), square.getYBottomRight(), square.getXBottomLeft(), square.getYBottomLeft());
			double topSideLength = findDistance(square.getXTopRight(), square.getYTopRight(), square.getXTopLeft(), square.getYTopLeft());

			return leftSideLength != 0 && leftSideLength == rightSideLength && rightSideLength == bottomSideLength && bottomSideLength == topSideLength;
		}
		return false;
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
	 * Validate the square by checking any of the coordinates are not overlapping with existing squares
	 * 
	 * @param shape
	 * @param existingShapes
	 * @return
	 */
	public static boolean isOverlappingWithExistingShapes(Shape shape, List<Shape> existingShapes) {
		Square square = (Square) shape;
		return existingShapes.parallelStream()
				             .filter(existingShape -> ShapeType.SQUARE.equals(existingShape.getType()))
				             .map(existingShape -> (Square) existingShape)
				             .anyMatch(existingShape -> isOverlapping(square, existingShape));
	}
	
	/**
	 * Checks any coordinates of the square provided is overlapping with any of existing squares
	 * or exactly overlapping with any of existing squares
	 * 
	 * @param square
	 * @param existingShape
	 * @return
	 */
    private static boolean isOverlapping(Square square, Square existingShape) {
		
		if (isInside(square.getXBottomLeft(), square.getYBottomLeft(), existingShape)) {
			logOverlapError(square.getXBottomLeft(), square.getYBottomLeft(), existingShape);
			return true;
		} else if (isInside(square.getXBottomRight(), square.getYBottomRight(), existingShape)) {
			logOverlapError(square.getXBottomRight(), square.getYBottomRight(), existingShape);
			return true;
		} else if (isInside(square.getXTopLeft(), square.getYTopLeft(), existingShape)) {
			logOverlapError(square.getXTopLeft(), square.getYTopLeft(), existingShape);
			return true;
		} else if (isInside(square.getXTopRight(), square.getYTopRight(), existingShape)) {
			logOverlapError(square.getXTopRight(), square.getYTopRight(), existingShape);
			return true;
		} else if (isExactlyOverlapping(square, existingShape)) {
			logOverlapError(null, null, existingShape);
			return true;
		}
		return false;
	}

    /**
     * Checks whether the square exactly overlaps with any of existing square
     * @param square
     * @param existingShape
     * @return
     */
	private static boolean isExactlyOverlapping(Square square, Square existingShape) {
		return square.getXBottomLeft().compareTo(existingShape.getXBottomLeft()) == 0 && 
			   square.getXBottomRight().compareTo(existingShape.getXBottomRight()) == 0 && 
			   square.getXTopRight().compareTo(existingShape.getXTopRight()) == 0 && 
			   square.getXTopLeft().compareTo(existingShape.getXTopLeft()) == 0 && 
			   square.getYBottomLeft().compareTo(existingShape.getYBottomLeft()) == 0 && 
			   square.getYBottomRight().compareTo(existingShape.getYBottomRight()) == 0 &&
			   square.getYTopRight().compareTo(existingShape.getYTopRight()) == 0 &&
			   square.getYTopLeft().compareTo(existingShape.getYTopLeft()) == 0;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param existingShape
	 */
	private static void logOverlapError(BigDecimal x, BigDecimal y, Square existingShape) {
		String errorMessage = "";
		if(x == null && y== null)
			errorMessage = "Given coordinates are exactly overlapping "
				       + "with existing Square {" + existingShape.getXBottomLeft()	+ "," + existingShape.getYBottomLeft() + "}, "
				       + "{" + existingShape.getXTopRight() + ","	+ existingShape.getYTopRight() + "}";
		else
			errorMessage = "Overlap detected for coordinates {" + x + "," + y + "}  "
				       + "with {" + existingShape.getXBottomLeft()	+ "," + existingShape.getYBottomLeft() + "} "
				       + "and  {" + existingShape.getXTopRight() + ","	+ existingShape.getYTopRight() + "}";
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
		return (isBetween(x, existingSquare.getXBottomLeft(), existingSquare.getXBottomRight()) && 
				isEqualOrBetween(y,existingSquare.getYBottomLeft(), existingSquare.getYTopLeft())) ||
			   (isBetween(x, existingSquare.getXBottomLeft(), existingSquare.getXBottomRight()) && 
			    isBetween(y, existingSquare.getYBottomLeft(), existingSquare.getYTopLeft()));	  
	}

	private static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
		return (value.compareTo(min) == 1 && value.compareTo(max) == -1);
	}
	
	private static boolean isEqualOrBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
		return (value.compareTo(min) >= 0 && value.compareTo(max) <= 0);
	}
}
