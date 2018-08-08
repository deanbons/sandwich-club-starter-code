package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
       Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject nameDetails = sandwichDetails.getJSONObject(KEY_NAME);
            sandwich.setMainName(nameDetails.getString(KEY_MAIN_NAME));

            List<String> akaList = new ArrayList<String>();
            JSONArray akaArray = nameDetails.getJSONArray(KEY_ALSO_KNOWN_AS);
            if(akaArray != null) {
                for (int i = 0; i < akaArray.length(); ++i) {
                    if (!akaArray.isNull(i)) {
                        akaList.add(akaArray.getString(i));
                    }
                }
            }
            sandwich.setAlsoKnownAs(akaList);

            sandwich.setPlaceOfOrigin(sandwichDetails.getString(KEY_PLACE_OF_ORIGIN));

            sandwich.setDescription(sandwichDetails.getString(KEY_DESCRIPTION));

            sandwich.setImage(sandwichDetails.getString(KEY_IMAGE));

            List<String> ingrList = new ArrayList<String>();
            JSONArray ingrArray = sandwichDetails.getJSONArray(KEY_INGREDIENTS);
            if(ingrArray != null) {
                for (int i = 0; i < ingrArray.length(); ++i) {
                    if (!ingrArray.isNull(i)) {
                        ingrList.add(ingrArray.getString(i));
                    }
                }
            }
            sandwich.setIngredients(ingrList);

            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}