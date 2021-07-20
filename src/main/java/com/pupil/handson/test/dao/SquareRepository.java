package com.pupil.handson.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pupil.handson.test.Entity.ShapeType;
import com.pupil.handson.test.Entity.Square;

/**
 * Square Repository. Responsible for all CRUD operations on Square
 * 
 * @author PattathilS
 *
 */
@Repository
public interface SquareRepository extends JpaRepository<Square, Long> {
	List<Square> findByType(ShapeType type);
}
