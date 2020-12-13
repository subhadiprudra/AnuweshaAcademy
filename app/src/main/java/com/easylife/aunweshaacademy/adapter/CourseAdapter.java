package com.easylife.aunweshaacademy.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easylife.aunweshaacademy.BasicFunction;
import com.easylife.aunweshaacademy.R;
import com.easylife.aunweshaacademy.models.CourseModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
@RequiresApi(api = Build.VERSION_CODES.KITKAT)

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseModel> itemlist ;
    private Context mContext;
    Boolean isAll;
    BasicFunction basicFunction;



    public CourseAdapter(Context mcontext, List<CourseModel> itemlist,Boolean isAll){
        this.itemlist=itemlist;
        this.mContext = mcontext;
        this.isAll= isAll;

    }
    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.courses_item,parent,false);
        basicFunction = new BasicFunction(mContext);
        return new  CourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseAdapter.ViewHolder holder, final int position) {



        holder.title.setText(itemlist.get(position).getName());
        if(itemlist.get(position).getPrise()>0) {
            holder.price.setText("₹ " + itemlist.get(position).getPrise() + "");
        }else {
            holder.price.setText("Free");
        }

        String imageUrl = itemlist.get(position).getImageUrl().replaceAll("\\/","/");
        Log.i("image_url",imageUrl);
        Picasso.with(mContext).load(imageUrl).into(holder.imageView);

        if(itemlist.get(position).getEnrolled()){
            Log.i("isEnrolled","true");
            holder.button.setText("Unenroll");
        }else {
            holder.button.setText("Enroll");
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionEnroll(holder.button.getText().toString().toLowerCase(),itemlist.get(position).getName(),position);

            }
        });


    }

    @Override
    public int getItemCount() {
        try{
            return itemlist.size();
        }
        catch (Exception e){
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title,price;
        Button button;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.image);
            title= itemView.findViewById(R.id.title);
            price= itemView.findViewById(R.id.prise);
            button= itemView.findViewById(R.id.button);
            linearLayout= itemView.findViewById(R.id.layout);
        }
    }

    ProgressDialog progressDialog;
    public void actionEnroll(final String action, final String course, final int position) {
        String url = "https://aunweshaa.000webhostapp.com/enroll.php";
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response",response);
                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                getCourses();
                if(response.equals("Enrolled successfully")){
                   itemlist.get(position).setEnrolled(true);
                }else if (response.equals("Unenrolled successfully")){
                    itemlist.get(position).setEnrolled(false);
                }
                notifyDataSetChanged();



            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",basicFunction.read("email").trim());
                params.put("password",basicFunction.read("password"));
                params.put("action",action);
                params.put("course",course);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);


    }


    public void getCourses() {
        String url = "https://aunweshaa.000webhostapp.com/courses.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                basicFunction.write("data",response);
                progressDialog.dismiss();

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",basicFunction.read("email"));
                params.put("password",basicFunction.read("password"));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);


    }


}
