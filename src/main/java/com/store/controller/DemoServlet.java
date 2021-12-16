package com.store.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

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

			int pageNumber = 1;
			if (!StringUtils.isEmpty(pageNum)) {
				pageNumber = Integer.parseInt(pageNum);
			}
			PageBean pageBean = demoService.findInfosByPage(pageNumber, StoreConstants.PAGE_SIZE);
			
			pageBean.setUrl("demoAction?action=list");
			req.setAttribute("page", pageBean);
			req.getRequestDispatcher("/jsp/demo.jsp").forward(req, resp);
		}
			
	}

	
	
	
}
