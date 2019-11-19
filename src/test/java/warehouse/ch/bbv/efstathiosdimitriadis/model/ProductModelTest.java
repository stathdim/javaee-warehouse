package warehouse.ch.bbv.efstathiosdimitriadis.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductModelTest {
	
	@Test
	void createProduct() {
		Product product = new Product("tomato", "fruit");
		assertEquals("tomato", product.getName());
		assertEquals("fruit", product.getCategory());
		assertEquals(String.class, product.getId().getClass());
	}
}
