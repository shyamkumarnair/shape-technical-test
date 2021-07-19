package com.pupil.handson.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.service.ShapeService;

@ExtendWith(MockitoExtension.class)
class ShapeRestControllerTest {

	@InjectMocks
	private ShapeRestController shapeRestController;

	@Mock
	private ShapeService shapeService;

	@Test
	public void test_getAllShapes() {
		// given
		Shape shape1 = new Shape();
		Shape shape2 = new Shape();
		Shape shape3 = new Shape();
		List<Shape> shapes = new ArrayList<Shape>();
		shapes.add(shape1);
		shapes.add(shape2);
		shapes.add(shape3);
		when(shapeService.getAllShapes()).thenReturn(shapes);

		// when
		List<Shape> resultList = shapeRestController.getAllShapes();

		// then
		assertThat(resultList.size()).isEqualTo(3);
	}

}
