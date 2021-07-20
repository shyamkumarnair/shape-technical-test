package com.pupil.handson.test.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.dao.SquareRepository;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;
import com.pupil.handson.test.service.validator.SquareValidator;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SquareIntegrationTest {

	@Autowired
	private SquareRepository squareRepository;

	private SquareValidator squareValidator;
	
	private List<Square> existingSquares;


	@BeforeEach
	public void loadData() {
		squareValidator = new SquareValidator();
		Square square = getSquare("Square1", "0.00", "0.00", "0.00", "2.00", "2.00", "0.00", "2.00", "2.00");
		squareRepository.save(square);
		square = getSquare("Square2", "2.00", "2.00", "2.00", "4.00", "4.00", "2.00", "4.00", "4.00");
		squareRepository.save(square);
		square = getSquare("Square3", "4.00", "4.00", "4.00", "6.00", "6.00", "4.00", "6.00", "6.00");
		squareRepository.save(square);
		existingSquares = squareRepository.findByType(ShapeType.SQUARE);
	}

	@Test
	public void whenValidSquareIsSaved_thenCorrectNumberOfSquaresAreRetured() {
		List<Square> squares = squareRepository.findByType(ShapeType.SQUARE);
		assertEquals(3, squares.size());
	}
	
	@Test
	public void whenValidSquareIsProvided_thenSquareisValidatedasValid() {
		Square square = getSquare("ValidSquare1", "10.00", "10.00", "10.00", "15.00", "15.00", "10.00", "15.00", "15.00");
		assertTrue(squareValidator.isValid(square));
	}
	
	@Test
	public void whenNonOverlappingSquareIsProvided_thenSquareisValidatedasValid() {
		Square square = getSquare("ValidSquare1", "10.00", "10.00", "10.00", "15.00", "15.00", "10.00", "15.00", "15.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square, existingSquares));
	}

	@Test
	public void whenInvalidSquareIsProvided_thenSquareisValidatedasInvalid() {
		Square square = getSquare("InvalidSquare1", "0.00", "0.00", "0.00", "4.00", "5.00", "0.00", "5.00", "4.00");
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareValidator.isValid(square), "InvalidSquareException");
		assertTrue(thrown.getMessage().contains("InvalidSquareException"));
	}
	
	@Test
	public void whenNotSquareIsProvided_thenShapeisValidatedasInvalid() {
		Square square = getSquare("InvalidSquare1", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00");
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareValidator.isValid(square), "InvalidSquareException");
		assertTrue(thrown.getMessage().contains("InvalidSquareException"));
	}
	
	@Test
	public void whenIncorrectCoordinatesProvided_thenShapeisValidatedasInvalid() {
		Square square = getSquare("InvalidSquare1", "5.00", "5.00", "5.00", "0.00", "10.00", "0.00", "10.00", "5.00");
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareValidator.isValid(square), "InvalidSquareException");
		assertTrue(thrown.getMessage().contains("InvalidSquareException"));
	}

	@Test
	public void whenSquareIsExactlyOverLapped_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "2.00", "2.00", "2.00", "4.00", "4.00", "2.00", "4.00", "4.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsPartiallyOverLapped_OnXAxis_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "1.50", "2.00", "1.50", "4.00", "3.50", "2.00", "3.50", "4.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsPartiallyOverLapped_OnYAxis_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "2.00", "1.90", "2.00", "3.90", "4.00", "1.90", "4.00", "3.90");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsOverlappedOnBottomLeft_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "-1.80", "-1.90", "-1.80", "1.90", "1.80", "-1.90", "1.80", "1.90");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsOverlappedOnTopLeft_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "-1.80", "1.90", "-1.80", "3.90", "1.80", "1.90", "1.80", "3.90");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsOverlappedOnTopRight_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "1.80", "-1.90", "1.80", "1.90", "3.80", "-1.90", "3.80", "1.90");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsOverlappedOnBottomRight_thenInvalidSquareExceptionThrown() {
		Square square = getSquare("Square4", "1.80", "1.90", "1.80", "3.90", "3.80", "1.90", "3.80", "3.90");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square, existingSquares),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void whenSquareIsAddedOnBottomLeft_thenNoInvalidSquareExceptionIsThrown() {
		Square square = getSquare("Square4", "-2.00", "-2.00", "-2.00", "0.00", "0.00", "-2.00", "0.00", "0.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square, existingSquares));

		square = getSquare("Square4", "-2.00", "10.00", "-2.00", "12.00", "0.00", "10.00", "0.00", "12.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square, existingSquares));

		square = getSquare("Square4", "10.00", "-2.00", "10.00", "0.00", "12.00", "-2.00", "12.00", "0.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square, existingSquares));

		square = getSquare("Square4", "10.00", "10.00", "10.00", "12.00", "12.00", "10.00", "12.00", "12.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square, existingSquares));
	}

	private Square getSquare(String name, String xBL, String yBL, String xTL, String yTL, String xBR, String yBR,
			String xTR, String yTR) {
		Square square = new Square();
		square.setName(name);
		square.setDescription("Test square description");
		square.setType(ShapeType.SQUARE);
		square.setXBottomLeft(new BigDecimal(xBL));
		square.setYBottomLeft(new BigDecimal(yBL));
		square.setXBottomRight(new BigDecimal(xBR));
		square.setYBottomRight(new BigDecimal(yBR));
		square.setXTopLeft(new BigDecimal(xTL));
		square.setYTopLeft(new BigDecimal(yTL));
		square.setXTopRight(new BigDecimal(xTR));
		square.setYTopRight(new BigDecimal(yTR));
		return square;
	}
}
