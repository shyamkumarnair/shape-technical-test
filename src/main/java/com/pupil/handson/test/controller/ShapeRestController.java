package com.pupil.handson.test.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pupil.handson.test.Entity.Shape;
import com.pupil.handson.test.service.ShapeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Rest controller for Shapes
 * 
 * @author PattathilS
 *
 */
@RestController
public class ShapeRestController {

	@Autowired
	private ShapeService shapeService;

	Logger logger = LogManager.getLogger(ShapeRestController.class);

	/**
	 * Get all shapes
	 *
	 * @return Shapes
	 */
	@Operation(summary = "Get all shapes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retrieved all shapes", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Shape.class)))),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content),
			@ApiResponse(responseCode = "400", description = "Did not find any Shapes", content = @Content) })
	@GetMapping(value = "/shapes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Shape> getAllShapes() {
		logger.trace("Getting all shapes");
		return shapeService.getAllShapes();
	}

}
