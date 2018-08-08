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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView akaTv = findViewById(R.id.also_known_tv);
        TextView origin = findViewById(R.id.origin_tv);
        TextView descTv = findViewById(R.id.description_tv);

        LinearLayout orgA = findViewById(R.id.orgArea);
        LinearLayout akaA = findViewById(R.id.akaArea);
        LinearLayout ingrA = findViewById(R.id.ingrArea);
        LinearLayout descA = findViewById(R.id.descArea);

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
            orgA.setVisibility(View.GONE);
        }

        akaTv.setText("");
        for(String s : sandwich.getAlsoKnownAs()){
            akaTv.append(s + ". ");
        }
        if(akaTv.getText().equals("") || akaTv.getText().equals(null)) {
            akaA.setVisibility(View.GONE);
        }

        descTv.setText(sandwich.getDescription());
        if(descTv.getText().equals("") || descTv.getText().equals(null)) {
            descA.setVisibility(View.GONE);
        }

        if(sandwich.getIngredients().isEmpty() || sandwich.getIngredients().equals(null)) {
            ingrA.setVisibility(View.GONE);
        } else {
            ListAdapter ingredientsAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, sandwich.getIngredients());
            ListView ingredientsLv = findViewById(R.id.ingredients_lv);
            ingredientsLv.setAdapter(ingredientsAdapter);
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
