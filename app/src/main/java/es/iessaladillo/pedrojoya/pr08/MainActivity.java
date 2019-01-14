package es.iessaladillo.pedrojoya.pr08;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr08.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
