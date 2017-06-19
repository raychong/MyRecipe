package com.pentagon.myrecipeapp.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pentagon.myrecipeapp.MainActivity;
import com.pentagon.myrecipeapp.R;
import com.pentagon.myrecipeapp.adapter.RecipeCardsAdapter;
import com.pentagon.myrecipeapp.details.RecipeDetailsFragment;
import com.pentagon.myrecipeapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


public class MainActivityFragment extends Fragment implements ShowRecipeCardView,OnRecordAddedListener,RecipeCardsAdapter.onItemClickedListener{

    Context mContext;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<Recipe> recipeList;
    RecipeCardsAdapter adapter;

    ShowRecipeCardsPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        initToolbar();
        presenter = new ShowRecipeCardsPresenter(this,new ShowRecipeCardsInteractor(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        presenter.getAllRecipes(Realm.getInstance(mContext));
        return view;
    }

    @OnClick(R.id.fabAdd)
    public void onAddRecipe(){
        try{
            presenter.switchToRecipeDetailsPage();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initToolbar(){
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showAllRecipe(ArrayList<Recipe> recipes) {
        recipeList = recipes;
        adapter = new RecipeCardsAdapter(recipeList,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showRecipeById(ArrayList<Recipe> recipes) {
        recipeList = recipes;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showRecipeDetailsScreen() {
        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
        detailsFragment.setCallbackFragment(this);
      getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,detailsFragment).addToBackStack("").commit();
    }

    @Override
    public void refreshRecipeList(int pos) {
        adapter.removeItem(pos);
    }

    @Override
    public void onRecordAdded() {
        presenter.getAllRecipes(Realm.getInstance(mContext));
    }

    @Override
    public void onItemClicked(int pos) {
        RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
        Bundle bd = new Bundle();
        bd.putInt(getString(R.string.tag_selected_recipe),recipeList.get(pos).getNo());
        bd.putBoolean(getString(R.string.tag_is_editmode),true);
        detailsFragment.setArguments(bd);
        detailsFragment.setCallbackFragment(this);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,detailsFragment).addToBackStack("").commit();
    }

    public void onBackPressed(){
        new AlertDialog.Builder(mContext)
                .setMessage(getString(R.string.dialog_exit_message))
                .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}
