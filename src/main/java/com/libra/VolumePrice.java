package com.libra;

import java.math.BigDecimal;

public final class VolumePrice {

	private final int quantity;
	private final BigDecimal price;

	public VolumePrice(int quantity, BigDecimal price) {
		if (quantity < 2) throw new IllegalArgumentException("Quantity should be greater than 1.");
		if (price.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Price should be positive");
		this.quantity = quantity;
		this.price = price;
	}

	public static VolumePrice of(int count, String price) {
		return new VolumePrice(count, new BigDecimal(price));
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
