package warehouse.ch.bbv.efstathiosdimitriadis.rest.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import ch.bbv.efstathiosdimitriadis.rest.model.ProductCategory;
import ch.bbv.efstathiosdimitriadis.rest.model.Product;

public class ProductModelTest {

	@Test
	void createProduct() throws NoSuchFieldException, SecurityException {
		Product product = new Product("tomato", ProductCategory.FRUIT, 2019);
		assertEquals("tomato", product.getName());
		assertEquals(ProductCategory.FRUIT, product.getCategory());
		String.class.equals(product.getId().getClass());
		try {
			UUID.fromString(product.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	void productsWithSameFieldsAreEqual() {
		Product product = new Product("tomato", ProductCategory.FRUIT, 2019);
		Product differentProduct = new Product("tomato", ProductCategory.FRUIT, 2019);
		assertTrue(product.equals(differentProduct));
	}

	@Test
	void modifyNameReturnsNewProductInstance() {
		// Each instance has a unique ID courtesy of UUID so if the two
		// products have different IDs then a new instance was created
		Product product = new Product("falafel", ProductCategory.FOOD, 2019);
		Product modifiendProduct = product.modifyName("pastitsio");
		assertNotEquals(product.getId(), modifiendProduct.getId());
	}

	@Test
	void modifyNameCorrectlyModifiesName() {
		Product product = new Product("3", ProductCategory.GLASSWARE, 2019);
		Product modifiedProduct = product.modifyName("4");
		assertEquals("4", modifiedProduct.getName());
	}

	@Test
	void modifyNameLeavesOriginalObjectUnmodified() {
		Product product = new Product("Xiaomi Mi A1", ProductCategory.SMARTPHONE, 2019);
		product.modifyName("Samsung S10");
		assertEquals("Xiaomi Mi A1", product.getName());
	}

	@Test
	void modifyCategoryReturnsNewProductInstanceWithNewName() {
		Product product = new Product("Xiaomi Mi A1", ProductCategory.TABLET, 2019);
		Product modifiedProduct = product.modifyCategory(ProductCategory.SMARTPHONE);
		assertEquals(ProductCategory.SMARTPHONE, modifiedProduct.getCategory());
		assertNotEquals(product.getId(), modifiedProduct.getId());
	}

	@Test
	void modifyCategoryLeavesOriginalObjectUnmodified() {
		Product product = new Product("Xiaomi Mi A1", ProductCategory.TABLET, 2019);
		product.modifyCategory(ProductCategory.SMARTPHONE);
		assertEquals(ProductCategory.TABLET, product.getCategory());
	}
	
	@Test
	void modifyYearLeavesOriginalUnmodified() {
		Product product = new Product("Xiaomi Mi A3", ProductCategory.TABLET, 2017);
		product.modifyYear(2019);
		assertEquals(2017, product.getYear());
	}
}
