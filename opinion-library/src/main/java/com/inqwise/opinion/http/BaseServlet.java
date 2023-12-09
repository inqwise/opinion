/**
 * 
 */
package com.inqwise.opinion.opinion.http;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basil
 *
 */
public class BaseServlet extends HttpServlet {

	public void init() {
		//System.out.println("Init");
	}
	
	public void destroy() {
		//System.out.println("Destroy");
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("OK");
	}
}
