package com.rana.recipe_api;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import recipemanagement_api.entity.Recipe;
import recipemanagement_api.repository.RecipeRepository;
import recipemanagement_api.service.RecipeService;



public class RecipeServiceTest {



	
	
	  @Autowired
	    private RecipeService recipeService;

	    @MockBean
	    private RecipeRepository recipeRepository;

	    @Test
	    public void testGetAllRecipes() {
	        Recipe r1 = new Recipe();
	        r1.setId("1");
	        r1.setTitle("Pasta");
	        r1.setCategory("Pasta");
	        r1.setCookingtime(20);

	        Recipe r2 = new Recipe();
	        r2.setId("2");
	        r2.setTitle("Burger");
	        r2.setCategory("Fast Food");
	        r2.setCookingtime(15);

	        when(recipeRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

	        List<Recipe> result = recipeService.getAllRecipes();
	        assertEquals(2, result.size());
	    }

	    @Test
	    public void testGetRecipesByCategory() {
	        Recipe r1 = new Recipe();
	        r1.setId("1");
	        r1.setTitle("Sushi");
	        r1.setCategory("Japanese");

	        when(recipeRepository.findByCategory("Japanese")).thenReturn(Arrays.asList(r1));

	        List<Recipe> result = recipeService.getRecipesByCategory("Japanese");
	        assertEquals(1, result.size());
	        assertEquals("Sushi", result.get(0).getTitle());
	    }

	    @Test
	    public void testSaveRecipe() {
	        Recipe recipe = new Recipe();
	        recipe.setId("5");
	        recipe.setTitle("Tacos");
	        recipe.setCategory("Mexican");

	        when(recipeRepository.save(recipe)).thenReturn(recipe);

	        Recipe saved = recipeService.createRecipe(recipe);
	        assertEquals("Tacos", saved.getTitle());
	    }

	    @Test
	    public void testDeleteRecipe() {
	        String id = "5";
	        doNothing().when(recipeRepository).deleteById(id);
	        recipeService.deleteRecipe(id);
	        verify(recipeRepository, times(1)).deleteById(id);
	    }
	
	
	
	
	
	
	
}
