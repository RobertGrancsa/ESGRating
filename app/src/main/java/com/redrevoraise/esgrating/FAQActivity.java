package com.redrevoraise.esgrating;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class FAQActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    private Button nextButton;
    private Button exitButton;
    private Integer slideNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        slideNumber = 0;

        nextButton = findViewById(R.id.nextButton);
        exitButton = findViewById(R.id.skipButton);
        viewFlipper = findViewById(R.id.flipper);
        viewFlipper.setInAnimation(this, R.anim.slide_to_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_to_left);
        Intent intent = new Intent(FAQActivity.this, MainActivity.class);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slideNumber == 4) {
                    startActivity(intent);
                    finish();
                }
                viewFlipper.showNext();
                slideNumber++;
                if (slideNumber == 4) {
                    nextButton.setText("Finish");
                    exitButton.setVisibility(View.GONE);
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
    }


}
