package warehouse.ch.bbv.efstathiosdimitriadis.rest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductModelTest {

	@Test
	void createProduct(TestReporter testReporter) throws NoSuchFieldException, SecurityException {
		Product product = new Product("tomato", "fruit");
		assertEquals("tomato", product.getName());
		assertEquals("fruit", product.getCategory());
		String.class.equals(product.getId().getClass());
		try {
			UUID.fromString(product.getId());
			}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
