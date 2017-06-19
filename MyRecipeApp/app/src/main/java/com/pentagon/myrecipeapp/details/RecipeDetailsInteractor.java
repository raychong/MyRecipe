package com.pentagon.myrecipeapp.details;

import com.pentagon.myrecipeapp.model.Recipe;

import io.realm.Realm;


public class RecipeDetailsInteractor {


    public void onAddRecipe(Realm realm, final Recipe recipe,final RecipeDetailsView view) {
        realm.executeTransaction(new Realm.Transaction() {
            public void execute(Realm realm) {
                Recipe recipe_ = realm.createObject(Recipe.class);

                recipe_.setNo(recipe.getNo());
                recipe_.setCategoryID(recipe.getCategoryID());
                recipe_.setCategory(recipe.getCategory());
                recipe_.setIngredient(recipe.getIngredient());
                recipe_.setSteps(recipe.getSteps());
                recipe_.setTitle(recipe.getTitle());
                realm.commitTransaction();
                realm.close();
                view.onRefreshList();
            }
        });
    }

    public void onUpdateRecipe(Realm realm,final Recipe recipe,final RecipeDetailsView view) {
        realm.executeTransaction(new Realm.Transaction() {
            public void execute(Realm realm) {
                Recipe recipeToEdit = realm.where(Recipe.class)
                        .equalTo("no", recipe.getNo()).findFirst();

                recipeToEdit.setNo(recipe.getNo());
                recipeToEdit.setCategoryID(recipe.getCategoryID());
                recipeToEdit.setCategory(recipe.getCategory());
                recipeToEdit.setIngredient(recipe.getIngredient());
                recipeToEdit.setSteps(recipe.getSteps());
                recipeToEdit.setTitle(recipe.getTitle());

                realm.commitTransaction();
                realm.close();
                view.onRefreshList();
            }
        });
    }

    public void onDeleteRecipe(Realm realm, final Recipe recipe,final RecipeDetailsView view) {
        realm.executeTransaction(new Realm.Transaction() {
            public void execute(Realm realm) {
                Recipe result = realm.where(Recipe.class)
                        .equalTo("no", recipe.getNo()).findFirst();
                result.removeFromRealm();
                realm.commitTransaction();
                realm.close();
                view.onRefreshList();
            }
        });

    }

    public void getRecipeByID(Realm realm,int id,final RecipeDetailsView view){
        Recipe recipe = realm.where(Recipe.class).equalTo("no", id).findFirst();
        view.showRecipeByID(recipe);
    }
}
