package com.pupil.handson.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.service.SquareService;

@ExtendWith(MockitoExtension.class)
class SquareRestControllerTest {

	@InjectMocks
	private SquareRestController squareRestController;

	@Mock
	private SquareService squareService;

	@Mock
	private Square square;

	@Test
	public void test_createSquare() {
		//given
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(square.getId()).thenReturn(new Long(1));
		when(squareService.addSquare(square)).thenReturn(square);
				
		//when
		ResponseEntity<Object> responseEntity = squareRestController.createSquare(square);
		ArgumentCaptor<Square> squareArgumentCaptor = ArgumentCaptor.forClass(Square.class);
		verify(squareService).addSquare(squareArgumentCaptor.capture());
		
		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/shapes");
		assertEquals(square, squareArgumentCaptor.getValue());
	}
}
