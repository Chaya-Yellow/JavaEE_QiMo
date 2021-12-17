package com.store.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.store.domain.Product;
import com.store.domain.ShopItem;
import com.store.service.DemoService;
import com.store.service.DemoServiceImpl;
import com.store.utils.PageBean;
import com.store.utils.StoreConstants;

public class DemoServlet extends HttpServlet{
	private DemoService demoService;

	@Override
	public void init() throws ServletException {
		demoService = new DemoServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("register".equals(action)) {
			String userId = req.getParameter("user_id");
			String userName = req.getParameter("user_name");
			
			int amt = demoService.createUser(userId, userName);
			String message = "操作失败";
			if (amt > 0) {
				message = "操作成功";
			}
			req.setAttribute("msg", message);
			req.getRequestDispatcher("/jsp/demo.jsp").forward(req, resp);
		}
		
		else if ("list".equals(action)) {
			String pageNum = req.getParameter("pageNum");
			String productName = req.getParameter("product_name");

			int pageNumber = 1;
			if (!StringUtils.isEmpty(pageNum)) {
				pageNumber = Integer.parseInt(pageNum);
			}
			PageBean pageBean = demoService.findInfosByPage(pageNumber, StoreConstants.PAGE_SIZE, productName);
			
			pageBean.setUrl("demoAction?action=list");
			req.setAttribute("page", pageBean);
			req.getRequestDispatcher("/jsp/demo.jsp").forward(req, resp);
		} else if ("create_order".equals(action)) {
			String[] shopChks = req.getParameterValues("shopChk");
			String[] shopNums = req.getParameterValues("shopNum");
			
			Long uid = 1L;//从session获取
			int amt = demoService.createOrder(shopChks, shopNums, uid);
			if (amt > 0) {
				req.setAttribute("order_msg", "订单创建成功");
			} else {
				req.setAttribute("order_msg", "订单创建失败");
			}
			req.getRequestDispatcher("/jsp/demo.jsp").forward(req, resp);
			
		} else if ("my_shopcart".equals(action)) {
			HttpSession session = req.getSession();
			//从session获取购物车信息
			Map<String, ShopItem> carts = (Map<String, ShopItem>) session.getAttribute("shop_carts");
			
			if (carts == null) {
				carts = new HashMap<String, ShopItem>();
				
				List<Map<String, Object>> products  = demoService.findProducts();

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
			req.getRequestDispatcher("/jsp/demo.jsp").forward(req, resp);
		}
			
	}

	
	
	
}
