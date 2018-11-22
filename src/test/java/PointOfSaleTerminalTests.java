import com.libra.PointOfSaleTerminal;
import com.libra.Product;
import com.libra.VolumePrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PointOfSaleTerminalTests {

	@Test
	public void test() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		terminal.scan("A")
				.scan("A")
				.scan("A");
		assertEquals(new BigDecimal(3.00).setScale(2), terminal.calculateTotal());
	}

	@Test
	public void ABCDABATest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		BigDecimal expectedResult = new BigDecimal(13.25);
		terminal.scan("A")
				.scan("B")
				.scan("C")
				.scan("D")
				.scan("A")
				.scan("B")
				.scan("A");
		assertEquals(expectedResult, terminal.calculateTotal(), "ABCDABA; the total price should be $13.25.");
	}

	@Test
	public void CCCCCCCTest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		BigDecimal expectedResult = new BigDecimal(6).setScale(2);
		terminal.scan("C")
				.scan("C")
				.scan("C")
				.scan("C")
				.scan("C")
				.scan("C")
				.scan("C");
		assertEquals(expectedResult, terminal.calculateTotal(), "CCCCCCC; the total price should be $6.00.");
	}

	@Test
	public void ABCDTest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		BigDecimal expectedResult = new BigDecimal(7.25);
		terminal.scan("A")
				.scan("B")
				.scan("C")
				.scan("D");
		assertEquals(expectedResult, terminal.calculateTotal(), "ABCD; the total price should be $7.25");
	}

	@Test
	public void missingPricingTest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		assertThrows(IllegalStateException.class, () -> terminal.scan("A"), "Throw exception if scanning before pricing is set.");
	}

	@Test
	public void emptyCartTest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		assertThrows(IllegalStateException.class, terminal :: calculateTotal, "Throw exception if cart is empty when calculating total.");
	}

	@Test
	public void missingProductTest() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		assertThrows(IllegalArgumentException.class, () -> terminal.scan("F"), "Throw exception if product code is unknown.");
	}

	@Test
	public void throwWhenSetPricingWhileCartIsNotEmpty() {
		PointOfSaleTerminal terminal = new PointOfSaleTerminal();
		setPricing(terminal);
		terminal.scan("A");
		assertThrows(IllegalStateException.class, () -> setPricing(terminal), "Throw exception if trying to set new pricing while cart is not checked out.");
	}

	static void setPricing(PointOfSaleTerminal terminal) {
		Product A = Product.of("A", "1.25", VolumePrice.of(3, "3.00"));
		Product B = Product.of("B", "4.25");
		Product C = Product.of("C", "1.00", VolumePrice.of(6, "5"));
		Product D = Product.of("D", "0.75");
		Set<Product> pricing = new HashSet<>();
		pricing.add(A);
		pricing.add(B);
		pricing.add(C);
		pricing.add(D);

		terminal.setPricing(pricing);
	}

}
