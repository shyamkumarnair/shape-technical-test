package com.pupil.handson.test.Entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import org.springframework.lang.NonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity to persist Squares
 * 
 * @author PattathilS
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@DiscriminatorColumn(name = "entityType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "SQUARE")
@Entity
public class Square extends Shape {

	@Schema(description = "Top left X or Horizontal coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "xTopLeft")
	private BigDecimal xTopLeft;

	@Schema(description = "Top left Y or Vertical coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "yTopLeft")
	private BigDecimal yTopLeft;

	@Schema(description = "Top Right X or Horizontal coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "xTopRight")
	private BigDecimal xTopRight;

	@Schema(description = "Top Right Y or Vertical coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "yTopRight")
	private BigDecimal yTopRight;

	@Schema(description = "Bottom left X or Horizontal coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "xBottomLeft")
	private BigDecimal xBottomLeft;

	@Schema(description = "Bottom left Y or Vertical coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "yBottomLeft")
	private BigDecimal yBottomLeft;

	@Schema(description = "Bottom Right X or Horizontal coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "xBottomRight")
	private BigDecimal xBottomRight;

	@Schema(description = "Bottom Right Y or Vertical coordinate of the square", example = "5.00", required = true)
	@NonNull
	@Digits(integer = 10, fraction = 2)
	@Column(name = "yBottomRight")
	private BigDecimal yBottomRight;

}
