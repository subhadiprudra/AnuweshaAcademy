package com.easylife.aunweshaacademy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easylife.aunweshaacademy.BasicFunction;
import com.easylife.aunweshaacademy.R;
import com.easylife.aunweshaacademy.screens.Browser;
import com.easylife.aunweshaacademy.screens.MainActivity;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Account extends Fragment {

    CircleImageView dp;
    TextView nameTv,emailTv;
    BasicFunction basicFunction;

    CardView rate,share,about,contact,logout;

    Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_account, null);

        context = getContext();

        dp = view.findViewById(R.id.dp);
        nameTv = view.findViewById(R.id.name);
        emailTv = view.findViewById(R.id.email);

        rate= view.findViewById(R.id.rate);
        about= view.findViewById(R.id.about);
        share= view.findViewById(R.id.share);
        contact= view.findViewById(R.id.contact);
        logout= view.findViewById(R.id.logout);

        basicFunction= new BasicFunction(getContext());
        nameTv.setText(basicFunction.read("name"));
        emailTv.setText(basicFunction.read("email"));



        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName())));
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Learn and grow. Download Anuwesha academy app \n" +"https://play.google.com/store/apps/details?id=" + context.getPackageName();
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Anuwesha academy");
                intent.putExtra(android.content.Intent.EXTRA_TEXT,message);
                startActivity(Intent.createChooser(intent,message));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Browser.class);
                intent.putExtra("url","https://www.aunweshaacademy.com/about/");
                startActivity(intent);

            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Browser.class);
                intent.putExtra("url","https://www.aunweshaacademy.com/contact/");
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                basicFunction.write("email","null");
                basicFunction.write("password","null");
                basicFunction.write("name","null");
                startActivity(new Intent(context, MainActivity.class));


            }
        });

        return view;
    }
}