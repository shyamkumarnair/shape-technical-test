package com.pupil.handson.test.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum for shape types
 * 
 * @author PattathilS
 *
 */
public enum ShapeType {
	CIRCLE("Circle"), 
	SEMI_CIRCLE("Semi_circle"), 
	TRIANGLE("Triangle"), 
	SQUARE("Square"), 
	RECTANGLE("Rectangle"),
	PENTAGON("Pentagon"), 
	HEXAGON("Hexagon");

	private String type;

	ShapeType(String type) {
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonCreator
	public static ShapeType getShapeTypeFromValue(String value) {
		for (ShapeType dep : ShapeType.values()) {
			if (dep.getType().equalsIgnoreCase(value)) {
				return dep;
			}
		}
		return null;
	}
}
