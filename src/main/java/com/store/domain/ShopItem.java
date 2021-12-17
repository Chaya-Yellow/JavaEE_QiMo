package com.store.domain;

/**
 * 购物条目
 * @author 25574
 *
 */
public class ShopItem {
	/**
	 * 商品类
	 */
	private Product product;
	/**
	 * 购买数量
	 */
	private int shopAmount = 0;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getShopAmount() {
		return shopAmount;
	}
	public void setShopAmount(int shopAmount) {
		this.shopAmount = shopAmount;
	}
	
	
	
}
