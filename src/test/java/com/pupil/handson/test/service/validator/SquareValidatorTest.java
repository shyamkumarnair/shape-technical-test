package com.pupil.handson.test.service.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;

@ExtendWith(MockitoExtension.class)
class SquareValidatorTest {

	@InjectMocks
	private SquareValidator squareValidator;

	@Test
	public void test_isValid_true() {
		Square square = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		assertTrue(squareValidator.isValid(square));
	}

	@Test
	public void test_isValid_false() {
		Square square = getSquare("InvalidSquare1", "0.00", "0.00", "0.00", "4.00", "5.00", "0.00", "5.00", "4.00");
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareValidator.isValid(square), "InvalidSquareException");
		assertTrue(thrown.getMessage().contains("InvalidSquareException"));
	}
	
	@Test
	public void test_isValid_false_point() {
		Square square = getSquare("InvalidSquare1", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00", "5.00");
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareValidator.isValid(square), "InvalidSquareException");
		assertTrue(thrown.getMessage().contains("InvalidSquareException"));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseOne() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseTwo() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseThree() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseFour() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseFive() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "5.00", "0.00", "9.00", "4.00", "5.00", "4.00", "9.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_false_caseSix() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "7.00", "0.00", "7.00", "5.00", "12.00", "0.00", "12.00", "5.00");
		assertFalse(squareValidator.isOverlappingWithExistingShapes(square2, existingShapes));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_true_caseOne() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "7.00", "7.00", "0.00", "7.00", "7.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "6.00", "0.00", "6.00", "7.00", "13.00", "0.00", "13.00", "7.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square2, existingShapes),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_true_caseTwo() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "0.00", "0.00", "0.00", "5.00", "5.00", "0.00", "5.00", "5.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square2, existingShapes),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_true_caseThree() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "3.00", "0.00", "8.00", "5.00", "3.00", "5.00", "8.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "3.00", "0.00", "3.00", "5.00", "8.00", "0.00", "8.00", "5.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square2, existingShapes),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}

	@Test
	public void test_isOverlappingWithExistingShapes_true_caseFour() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "4.00", "0.00", "10.00", "6.00", "4.00", "6.00", "10.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "5.00", "0.00", "5.00", "5.00", "10.00", "0.00", "10.00", "5.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square2, existingShapes),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
	}
	
	@Test
	public void test_isOverlappingWithExistingShapes_true_caseFive() {
		List<Shape> existingShapes = new ArrayList<>();
		Square square1 = getSquare("ValidSquare1", "0.00", "0.00", "0.00", "10.00", "10.00", "0.00", "10.00", "10.00");
		existingShapes.add(square1);
		Square square2 = getSquare("ValidSquare2", "3.00", "3.00", "3.00", "8.00", "8.00", "3.00", "8.00", "8.00");
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareValidator.isOverlappingWithExistingShapes(square2, existingShapes),
				"SquareOverlapException");
		assertTrue(thrown.getMessage().contains("SquareOverlapException"));
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
