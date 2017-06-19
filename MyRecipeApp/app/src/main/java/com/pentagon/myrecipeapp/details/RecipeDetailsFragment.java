package com.pentagon.myrecipeapp.details;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.pentagon.myrecipeapp.MainActivity;
import com.pentagon.myrecipeapp.R;
import com.pentagon.myrecipeapp.library.Utils;
import com.pentagon.myrecipeapp.main.OnRecordAddedListener;
import com.pentagon.myrecipeapp.model.Category;
import com.pentagon.myrecipeapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.pentagon.myrecipeapp.library.Utils.hideKeyboard;


public class RecipeDetailsFragment extends Fragment implements RecipeDetailsView {

    Context mContext;
    Fragment callbackFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.categorySpinner)
    AppCompatSpinner categorySpinner;

    @BindView(R.id.etName)
    TextInputEditText etName;

    @BindView(R.id.etIngredient)
    TextInputEditText etIngredient;

    @BindView(R.id.etSteps)
    TextInputEditText etSteps;

    @BindView(R.id.btnDelete)
    AppCompatButton btnDelete;

    boolean isEditMode = false;
    int recipeID;
    RecipeDetailsPresenter presenter;
    Recipe selectedRecipe;
    ArrayList<Category> categoryList;
    ArrayList<String> categoryNameList;
    ArrayAdapter<String> categoryAdapter;

    OnRecordAddedListener mCallbackListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            mCallbackListener = (OnRecordAddedListener) callbackFragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);
        Bundle bd = getArguments();
        if (bd != null) {
            recipeID = bd.getInt(getString(R.string.tag_selected_recipe));
            isEditMode = bd.getBoolean(getString(R.string.tag_is_editmode));
        }
        initToolbar();
        setHasOptionsMenu(true);
        presenter = new RecipeDetailsPresenter(this, new RecipeDetailsInteractor());
        btnDelete.setVisibility(isEditMode ? View.VISIBLE : View.GONE);
        initAdapter();

        if (isEditMode)
            setContent();

        return view;
    }


    public void initToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(!isEditMode ? getString(R.string.title_create) : getString(R.string.title_edit));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(mContext, android.R.color.white), PorterDuff.Mode.MULTIPLY);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    public void onBackPressed(){
        try{
            new AlertDialog.Builder(mContext)
                    .setTitle(getString(R.string.dialog_discard_title))
                    .setMessage(getString(R.string.dialog_discard_message))
                    .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().getSupportFragmentManager().popBackStack();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initAdapter() {
        try {
            categoryList = Utils.getRecipeCategory(mContext);
            categoryNameList = new ArrayList<>();
            if (categoryList.size() > 0) {
                for (Category category : categoryList) {
                    categoryNameList.add(category.getName());
                }
                categoryAdapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_spinner_dropdown_item, categoryNameList);
                categorySpinner.setAdapter(categoryAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContent() {
        try {
            presenter.getRecipeByID(Realm.getInstance(mContext), recipeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSpinnerPos(String categoryID) {
        int pos = 0;
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryID.equalsIgnoreCase(categoryList.get(i).getId())) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    public String getCategoryID() {
        return categoryList.get(categorySpinner.getSelectedItemPosition()).getId();
    }

    @Override
    public String getCategory() {
        return categoryList.get(categorySpinner.getSelectedItemPosition()).getName();
    }

    @Override
    public String getTitle() {
        return etName.getText().toString().trim();
    }

    @Override
    public String getIngredient() {
        return etIngredient.getText().toString().trim();
    }

    @Override
    public String getSteps() {
        return etSteps.getText().toString().trim();
    }

    @Override
    public void showRecipeNameError(int resId) {

        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showIngredientError(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStepsError(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteError(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetRecipeByIDError(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRecipeByID(Recipe recipe) {
        selectedRecipe = recipe;
        if (selectedRecipe != null) {
            etName.setText("" + selectedRecipe.getTitle());
            etIngredient.setText("" + selectedRecipe.getIngredient());
            etSteps.setText("" + selectedRecipe.getSteps());
            categorySpinner.setSelection(getSpinnerPos(selectedRecipe.getCategoryID()));
        }
    }

    @Override
    public void onRefreshList() {
        hideKeyboard(mContext);
        mCallbackListener.onRecordAdded();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (isEditMode)
                presenter.onUpdateRecipe(Realm.getInstance(mContext), selectedRecipe.getNo());
            else
                presenter.onAddRecipe(Realm.getInstance(mContext));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCallbackFragment(Fragment callbackFragment) {
        this.callbackFragment = callbackFragment;
    }

    @OnClick(R.id.btnDelete)
    public void onDeleteRecipe() {
        try {
            new AlertDialog.Builder(mContext)
                    .setTitle(getString(R.string.dialog_delete_title))
                    .setMessage(getString(R.string.dialog_delete_message))
                    .setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.onDeleteRecipe(Realm.getInstance(mContext), selectedRecipe);
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
