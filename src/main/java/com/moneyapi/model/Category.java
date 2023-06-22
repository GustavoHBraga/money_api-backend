package com.moneyapi.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="category")
public class Category {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long cod;
		
		@NotNull
		@Size(min = 3,max = 50)
		private String name;
		
		public Long getCod() {
			return cod;
		}
		
		public void setCod(Long cod) {
			this.cod = cod;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(cod, name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Category other = (Category) obj;
			return Objects.equals(cod, other.cod) && Objects.equals(name, other.name);
		}
		
		
}
