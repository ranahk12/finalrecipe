package recipemanagement_api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import recipemanagement_api.entity.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe,String> {
	  List<Recipe> findByTitle(String title);
	    List<Recipe> findRecipesByTitleLike(String titlePattern);

	    List<Recipe> findByCategory(String category);
	    List<Recipe> findRecipesByCategoryLike(String categoryPattern);

	    List<Recipe> findByCookingtimeBetween(int min, int max);
}
