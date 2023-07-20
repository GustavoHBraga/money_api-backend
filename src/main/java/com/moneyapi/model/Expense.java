package com.moneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
 
@Entity
@Table(name="expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cod;
	
	@NotNull
	private String description;
	
	@NotNull
	@Column(name="due_date")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate dueDate;
	
	@Column(name="payment_date")
	@JsonFormat(pattern =  "dd/MM/yyyy")
	private LocalDate paymentDate;
	
	@NotNull
	private BigDecimal amount;
	
	private String observation;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TypeExpense type;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cod_category")
	private Category category;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cod_person")
	private Person person;

	public Long getCod() {
		return cod;
	}


	public void setCod(Long cod) {
		this.cod = cod;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymenteDate) {
		this.paymentDate = paymenteDate;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public TypeExpense getType() {
		return type;
	}


	public void setType(TypeExpense type) {
		this.type = type;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person people) {
		this.person = people;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cod);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expense other = (Expense) obj;
		return Objects.equals(cod, other.cod);
	}
	

	
}
