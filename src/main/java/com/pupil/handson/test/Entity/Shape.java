package com.pupil.handson.test.Entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base Shape Entity.
 * 
 * @author PattathilS
 *
 */

@NoArgsConstructor
@Data
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="entityType",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="ABSTRACT-SHAPE")
@Entity
public class Shape {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private long id;
	
	@Schema(description = "Name of the Shape.", 
	            example = "Drywall Square", required = true)
	@NotBlank
	@Size(min=0, max=20)
	@Column(name = "name", unique = true)
	private String name;
	
	@Schema(description = "Shape type.", 
            example = "SQUARE", required = true)
	@Column(name = "type")
	private ShapeType type;
	
	@Schema(description = "Description about the shape.", 
            example = "Drywall Square", required = false)
	@Size(min=0, max=100)
	@Column(name = "desc")
	private String description;
}
