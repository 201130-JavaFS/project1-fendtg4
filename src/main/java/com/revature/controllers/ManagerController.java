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
import com.revature.models.ManagerDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ManagerService;

public class ManagerController {
	
	private static final Logger log = LogManager.getLogger(ManagerController.class);
	private ObjectMapper om = new ObjectMapper();
	private ManagerService ms = new ManagerService();
	
	public void viewAll(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();

			StringBuilder sb = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			String body = new String(sb);
			
			ManagerDTO mDTO = om.readValue(body, ManagerDTO.class);
			
			List<Reimbursement> reimbursementList = ms.getAllReimbursements();
			
			if (reimbursementList.size()>0) {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", true);
				String json = om.writeValueAsString(reimbursementList);
				res.getWriter().print(json);
				res.setStatus(202);
			}
			else {
				HttpSession ses = req.getSession();
				ses.setAttribute("requestSent", true);
				String json = om.writeValueAsString("There were no reimbursements");
				res.getWriter().print(json);
				res.setStatus(204);
			}
		}
	}

}
