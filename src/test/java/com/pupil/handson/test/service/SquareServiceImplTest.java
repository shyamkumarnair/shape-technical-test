package com.pupil.handson.test.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.dao.SquareRepository;
import com.pupil.handson.test.service.exception.InvalidSquareException;
import com.pupil.handson.test.service.exception.SquareOverlapException;
import com.pupil.handson.test.service.validator.SquareValidator;

@ExtendWith(MockitoExtension.class)
class SquareServiceImplTest {

	@InjectMocks
	private SquareServiceImpl squareService;

	@Mock
	private SquareRepository squareRepository;
	@Mock
	private SquareValidator squareValidator;
	@Mock
	private Square square;
	@Mock
	private List<Shape> shapes;

	@Test
	public void test_getAllSquares() {
		List<Shape> squares = new ArrayList<Shape>();
		when(squareRepository.findByType(ShapeType.SQUARE)).thenReturn(squares);
		List<Shape> resultList = squareService.getAllSquares();
		ArgumentCaptor<ShapeType> shapeTypeArgument = ArgumentCaptor.forClass(ShapeType.class);
		verify(squareRepository).findByType(shapeTypeArgument.capture());
		assertEquals(ShapeType.SQUARE, shapeTypeArgument.getValue());
		assertEquals(squares, resultList);
	}

	@Test
	public void test_addSquare_validSquare() {
		Square square = getSquare();
		when(squareValidator.isValid(square)).thenReturn(true);
		squareService.addSquare(square);
		ArgumentCaptor<Square> squareArgument = ArgumentCaptor.forClass(Square.class);
		verify(squareRepository).save(squareArgument.capture());
		assertEquals(square, squareArgument.getValue());
	}

	@Test
	public void test_addSquare_invalidSquare() {
		when(squareValidator.isValid(square)).thenReturn(false);
		InvalidSquareException thrown = assertThrows(InvalidSquareException.class,
				() -> squareService.addSquare(square), "Invalid Square Exception");
		assertTrue(thrown.getMessage().contains("Invalid Square Exception"));
	}

	@Test
	public void test_addSquare_overlappingSquare() {
		when(squareRepository.findByType(ShapeType.SQUARE)).thenReturn(shapes);
		when(squareValidator.isValid(square)).thenReturn(true);
		when(squareValidator.isOverlappingWithExistingShapes(square, shapes)).thenReturn(true);
		SquareOverlapException thrown = assertThrows(SquareOverlapException.class,
				() -> squareService.addSquare(square), "Square Overlap Exception");
		assertTrue(thrown.getMessage().contains("Square Overlap Exception"));
	}

	private Square getSquare() {
		Square square = new Square();
		square.setName("TestSquare");
		square.setDescription("Test square description");
		square.setType(ShapeType.SQUARE);
		square.setXBottomLeft(new BigDecimal(0.00));
		square.setYBottomLeft(new BigDecimal(0.00));
		square.setXBottomRight(new BigDecimal(5.00));
		square.setYBottomRight(new BigDecimal(0.00));
		square.setXTopLeft(new BigDecimal(0.00));
		square.setYTopLeft(new BigDecimal(5.00));
		square.setXTopRight(new BigDecimal(5.00));
		square.setYTopRight(new BigDecimal(5.00));
		return square;
	}

}
