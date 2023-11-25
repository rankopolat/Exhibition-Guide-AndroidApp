package com.example.exhibitionguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Top_Layer_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_layer);

        Button art = (Button) findViewById(R.id.artButton);
        art.setOnClickListener(clicked);
        Button war = (Button) findViewById(R.id.warButton);
        war.setOnClickListener(clicked);
        Button visual = (Button) findViewById(R.id.visButton);
        visual.setOnClickListener(clicked);
        Button space = (Button) findViewById(R.id.spaceButton);
        space.setOnClickListener(clicked);
    }

    private final View.OnClickListener clicked  =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(view.getId()){
                        case R.id.artButton:
                            Intent i = new Intent(Top_Layer_Activity.this,art_exhibiton.class);
                            i.putExtra(art_exhibiton.SELECT_SHOW,"Art Exhibiton");
                            startActivity(i);
                            break;

                        case R.id.warButton:
                            Intent war = new Intent(Top_Layer_Activity.this,art_exhibiton.class);
                            war.putExtra(art_exhibiton.SELECT_SHOW,"WWII Exhibiton");
                            startActivity(war);
                            break;
                        case R.id.visButton:
                            Intent vis = new Intent(Top_Layer_Activity.this,art_exhibiton.class);
                            vis.putExtra(art_exhibiton.SELECT_SHOW,"Visual Show");
                            startActivity(vis);
                            break;
                        case R.id.spaceButton:
                            Intent spa = new Intent(Top_Layer_Activity.this,art_exhibiton.class);
                            spa.putExtra(art_exhibiton.SELECT_SHOW,"Exploring The Space");
                            startActivity(spa);
                            break;
                    }
                }
            };
}