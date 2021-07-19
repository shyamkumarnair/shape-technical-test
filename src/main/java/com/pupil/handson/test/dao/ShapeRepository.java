package com.pupil.handson.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pupil.handson.test.Entity.Shape;

/**
 * Shape Repository. Responsible for all CRUD operations on Shape
 * 
 * @author PattathilS
 *
 */
@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
}
