package com.pentagon.myrecipeapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Recipe extends RealmObject {
    @PrimaryKey
    private int no;

    private String title;
    private String ingredient;
    private String steps;
    private String category;
    private String categoryID;

    public Recipe() {
    }

    public Recipe(int no, String title, String ingredient, String steps, String category, String categoryID) {
        this.no = no;
        this.title = title;
        this.ingredient = ingredient;
        this.steps = steps;
        this.category = category;
        this.categoryID = categoryID;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

}
