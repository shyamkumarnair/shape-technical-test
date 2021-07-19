package com.pupil.handson.test.startup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.pupil.handson.test.ShapeApplication;
import com.pupil.handson.test.dao.ShapeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShapeApplication.class)
@Sql("/shape.sql")
class StartupIntegrationTest {

	@Autowired
	private ShapeRepository shapeRepository;

	@Test
	@Sql(scripts = { "/import_shapes.sql" })
	public void testLoadDataForTestCase() {
		assertEquals(3, shapeRepository.findAll().size());
	}
}
