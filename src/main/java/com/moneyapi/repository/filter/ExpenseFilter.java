package com.moneyapi.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ExpenseFilter {

	private String description;
	
	@DateTimeFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dueDateMin;
	
	@DateTimeFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dueDateMax;

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDateMin() {
		return dueDateMin;
	}

	public void setDueDateMin(LocalDate dueDateMin) {
		this.dueDateMin = dueDateMin;
	}

	public LocalDate getDueDateMax() {
		return dueDateMax;
	}

	public void setDueDateMax(LocalDate dueDateMax) {
		this.dueDateMax = dueDateMax;
	}
	
}
