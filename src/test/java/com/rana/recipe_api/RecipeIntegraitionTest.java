package com.rana.recipe_api;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import recipemanagement_api.entity.Recipe;
import recipemanagement_api.repository.RecipeRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc









public class RecipeIntegraitionTest {

	
	 @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private RecipeRepository recipeRepository;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @BeforeEach
	    void setup() {
	        recipeRepository.deleteAll(); // Clean DB before each test
	    }

	    @Test
	    public void testCreateAndFetchRecipe() throws Exception {
	        Recipe recipe = new Recipe();
	        recipe.setId("id1");
	        recipe.setTitle("Spaghetti");

	        // Create
	        mockMvc.perform(post("/recipes")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(recipe)))
	                .andExpect(status().isOk());

	        // Fetch
	        mockMvc.perform(get("/recipes/id1"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.title").value("Spaghetti"));
	    }
	
	
	
	
	
}
