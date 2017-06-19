package com.pentagon.myrecipeapp.main;

import com.pentagon.myrecipeapp.model.Recipe;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowRecipeCardsInteractor {

    ShowRecipeCardView view;

    public ShowRecipeCardsInteractor(ShowRecipeCardView view) {
        this.view = view;
    }

    public ArrayList<Recipe> getAllRecipe(Realm realm) {
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        RealmResults<Recipe> query = realm.where(Recipe.class)
                .findAll();
        for (Recipe recipe : query) {
            recipes.add(recipe);
        }
        view.showAllRecipe(recipes);
        return recipes;
    }
}
