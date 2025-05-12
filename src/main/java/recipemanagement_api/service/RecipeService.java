package recipemanagement_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import recipemanagement_api.entity.Recipe;
import recipemanagement_api.repository.RecipeRepository;
@Service
public class RecipeService {
	
@Autowired	
RecipeRepository recipeRepository;
	public Recipe createRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		return recipeRepository.save(recipe);
	}

	public List<Recipe> getAllRecipes() {
		// TODO Auto-generated method stub
		return recipeRepository.findAll();
	}

	public Recipe getRecipebyId(String id) {
		// TODO Auto-generated method stub
		return recipeRepository.findById(id).orElse(null);
	}

	public void deleteRecipe(String id) {
		
	 recipeRepository.deleteById(id);
	}

	

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

	
    public List<Recipe> findProductsWithSorting(String field){
    	return  recipeRepository.findAll(Sort.by(Sort.Direction.ASC,field));
    	
    	
    }
    
    public Page<Recipe>  findProductsWithPagination(int offset,int pagesize){
    	Page<Recipe> recipes = recipeRepository.findAll(PageRequest.of(offset, pagesize));
   return recipes;
    }
    
    
    
    public Page<Recipe>  findProductsWithPaginationAndSorting(int offset,int pagesize,String field){
    	Page<Recipe> recipes = recipeRepository.findAll(PageRequest.of(offset, pagesize).withSort(Sort.by(field)));
   return recipes;
    }
    
    public List<Recipe> getRecipesByTitle(String title) {
        return recipeRepository.findByTitle(title);
    }

    public List<Recipe> searchRecipesByTitle(String titlePattern) {
        return recipeRepository.findRecipesByTitleLike(titlePattern);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findByCategory(category);
    }

    public List<Recipe> searchRecipesByCategory(String categoryPattern) {
        return recipeRepository.findRecipesByCategoryLike(categoryPattern);
    }

    public List<Recipe> getRecipesByCookingTimeRange(int min, int max) {
        return recipeRepository.findByCookingtimeBetween(min, max);
    }
	
	
	
	
	
	
	
	
	
}
