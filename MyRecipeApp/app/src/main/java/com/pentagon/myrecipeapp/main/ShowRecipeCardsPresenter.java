package com.pentagon.myrecipeapp.main;

import io.realm.Realm;



public class ShowRecipeCardsPresenter {
    ShowRecipeCardView view;
    ShowRecipeCardsInteractor interactor;

    public ShowRecipeCardsPresenter(ShowRecipeCardView showRecipeCardView, ShowRecipeCardsInteractor interactor) {
        this.view = showRecipeCardView;
        this.interactor = interactor;
    }

    void getAllRecipes(Realm realm){
        interactor.getAllRecipe(realm);
    }

    void switchToRecipeDetailsPage(){
        view.showRecipeDetailsScreen();
    }

}
