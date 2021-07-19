package com.pupil.handson.test.service.validator;

import java.util.List;

import com.pupil.handson.test.Entity.Shape;

/**
 * Base Shape Validator
 * 
 * @author PattathilS
 *
 */
public interface ShapeValidator {

	boolean isValid(Shape shape);

	boolean isOverlappingWithExistingShapes(Shape shape, List<Shape> existingShapes);

}
