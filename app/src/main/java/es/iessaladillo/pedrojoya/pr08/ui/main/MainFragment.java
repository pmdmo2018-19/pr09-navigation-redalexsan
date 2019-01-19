package es.iessaladillo.pedrojoya.pr08.ui.main;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import es.iessaladillo.pedrojoya.pr08.R;
import es.iessaladillo.pedrojoya.pr08.ui.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr08.ui.settings.SettingsFragment;
import es.iessaladillo.pedrojoya.pr08.utils.FragmentUtils;

public class MainFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        setUpToolbar();
        setUpFav(requireView());
        setTextLorem();
    }

    private void setTextLorem() {
        TextView lorem = ViewCompat.requireViewById(requireView(),R.id.txtLorem);
        lorem.setText(sharedPreferences.getString(getString(R.string.prefLoremType_key),getString(R.string.main_latin_ipsum)));
    }

    private void setUpFav(View view) {
        FloatingActionButton fab = ViewCompat.requireViewById(view,R.id.floatingActionButtonMain);
        fab.setOnClickListener(v -> navigateToDetail());
    }

    private void navigateToDetail(){
        FragmentUtils.replaceFragmentAddToBackstack(requireFragmentManager(),R.id.container,new DetailFragment(),DetailFragment.class.getSimpleName(),
                DetailFragment.class.getSimpleName(),FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }

    private void setUpToolbar() {
        Toolbar toolbar = ViewCompat.requireViewById(requireView(),R.id.toolbar);
        toolbar.setTitle(getString(R.string.fragmnet_main_title));
        toolbar.inflateMenu(R.menu.menu_settings);
        toolbar.setOnMenuItemClickListener(item -> {
            navigateToPreferences();
            return true;
        });
    }

    private void navigateToPreferences() {
        FragmentUtils.replaceFragmentAddToBackstack(requireFragmentManager(),R.id.container,new SettingsFragment(),SettingsFragment.class.getSimpleName(),
                SettingsFragment.class.getSimpleName(),FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_settings,menu);
    }

    @Override
    public void onResume() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(TextUtils.equals(getString(R.string.prefLoremType_key),key))
            setTextLorem();
    }
}
