package com.ms.rootapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping(value = "/")
public class RootappApplication {

	public static void main(String[] args) {
		SpringApplication.run(RootappApplication.class, args);
	}

	@Value("${domain.extension}")
	private String extension;

	@RequestMapping(value={"/","/index"}, method = RequestMethod.GET)
	public String getAppTitle(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		String requestURL = String.valueOf(request.getRequestURL());


		return "<center><h1 style='margin-top:200px'> Welcome to Gymrabbit Dev Environment "+requestURL+" </h1></center>";
	}

	public static String getCurrentUrlFromRequest(HttpServletRequest request)
	{
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null)
			return requestURL.toString();

		return requestURL.append('?').append(queryString).toString();
	}
}
