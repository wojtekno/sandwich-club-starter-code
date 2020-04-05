package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView alsoKnownTV;
    TextView ingredientsTV;
    TextView originTV;
    TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        alsoKnownTV = (TextView) findViewById(R.id.also_known_tv);
        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        originTV = (TextView) findViewById(R.id.origin_tv);
        descriptionTV = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            Log.e(LOG_TAG, "No intent");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.e(LOG_TAG, "DEAFULT POSITION \n\n\nEXTRA_POSITION not found in intent");

            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.d("DetailActivity", json);
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e(LOG_TAG, "Sandwich == nul \n\n\nSandwich data unavailable\n");

            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate UI with information contained in Sandwich object.
     * If some of the sandwich object's fields are empty, UI makes the section invisible (GONE).
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList == null || alsoKnownAsList.size() == 0) {
            TextView alsoKnownAsLabel = (TextView) findViewById(R.id.also_known_label_tv);
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownTV.setVisibility(View.GONE);
        } else {
            for (String otherName : alsoKnownAsList) {
                if (alsoKnownAsList.indexOf(otherName) == alsoKnownAsList.size() - 1) {
                    alsoKnownTV.append(otherName);
                } else {
                    alsoKnownTV.append(otherName + ", ");
                }
            }
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients == null || ingredients.isEmpty()) {
            TextView ingredientsLabel = findViewById(R.id.ingredients_label_tv);
            ingredientsLabel.setVisibility(View.GONE);
            ingredientsTV.setVisibility(View.GONE);
        } else {
            for (String ingredient : ingredients) {
                if (ingredients.indexOf(ingredient) == ingredients.size() - 1) {
                    ingredientsTV.append(ingredient);
                } else {
                    ingredientsTV.append(ingredient + ", ");
                }
            }
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().isEmpty()) {
            originTV.setText(sandwich.getPlaceOfOrigin());
        } else {
            TextView originLabel = findViewById(R.id.origin_label_tv);
            originLabel.setVisibility(View.GONE);
            originTV.setVisibility(View.GONE);
        }

        if (sandwich.getDescription() != null && !sandwich.getDescription().isEmpty()) {
            descriptionTV.setText(sandwich.getDescription());
        } else {
            TextView descriptionLabel = findViewById(R.id.description_label_tv);
            descriptionLabel.setVisibility(View.GONE);
            descriptionTV.setVisibility(View.GONE);
        }
    }
}
