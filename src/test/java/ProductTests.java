import com.libra.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTests {

	@Test
	public void invalidItemPriceTest() {
		assertThrows(IllegalArgumentException.class, () -> Product.of("A", "-2.34"), "Throw if price is invalid.");
	}
}
