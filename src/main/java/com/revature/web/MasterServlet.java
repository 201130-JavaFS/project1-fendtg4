package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.controllers.EmployeeController;
import com.revature.controllers.LoginController;
import com.revature.controllers.ManagerController;


public class MasterServlet extends HttpServlet {

	private static final Logger log = LogManager.getLogger(MasterServlet.class);
	private LoginController lc = new LoginController();
	private EmployeeController ec = new EmployeeController();
	private ManagerController mc = new ManagerController();
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		//log.debug("test in do get");
		res.setContentType("application/json");
		// By default tomcat will send back a successful status code, we are overriding
		// this by setting status
		res.setStatus(404);
		
		final String URI = req.getRequestURI().replace("/ReimbursementApplication/", "");
		
		log.debug(URI);

		switch (URI) {
		
		case "login":
			//log.debug("test in login in switch case");
			lc.login(req, res);
			res.addHeader("test", "testing1");
			break;
		case "request":
			ec.request(req, res);
			break;
		case "tickets":
			ec.getTickets(req, res);
			break;
		case "reimbursements":
			mc.viewAll(req,res);
			break;
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req, res);
	}

}
