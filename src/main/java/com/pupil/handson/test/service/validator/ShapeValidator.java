package com.pupil.handson.test.service.validator;

import java.util.List;

/**
 * Base Shape Validator
 * 
 * @author PattathilS
 *
 */
public interface ShapeValidator<T> {

	boolean isValid(T shape);

	boolean isOverlappingWithExistingShapes(T shape, List<T> existingShapes);

}
