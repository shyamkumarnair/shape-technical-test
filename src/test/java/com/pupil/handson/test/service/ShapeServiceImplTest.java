package com.pupil.handson.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.dao.ShapeRepository;

@ExtendWith(MockitoExtension.class)
class ShapeServiceImplTest {

	@InjectMocks
	private ShapeServiceImpl shapeService;

	@Mock
	private ShapeRepository shapeRepository;

	@Test
	public void test_getAllShapes() {
		List<Shape> shapes = new ArrayList<Shape>();
		when(shapeRepository.findAll()).thenReturn(shapes);
		List<Shape> resultList = shapeService.getAllShapes();
		verify(shapeRepository).findAll();
		assertEquals(shapes, resultList);
	}
}
