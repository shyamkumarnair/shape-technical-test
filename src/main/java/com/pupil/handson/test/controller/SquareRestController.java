package com.pupil.handson.test.controller;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pupil.handson.test.Entity.Square;
import com.pupil.handson.test.service.SquareService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Square Rest Controller
 * 
 * @author PattathilS
 *
 */
@RestController
@RequestMapping("/shapes")
public class SquareRestController {

	Logger logger = LogManager.getLogger(SquareRestController.class);

	@Autowired
	private SquareService squareService;

	/**
	 * Create new square
	 *
	 * @param square
	 * @return Square ResponseEntity Object
	 */
	@Operation(summary = "Create new Square")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully created new Square", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Square.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Invalid Square or Overlapping with existing Squares", content = @Content) })
	@PostMapping(path = "/square", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createSquare(@Parameter(description = "square to be created") @NonNull @RequestBody Square square) {
		Square savedSquare = squareService.addSquare(square);
		logger.info("Created square with Id" + savedSquare.getId());
		// redirect to list all shapes through HATEOAS
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/shapes").buildAndExpand().toUri();
		return ResponseEntity.created(location).build();
	}
}
