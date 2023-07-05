package com.moneyapi.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	private Long cod;
	
	public ResourceCreatedEvent(Object source,HttpServletResponse response, Long cod) {
		super(source);
		this.cod = cod;
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCod() {
		return cod;
	}
}
