package com.revature.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.LoginDTO;
import com.revature.services.LoginService;

public class LoginController {

	private static final Logger log = LogManager.getLogger(LoginController.class);
	private ObjectMapper om = new ObjectMapper();
	private LoginService ls = new LoginService();

	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();

			StringBuilder sb = new StringBuilder();

			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			String body = new String(sb);

			LoginDTO lDTO = om.readValue(body, LoginDTO.class);

			if (ls.login(lDTO.username, lDTO.password)) {
				HttpSession ses = req.getSession();

				ses.setAttribute("user", lDTO);
				ses.setAttribute("loggedin", true);
				int roleId = ls.getRoleId(lDTO.username);
				String userRole = ls.getUserRole(roleId);
				
				String json = om.writeValueAsString(userRole);
				res.getWriter().print(json);
				res.setStatus(200);

			} else {
				HttpSession ses = req.getSession(false);
				if (ses != null) {
					ses.invalidate();
				}
				res.setStatus(401);
				res.getWriter().print("Login failed");
			}
			
			

		}
	}

}
