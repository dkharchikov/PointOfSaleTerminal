package com.libra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PointOfSaleTerminal {

	private Map<String, Product> catalog;
	private Map<Product, Integer> cart;

	/**
	 * Sets set of items available to scan.
	 *
	 * @param pricing set of products with prices
	 * @return a reference to this object
	 * @throws IllegalStateException when trying set new pricing while cart is not empty.
	 */
	public PointOfSaleTerminal setPricing(Set<Product> pricing) throws IllegalStateException {
		if (cart != null && !cart.isEmpty())
			throw new IllegalStateException("Can't set pricing. Please checkout scanned items first.");

		this.catalog = new HashMap<>();
		for (Product p : pricing) {
			catalog.put(p.getProductCode(), p);
		}

		return this;
	}

	/**
	 * Scan item by product code and add it to cart.
	 *
	 * @param productCode representing product
	 * @return a reference to this object
	 * @throws IllegalStateException    if pricing wasn't set.
	 * @throws IllegalArgumentException if pricing doesn't have {@param productCode}
	 */
	public PointOfSaleTerminal scan(String productCode) throws IllegalStateException, IllegalArgumentException {
		if (catalog == null) throw new IllegalStateException("Please provide pricing before scanning.");

		if (cart == null)
			cart = new HashMap<>();

		if (catalog.containsKey(productCode)) {
			Product p = catalog.get(productCode);
			int count = cart.computeIfAbsent(p, (Product pr) -> 0);
			cart.put(p, ++count);
		} else {
			throw new IllegalArgumentException(String.format("Item with code: %s doesn't exist.", productCode));
		}

		return this;
	}

	/**
	 * Calculates total price for scanned items and clears out cart.
	 *
	 * @return total price for items scanned.
	 * @throws IllegalStateException if there were no items scanned.
	 */
	public BigDecimal calculateTotal() throws IllegalStateException {
		if (cart == null || cart.isEmpty()) throw new IllegalStateException("Please scan something first.");

		BigDecimal result = new BigDecimal(0);
		for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
			Product p = entry.getKey();
			int count = entry.getValue();
			if (p.hasValuePrice()) {
				int valueCount = count / p.getVolumePrice().getQuantity();
				int itemCount = valueCount > 0 ? count % p.getVolumePrice().getQuantity() : count;
				BigDecimal valueTotal = p.getVolumePrice().getPrice().multiply(new BigDecimal(valueCount));
				BigDecimal itemTotal = p.getItemPrice().multiply(new BigDecimal(itemCount));
				result = result.add(valueTotal).add(itemTotal);
			} else {
				BigDecimal itemTotal = p.getItemPrice().multiply(new BigDecimal(count));
				result = result.add(itemTotal);
			}
		}

		cart.clear();

		return result;
	}

}
