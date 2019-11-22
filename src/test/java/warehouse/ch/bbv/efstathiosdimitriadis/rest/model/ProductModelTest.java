package warehouse.ch.bbv.efstathiosdimitriadis.rest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

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
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	void productsWithSameFieldsAreEqual() {
		Product product = new Product("tomato", "fruit");
		Product differentProduct = new Product("tomato", "fruit");
		assertTrue(product.equals(differentProduct));
	}

	@Test
	void modifyNameReturnsNewProductInstance() {
		// Each instance has a unique ID courtesy of UUID so if the two
		// products have different IDs then a new instance was created
		Product product = new Product("falafel", "food");
		Product modifiendProduct = product.modifyName("pastitsio");
		assertNotEquals(product.getId(), modifiendProduct.getId());
	}

	@Test
	void modifyNameCorrectlyModifiesName() {
		Product product = new Product("3", "455.33");
		Product modifiedProduct = product.modifyName("4");
		assertEquals("4", modifiedProduct.getName());
	}

	@Test
	void modifyNameLeavesOriginalObjectUnmodified() {
		Product product = new Product("Xiaomi Mi A1", "smartphone");
		product.modifyName("Samsung S10");
		assertEquals("Xiaomi Mi A1", product.getName());
	}

	@Test
	void modifyCategoryReturnsNewProductInstanceWithNewName() {
		Product product = new Product("Xiaomi Mi A1", "smartphone");
		Product modifiedProduct = product.modifyCategory("cellphone");
		assertEquals("cellphone", modifiedProduct.getCategory());
		assertNotEquals(product.getId(), modifiedProduct.getId());
	}

	@Test
	void modifyCategoryLeavesOriginalObjectUnmodified() {
		Product product = new Product("Xiaomi Mi A1", "smartphone");
		product.modifyCategory("cellphone");
		assertEquals("smartphone", product.getCategory());
	}
}
