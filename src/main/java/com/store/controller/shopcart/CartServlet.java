package com.store.controller.shopcart;


import com.store.domain.Product;
import com.store.domain.ShopItem;
import com.store.service.DemoService;
import com.store.service.DemoServiceImpl;
import com.store.service.shopcart.CartService;
import com.store.service.shopcart.CartServiceImpl;
import com.store.utils.PageBean;
import com.store.utils.StoreConstants;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServlet extends HttpServlet{
	private CartService cartService;

	@Override
	public void init() throws ServletException {
		cartService = (CartService) new CartServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取页面传过来的参数
		String action = req.getParameter("action"); //操作指令
		String productId = req.getParameter("product_id");//商品编号
		System.out.println("action=>"+action+";productId=>"+productId);
		int clickCount=0;
		clickCount++;
		System.out.println("clickCount =>"+clickCount);
		HttpSession session = req.getSession();
		//从session获取购物车信息
		Map<String, ShopItem> carts = (Map<String, ShopItem>) session.getAttribute("shop_carts");

		if (carts == null) {
			carts = new HashMap<String, ShopItem>();

			List<Map<String, Object>> products  = CartService.findProducts();

			if (products != null) {
				for (Map<String, Object> productMap : products) {

					ShopItem shopItem = new ShopItem();
					Product product = new Product();
					product.setPid(Long.parseLong(productMap.get("pid").toString()));
					product.setPname(productMap.get("pname").toString());
					product.setpImage(productMap.get("pimage").toString());
					product.setMarketPrice(Double.parseDouble(productMap.get("market_price").toString()));
					product.setShopPrice(Double.parseDouble(productMap.get("shop_price").toString()));
					product.setCid(Long.parseLong(productMap.get("cid").toString()));
					product.setpDesc(productMap.get("pdesc").toString());
					product.setpState(productMap.get("pstate").toString());

					shopItem.setProduct(product);
					shopItem.setShopAmount(1);

					carts.put(product.getPid().toString(), shopItem);
				}
			}

			session.setAttribute("shopCartList", carts);
		}

		//添加商品到购物车
		if(action != null && action.equals("add")) {
			if (carts == null) {
				carts = new HashMap<String, ShopItem>();
				session.setAttribute("shop_carts", carts);
			}

			addCarts(productId,"测试商品"+productId, carts);
		}
		//从购物车扣减或移除商品
		else if(action != null && action.equals("delete")) {
			removeCarts(productId, carts);
		}
		//查看购物车内容
		else if(action != null && action.equals("query")) {
			queryCarts(carts);
		}

		req.getRequestDispatcher("/cart.jsp").forward(req, resp);
	}
	/**
	 * 添加商品到购物车
	 */
	private void addCarts(String productId, String productName, Map<String, ShopItem> carts) {
		//根据商品编号获取购物车条目
		ShopItem shopItem = carts.get(productId);
		// 如果商品条目为空
		if (shopItem == null) {
			shopItem = new ShopItem();
			Product product = new Product();
			product.setPid(Long.valueOf(productId));
			product.setPname(productName);
			product.setShopPrice(12.0D);
			shopItem.setProduct(product);
			carts.put(productId, shopItem);
		}
		//每点击一次，添加1份购物数量
		shopItem.setShopAmount(shopItem.getShopAmount()+1);
	}
	/**
	 * 从购物车扣减或移除商品
	 * @param productId
	 * @param carts
	 */
	private void removeCarts(String productId, Map<String, ShopItem> carts) {
		if (carts != null) {
			// 从map中获取购物条目
			ShopItem shopItem = carts.get(productId);
			if (shopItem != null) {
				//如果购物条目存在，则扣减1
				shopItem.setShopAmount(shopItem.getShopAmount()-1);
				//如果购买数量为0，则从购物车把商品移除
				if (shopItem.getShopAmount() <=0) {
					carts.remove(productId);
				}
			}
		}
	}
	/**
	 * 查看购物车内容
	 * @param carts
	 */
	private void queryCarts(Map<String, ShopItem> carts) {
		if (carts != null) {
			System.out.println("购物车的内容有：");
			//费用合计
			double totalPrice = 0d;
			for (Map.Entry<String, ShopItem> entry : carts.entrySet()) {
				totalPrice += entry.getValue().getProduct().getShopPrice()*entry.getValue().getShopAmount();
				System.out.println("商品编号："+entry.getKey() + ";商品名称："+entry.getValue().getProduct().getShopPrice() + "; 单价："+entry.getValue().getProduct().getShopPrice() + ";购买数量："+entry.getValue().getShopAmount());
			}
			System.out.println("费用合计："+ totalPrice);
		}

		}


	}


