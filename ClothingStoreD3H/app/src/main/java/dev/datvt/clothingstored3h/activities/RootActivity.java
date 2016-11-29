package dev.datvt.clothingstored3h.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dev.datvt.clothingstored3h.R;


/**
 * Created by datvt on 8/23/2016.
 */
public class RootActivity extends AppCompatActivity {

    int onStartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStartCount = 1;
        if (savedInstanceState == null) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else {
            onStartCount = 2;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }
}
