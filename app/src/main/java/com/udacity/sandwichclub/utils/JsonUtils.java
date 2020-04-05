package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        String mainName = null;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = new ArrayList<>();

        JSONObject sandwichJSONObj;
        try {
            sandwichJSONObj = new JSONObject(json);
            JSONObject nameJSONObj = sandwichJSONObj.getJSONObject("name");
            mainName = nameJSONObj.getString("mainName");
            JSONArray alsoKnownAsJSONArray = nameJSONObj.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownAsJSONArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
            }
            placeOfOrigin = sandwichJSONObj.getString("placeOfOrigin");
            description = sandwichJSONObj.getString("description");
            image = sandwichJSONObj.getString("image");
            JSONArray ingredientsJSONArray = sandwichJSONObj.getJSONArray("ingredients");
            for (int i = 0; i < alsoKnownAsJSONArray.length(); i++) {
                ingredients.add(ingredientsJSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }


}
