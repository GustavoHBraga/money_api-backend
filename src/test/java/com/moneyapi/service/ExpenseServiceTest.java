package com.moneyapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.moneyapi.model.Category;
import com.moneyapi.model.Expense;
import com.moneyapi.model.InformationAdress;
import com.moneyapi.model.Person;
import com.moneyapi.model.TypeExpense;
import com.moneyapi.repository.ExpenseRepository;
import com.moneyapi.repository.PersonRepository;
import com.moneyapi.service.exception.PersonNotActiveOrNoExists;

public class ExpenseServiceTest {
	
	@Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testSaveExpense() {
        List<Expense> expenses = factoryExpense();
		Expense expense = expenses.get(0);
		
		// check if person exists or not active - Then inject expense example to this function
		when(personRepository.findById(1L)).thenReturn(Optional.of(expense.getPerson()));
		
		when(expenseRepository.save(expense)).thenReturn(expense);

        Expense savedExpense = expenseService.save(expense);

        verify(expenseRepository, times(1)).save(expense);

        assertEquals(expense, savedExpense);

    }
    @Test
    public void testSaveExpensePersonNotActive() {
        // Crie um Expense de exemplo
    	List<Expense> expenses = factoryExpense();
		Expense expense = expenses.get(1);

        when(personRepository.findById(1L)).thenReturn(Optional.of(expense.getPerson()));
        verify(expenseRepository, never()).save(expense);
        assertThrows(PersonNotActiveOrNoExists.class, () -> expenseService.save(expense));

        
    }
    public static List<Expense> factoryExpense(){
		List<Expense> expenses = new ArrayList<>();
		List<Category> categories = factoryCategories();
		List<Person> people = factoryPeople();
		
		Expense expense_1 = new Expense();
		LocalDate dueDate = LocalDate.of(2023, 07, 10);
		LocalDate paymentDate = LocalDate.now();
		BigDecimal amount = BigDecimal.valueOf(125.5);
		
		
		expense_1.setCod(1L);
		expense_1.setDescription("Academia - Gastos semanal");
		expense_1.setDueDate(dueDate);
		expense_1.setPaymentDate(paymentDate);
		expense_1.setType(TypeExpense.REVENUE);
		expense_1.setAmount(amount);
		expense_1.setCategory(categories.get(0));
		expense_1.setPerson(people.get(0));
		
		
		Expense expense_2 = new Expense();
		LocalDate dueDate_2 = LocalDate.of(2023, 05, 20);
		LocalDate paymentDate_2 = LocalDate.now();
		BigDecimal amount_2 = BigDecimal.valueOf(355.5);
		
		
		expense_2.setCod(2L);
		expense_2.setDescription("NETFLIX");
		expense_2.setDueDate(dueDate_2);
		expense_2.setPaymentDate(paymentDate_2);
		expense_2.setType(TypeExpense.COST);
		expense_2.setAmount(amount_2);
		expense_2.setCategory(categories.get(1));
		expense_2.setPerson(people.get(1));
		
		expenses.add(expense_1);
		expenses.add(expense_2);
		
		
		return expenses;
	}
	public static List<Category> factoryCategories (){
    	
    	List<Category> categories = new ArrayList<>();
    	Category category1 = new Category();
    	category1.setCod(1L);
    	category1.setName("Category 1");
    	categories.add(category1);
        
    	Category category2 = new Category();
    	category2.setCod(1L);
    	category2.setName("Category 2");
    	
    	categories.add(category1);
    	categories.add(category2);
    	
    	return categories;
    }
	public static List<Person> factoryPeople(){
		
		List<Person> people = new ArrayList<>();
		
		Person person_1 = new Person();
		person_1.setCod(1L);
		person_1.setName("Gustavo");
		person_1.setActive(true);
		InformationAdress address_1 = new InformationAdress();
		address_1.setAddress("Rua rio paranapanema");
		address_1.setCep("06220250");
		address_1.setCity("OSASCO");
		address_1.setState("Sao Paulo");
		person_1.setInformationAdress(address_1);
		
		Person person_2 = new Person();
		person_2.setCod(1L);
		person_2.setName("Gustavo");
		person_2.setActive(false); 
		InformationAdress address_2 = new InformationAdress();
		address_2.setAddress("Bahia dos alvaros");
		address_2.setCep("06220250");
		address_2.setCity("Piaui");
		address_2.setState("Piaui");
		person_2.setInformationAdress(address_2);
		
		people.add(person_1);
		people.add(person_2);
		
		return people;
		
	}
}
