import com.libra.VolumePrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class VolumePriceTests {

	@Test
	public void qtyGreaterThan1Test() {
		assertThrows(IllegalArgumentException.class, () -> VolumePrice.of(1, "1.25"), "Qty should be greater than 1.");
	}

	@Test
	public void invalidPrice() {
		assertThrows(IllegalArgumentException.class, () -> VolumePrice.of(2, "-2.25"), "throw if price is invalid.");
	}
}
