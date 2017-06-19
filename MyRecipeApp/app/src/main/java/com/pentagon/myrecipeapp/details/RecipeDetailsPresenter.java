package com.pentagon.myrecipeapp.details;

import com.pentagon.myrecipeapp.R;
import com.pentagon.myrecipeapp.model.Recipe;

import io.realm.Realm;


public class RecipeDetailsPresenter {
    RecipeDetailsView view;
    RecipeDetailsInteractor interactor;

    public RecipeDetailsPresenter(RecipeDetailsView view, RecipeDetailsInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public RecipeDetailsPresenter(RecipeDetailsView view) {
        this.view = view;
    }

    void onAddRecipe(Realm realm){

        if(view.getTitle().isEmpty()){
            view.showRecipeNameError(R.string.error_recipe_name);
            return;
        }

        if(view.getSteps().isEmpty()){
            view.showStepsError(R.string.error_recipe_steps);
            return;
        }

        if(view.getIngredient().isEmpty()){
            view.showIngredientError(R.string.error_recipe_ingredients);
            return;
        }

        int nextID = (int) (realm.where(Recipe.class).maximumInt("no") + 1);
        Recipe recipe = new Recipe(nextID ,view.getTitle(),view.getIngredient(),view.getSteps(),view.getCategory(),view.getCategoryID());
        interactor.onAddRecipe(realm,recipe,view);
    }

    void onUpdateRecipe(Realm realm, int recipeID){

        if(view.getTitle().isEmpty()){
            view.showRecipeNameError(R.string.error_recipe_name);
            return;
        }

        if(view.getSteps().isEmpty()){
            view.showStepsError(R.string.error_recipe_steps);
            return;
        }

        if(view.getIngredient().isEmpty()){
            view.showIngredientError(R.string.error_recipe_ingredients);
            return;
        }

        Recipe recipe = new Recipe(recipeID ,view.getTitle(),view.getIngredient(),view.getSteps(),view.getCategory(),view.getCategoryID());
        interactor.onUpdateRecipe(realm,recipe,view);
        return;
    }

    void onDeleteRecipe(Realm realm, Recipe recipe){

        if(recipe == null){
            view.showDeleteError(R.string.error_delete_failed);
            return;
        }
        interactor.onDeleteRecipe(realm,recipe,view);
    }

    void getRecipeByID(Realm realm, int recipeID){
        if(recipeID == -1){
            view.onGetRecipeByIDError(R.string.error_failed_to_get_recipe);
            return;
        }
        interactor.getRecipeByID(realm,recipeID,view);

    }
}
