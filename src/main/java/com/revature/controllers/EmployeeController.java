package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.revature.models.RequestDTO;
import com.revature.models.Ticket;
import com.revature.services.EmployeeService;


public class EmployeeController {

	private static final Logger log = LogManager.getLogger(EmployeeController.class);
	private ObjectMapper om = new ObjectMapper();
	private EmployeeService es = new EmployeeService();
	
	public void request(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();

			StringBuilder sb = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			RequestDTO rDTO = new RequestDTO();
			String body = new String(sb);
			try {
				rDTO = om.readValue(body, RequestDTO.class);
			} 
			catch(InvalidFormatException ife) {
				res.setStatus(400);
				String json = om.writeValueAsString("Please enter a number for reimbursement amount!");
				res.getWriter().print(json);
				return;
			}
			if (rDTO.description.length()>250) {
				res.setStatus(400);
				String json = om.writeValueAsString("Description too long!");
				res.getWriter().print(json);
				return;
			}
		
			if (rDTO.amount<=0) {
				String json = om.writeValueAsString("Please enter a positive amount!");
				res.getWriter().print(json);
				return;
			}
			if (es.request(rDTO.username, rDTO.description, rDTO.amount, rDTO.type)) {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", true);
				String json = om.writeValueAsString("Request Sent!");
				res.getWriter().print(json);
				res.setStatus(201);
			}
			else {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", false);
				String json = om.writeValueAsString("Error while sending request!");
				res.getWriter().print(json);
				res.setStatus(500);
			}
			
				
			
			
		}
	}

	public void getTickets(HttpServletRequest req, HttpServletResponse res) throws IOException {
		

		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();

			StringBuilder sb = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			String body = new String(sb);
			
			RequestDTO rDTO = om.readValue(body, RequestDTO.class);
			
			List<Ticket> ticketList = es.getTickets(rDTO.username);
			if (ticketList.size()>0) {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", true);
				String json = om.writeValueAsString(ticketList);
				res.getWriter().print(json);
				res.setStatus(202);
			}
			else {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", true);
				String json = om.writeValueAsString("There were no tickets");
				res.getWriter().print(json);
				res.setStatus(204);
			}
			
			
		}
	}
}
