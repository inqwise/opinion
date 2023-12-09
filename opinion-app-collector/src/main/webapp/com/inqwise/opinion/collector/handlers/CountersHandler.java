package com.inqwise.opinion.collector.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class CountersHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1018114915427989524L;
	private static final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/javascript;charset=UTF-8");
		ServletOutputStream out = resp.getOutputStream();
		
		String id = req.getParameter("id");
		int cnt;
		if(null != id && StringUtils.isNotEmpty(id)){
			AtomicInteger counter;
			if(null == (counter = counters.get(id))){
				AtomicInteger originCounter = counters.putIfAbsent(id, counter = new AtomicInteger(1));
				if(null != originCounter){
					counter = originCounter;
				}
			}
			
			cnt = counter.getAndIncrement();
			out.print("popup.pop("+ cnt +");");
			out.close();
		}
		else
		{
			resp.setStatus(404);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(404);
	}
}
