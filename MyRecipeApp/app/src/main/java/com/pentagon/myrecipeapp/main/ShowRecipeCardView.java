package com.pentagon.myrecipeapp.main;

import com.pentagon.myrecipeapp.model.Recipe;

import java.util.ArrayList;


public interface ShowRecipeCardView {
    void showAllRecipe(ArrayList<Recipe> recipes);

    void showRecipeById(ArrayList<Recipe> recipes);

    void showRecipeDetailsScreen();

    void refreshRecipeList(int pos);
}
