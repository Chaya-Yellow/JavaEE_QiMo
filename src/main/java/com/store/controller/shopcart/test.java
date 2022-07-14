package com.store.controller.shopcart;

import com.store.domain.ShopItem;
import com.store.service.shopcart.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/jsp/shopcart/test")
public class test extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        HashMap<String, ShopItem> carts;
        carts = new HashMap<String, ShopItem>();
        session.setAttribute("shopCartList",carts);
        System.out.println("1");
        try {
            carts = CartService.findProducts();


            System.out.println(carts);
            session.setAttribute("shopCartList", carts);


            HashMap<String, ShopItem> cart = (HashMap<String, ShopItem>) session.getAttribute("shopCartList");
            for (HashMap.Entry<String, ShopItem> entry : cart.entrySet()) {
                System.out.println(entry.getValue().getShopAmount());
                System.out.println(entry.getValue().getProduct().getShopPrice());

            }


            System.out.println("2");
            req.getRequestDispatcher("test.jsp").forward(req, resp);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
