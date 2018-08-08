package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
       Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichDetails = new JSONObject(json);

            JSONObject nameDetails = sandwichDetails.getJSONObject("name");
            sandwich.setMainName(nameDetails.getString("mainName"));

            List<String> akaList = new ArrayList<String>();
            JSONArray akaArray = nameDetails.getJSONArray("alsoKnownAs");
            if(akaArray != null) {
                for (int i = 0; i < akaArray.length(); ++i) {
                    if (!akaArray.isNull(i)) {
                        akaList.add(akaArray.getString(i));
                    }
                }
            }
            sandwich.setAlsoKnownAs(akaList);

            sandwich.setPlaceOfOrigin(sandwichDetails.getString("placeOfOrigin"));

            sandwich.setDescription(sandwichDetails.getString("description"));

            sandwich.setImage(sandwichDetails.getString("image"));

            List<String> ingrList = new ArrayList<String>();
            JSONArray ingrArray = sandwichDetails.getJSONArray("ingredients");
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
