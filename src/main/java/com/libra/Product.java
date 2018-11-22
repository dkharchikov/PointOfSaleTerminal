package com.libra;

import java.math.BigDecimal;

public final class Product {
	private final String productCode;
	private final BigDecimal itemPrice;
	private final VolumePrice volumePrice;

	public Product(String productCode, BigDecimal itemPrice) {
		if (itemPrice.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Price should be positive.");
		this.productCode = productCode;
		this.itemPrice = itemPrice;
		this.volumePrice = null;
	}

	public Product(String productCode, BigDecimal itemPrice, VolumePrice volumePrice) {
		if (itemPrice.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Price should be positive");
		this.productCode = productCode;
		this.itemPrice = itemPrice;
		this.volumePrice = volumePrice;
	}

	public static Product of(String productCode, String itemPrice) {
		return new Product(productCode, new BigDecimal(itemPrice));
	}

	public static Product of(String productCode, String itemPrice, VolumePrice volumePrice) {
		return new Product(productCode, new BigDecimal(itemPrice), volumePrice);
	}

	public String getProductCode() {
		return productCode;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public VolumePrice getVolumePrice() {
		return volumePrice;
	}

	public boolean hasValuePrice() {
		return volumePrice != null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Product)) return false;
		Product product = (Product) o;
		return productCode.equals(product.productCode);
	}

	@Override
	public int hashCode() {
		return productCode.hashCode();
	}
}
