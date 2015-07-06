package com.nedap_bi.examples.server.authorizationcode.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.Map;

import com.nedap_bi.examples.server.authorizationcode.helpers.SessionHelpers;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class HomeController {	

	public HomeController(){	
		
		/**
		 * Home route /
		 */
		get("/", (request, response) -> {
			Map<String, String> map = new HashMap<String, String>();
			String userCookie = request.cookie(SessionHelpers.COOKIE_USER);
			map.put("user", userCookie);
			if (userCookie != null && userCookie.length() != 0) {
				response.redirect("/myapp/home");
			}
			return new ModelAndView(map, "index.mustache");
		}, new MustacheTemplateEngine());

		/**
		 * Login form
		 */
		get("/login", (request, response) -> {
			return new ModelAndView(null, "login.mustache");
		}, new MustacheTemplateEngine());

		/**
		 * Login handler
		 */
		post("/login", (request, response) -> {
			String username = request.queryParams("username");
			if (username != null && username.length() != 0) {
				response.cookie(SessionHelpers.COOKIE_USER, request.queryParams("username"));
				
				response.redirect("/");
			} else {
				response.redirect("/login");
			}
			return "";
		});

		get("/logout", (request, response) -> {
			SessionHelpers.destroySession(response);
			response.redirect("/");
			return "";
		});

	}
}
