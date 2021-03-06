package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    protected ImageView ingredientsIv;
    @BindView(R.id.also_known_tv)
    protected TextView akaTv;
    @BindView(R.id.origin_tv)
    protected TextView origin;
    @BindView(R.id.ingredients_tv)
    protected TextView ingredientsTv;
    @BindView(R.id.description_tv)
    protected TextView descTv;
    @BindView(R.id.orgArea)
    protected TextView orgA;
    @BindView(R.id.akaArea)
    protected TextView akaA;
    @BindView(R.id.ingrArea)
    protected TextView ingrA;
    @BindView(R.id.descArea)
    protected TextView descA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // bind the view using butterknife
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        origin.setText(sandwich.getPlaceOfOrigin());
        if(origin.getText().equals("") || origin.getText().equals(null)){
            origin.setVisibility(View.GONE);
            orgA.setVisibility(View.GONE);
        }

        akaTv.setText("");
        for(String s : sandwich.getAlsoKnownAs()){
            akaTv.append(s + ". ");
        }
        if(akaTv.getText().equals("") || akaTv.getText().equals(null)) {
            akaTv.setVisibility(View.GONE);
            akaA.setVisibility(View.GONE);
        }

        descTv.setText(sandwich.getDescription());
        if(descTv.getText().equals("") || descTv.getText().equals(null)) {
            descTv.setVisibility(View.GONE);
            descA.setVisibility(View.GONE);
        }

        if(sandwich.getIngredients().isEmpty() || sandwich.getIngredients().equals(null)) {
            ingredientsTv.setVisibility(View.GONE);
            ingrA.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText("");
            for(String i : sandwich.getIngredients()) {
                ingredientsTv.append(i +"\n\n");
            }
        }

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
