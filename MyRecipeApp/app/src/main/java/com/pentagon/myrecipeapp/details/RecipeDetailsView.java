package com.pentagon.myrecipeapp.details;

import com.pentagon.myrecipeapp.model.Recipe;


public interface RecipeDetailsView {

    String getCategoryID();
    String getCategory();
    String getTitle();
    String getIngredient();
    String getSteps();

    void showRecipeNameError(int resId);
    void showIngredientError(int resId);
    void showStepsError(int resId);
    void showDeleteError(int resId);
    void onGetRecipeByIDError(int resId);
    void showRecipeByID(Recipe recipe);

    void onRefreshList();

}
