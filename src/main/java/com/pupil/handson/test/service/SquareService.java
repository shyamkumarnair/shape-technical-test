package com.pupil.handson.test.service;

import java.util.List;

import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;

/**
 * Base interface for Square shape services
 * 
 * @author PattathilS
 *
 */
public interface SquareService {

	List<Square> getAllSquares();

	Square addSquare(Square square) throws InvalidSquareException, SquareOverlapException;

}
