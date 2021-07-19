package com.pupil.handson.test.util;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.pupil.handson.test.Entity.ShapeType;

/**
 * Converted for ShapeType Enum
 * 
 * @author PattathilS
 *
 */
@Converter(autoApply = true)
public class ShapeConvertor implements AttributeConverter<ShapeType, String> {

	@Override
	public String convertToDatabaseColumn(ShapeType shapeType) {
		if (shapeType == null) {
			return null;
		}
		return shapeType.getType();
	}

	@Override
	public ShapeType convertToEntityAttribute(String shapeType) {
		if (shapeType == null) {
			return null;
		}

		return Stream.of(ShapeType.values()).filter(c -> c.getType().equals(shapeType)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
