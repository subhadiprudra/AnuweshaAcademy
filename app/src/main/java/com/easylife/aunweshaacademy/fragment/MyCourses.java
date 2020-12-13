package com.easylife.aunweshaacademy.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylife.aunweshaacademy.BasicFunction;
import com.easylife.aunweshaacademy.R;
import com.easylife.aunweshaacademy.adapter.CourseAdapter;
import com.easylife.aunweshaacademy.models.CourseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyCourses extends Fragment {

    RecyclerView recyclerView;
    BasicFunction basicFunction;
    Context context;
    JSONArray jsonArray;
    TextView noItem;

    public MyCourses(Context context) {
        this.context = context;
        basicFunction = new BasicFunction(context);
        try {
            jsonArray = new JSONArray(basicFunction.read("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, null);
        List<CourseModel> courseModelList =new ArrayList<>();


        for(int i=0;i<jsonArray.length();i++){
            try {
                CourseModel model = new CourseModel((JSONObject) jsonArray.get(i));
                if(!model.getEnrolled()){
                    continue;
                }
                courseModelList.add(model);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        noItem = view.findViewById(R.id.no_course);
        recyclerView=view.findViewById(R.id.all_course_rv);

        if(courseModelList.isEmpty()){
            noItem.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            noItem.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            CourseAdapter courseAdapter = new CourseAdapter(context,courseModelList,false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(courseAdapter);
        }

        return view;
    }
}