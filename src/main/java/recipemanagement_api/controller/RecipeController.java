package recipemanagement_api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import recipemanagement_api.entity.Recipe;
import recipemanagement_api.service.RecipeService;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired  
    private RecipeService recipeService;

    // Create a new recipe (Admin or Client)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    // Get all recipes (Admin or Client)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // Get a recipe by ID (Admin or Client)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipebyId(id);
        return ResponseEntity.ok(recipe);
    }

    // Update a recipe (Admin or Client)
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedRecipe);
    }

    // Delete a recipe by ID (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    // Sorting and Pagination
    @GetMapping("/sort")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> getSortedRecipes(@RequestParam String field) {
        return ResponseEntity.ok(recipeService.findProductsWithSorting(field));
    }

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Page<Recipe>> getPaginatedRecipes(@RequestParam int offset, @RequestParam int pagesize) {
        return ResponseEntity.ok(recipeService.findProductsWithPagination(offset, pagesize));
    }

    @GetMapping("/page-sort")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Page<Recipe>> getPaginatedSortedRecipes(@RequestParam int offset,
                                                                  @RequestParam int pagesize,
                                                                  @RequestParam String field) {
        return ResponseEntity.ok(recipeService.findProductsWithPaginationAndSorting(offset, pagesize, field));
    }

    // Search by title or category
    @GetMapping("/title")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> getByTitle(@RequestParam String title) {
        return ResponseEntity.ok(recipeService.getRecipesByTitle(title));
    }

    @GetMapping("/title-like")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> searchByTitle(@RequestParam String titlePattern) {
        return ResponseEntity.ok(recipeService.searchRecipesByTitle(titlePattern));
    }

    @GetMapping("/category")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> getByCategory(@RequestParam String category) {
        return ResponseEntity.ok(recipeService.getRecipesByCategory(category));
    }

    @GetMapping("/category-like")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> searchByCategory(@RequestParam String categoryPattern) {
        return ResponseEntity.ok(recipeService.searchRecipesByCategory(categoryPattern));
    }

    @GetMapping("/time-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<Recipe>> getByCookingTimeRange(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(recipeService.getRecipesByCookingTimeRange(min, max));
    }
}
