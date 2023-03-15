package com.android.c196.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.c196.Assessment.UI.Assessments;
import com.android.c196.Course.UI.Courses;
import com.android.c196.R;
import com.android.c196.Term.UI.Terms;
import com.android.c196.util.Repository;

import java.util.ArrayList;
import java.util.Calendar;

public class Home extends AppCompatActivity implements CatRecyclerViewInterface {
    private ArrayList<Category> categories;
    private Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        RecyclerView recyclerView = findViewById(R.id.categoriesRecyclerView);
        repo = new Repository(getApplication());

        categories = new ArrayList<>();
        setUpCategories();

        //create adapter after we set up our models
        Category_RecyclerViewAdapter adapter = new Category_RecyclerViewAdapter(this, categories, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }

    private void setUpCategories() {
        Category terms = new Category();
        terms.setId(1);
        terms.setCategoryName("Terms");
        terms.setImageUrl(R.drawable.ic_baseline_calendar_month_24);

        Category courses = new Category();
        courses.setId(2);
        courses.setCategoryName("Courses");
        courses.setImageUrl(R.drawable.ic_baseline_class_24);

        Category assessments = new Category();
        assessments.setId(3);
        assessments.setCategoryName("Assessments");
        assessments.setImageUrl(R.drawable.ic_baseline_assessment_24);

        categories.add(terms);
        categories.add(courses);
        categories.add(assessments);

        Calendar calendar = Calendar.getInstance();

    }

    @Override
    public void onItemClick(int position) {
        int id = categories.get(position).getId();

        switch(id) {
            case 1:
                Intent terms = new Intent(Home.this, Terms.class);
                startActivity(terms);
                break;
            case 2:
                Intent courses = new Intent(Home.this, Courses.class);
                startActivity(courses);
                break;
            case 3:
                Intent assessments = new Intent(Home.this, Assessments.class);
                startActivity(assessments);
                break;
        }
    }
}

class Category {
    private int id;
    private int imageUrl;
    private String categoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}