package com.moneyapi.resource;
import com.moneyapi.event.ResourceCreatedEvent;
import com.moneyapi.model.Category;
import com.moneyapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryResourceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private CategoryResource categoryResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListAll() {
    	
        // Crie uma lista de categorias de exemplo
    	List<Category> categories = factoryCategories();
    	
    	// Defina o comportamento esperado para o repository mock
        when(categoryRepository.findAll()).thenReturn(categories);

        // Execute o método a ser testado
        ResponseEntity<?> response = categoryResource.listAll();

        // Verifique se o repository findAll() foi chamado
        verify(categoryRepository, times(1)).findAll();

        // Verifique se a resposta contém a lista de categorias
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        
    }
    @Test
    public void testListAllEmpty() {
    	
    	// Defina o comportamento esperado para o repository mock
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());

        // Execute o método a ser testado
        ResponseEntity<?> response = categoryResource.listAll();

        // Verifique se o repository findAll() foi chamado
        verify(categoryRepository, times(1)).findAll();

        // Verifique se a resposta contém a lista de categorias
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        
    }
    @Test
    public void testFindByCod() {
    	List<Category> categories = factoryCategories();
    	
        // Crie uma categoria de exemplo
    	Category category = categories.get(0);
    	

        // Defina o comportamento esperado para o repository mock
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Execute o método a ser testado
        ResponseEntity<Category> response = categoryResource.findByCod(1L);

        // Verifique se o repository findById() foi chamado
        verify(categoryRepository, times(1)).findById(1L);

        // Verifique se a resposta contém a categoria
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }
    
    @Test
    public void testFindByCodNotFound() {
        // Defina o comportamento esperado para o repository mock
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute o método a ser testado
        ResponseEntity<Category> response = categoryResource.findByCod(1L);

        // Verifique se o repository findById() foi chamado
        verify(categoryRepository, times(1)).findById(1L);

        // Verifique se a resposta é "not found"
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testCreate() {
    	List<Category> categories = factoryCategories();
    	
        // Crie uma categoria de exemplo
    	Category category = categories.get(0);

        // Defina o comportamento esperado para o repository mock
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Execute o método a ser testado
        ResponseEntity<Category> response = categoryResource.create(category, mock(HttpServletResponse.class));

        // Verifique se o repository save() foi chamado
        verify(categoryRepository, times(1)).save(category);

        // Verifique se o evento de criação de recurso foi publicado
        verify(publisher, times(1)).publishEvent(any(ResourceCreatedEvent.class));

        // Verifique se a resposta contém a categoria salva
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void testDeleteCategory() {
        // Execute o método a ser testado
        categoryResource.deleteCategory(1L);

        // Verifique se o repository deleteById() foi chamado
        verify(categoryRepository, times(1)).deleteById(1L);
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
}