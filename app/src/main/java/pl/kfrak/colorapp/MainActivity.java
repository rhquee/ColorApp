package pl.kfrak.colorapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static pl.kfrak.colorapp.R.color.whiteColor;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_color)
    LinearLayout linearLayout;

    @BindColor(R.color.colorTruskawkowy)
    int truskawkowy;

    @BindColor(R.color.colorJagodowy)
    int jagodowy;

    @BindColor(R.color.colorBananowy)
    int bananowy;

    @BindColor(android.R.color.white)
    int whiteColor;

    private int currentColor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkTerms();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        currentColor = sharedPreferences.getInt("background_color", whiteColor);
        setColor(currentColor);
    }

    //ONCLICKI
    @OnClick(R.id.activityMain_TruskawkowyButton)
    public void setColorTruskawkowy() {
        setColor(truskawkowy);
        saveColor();
    }

    @OnClick(R.id.activityMain_JagodowyButton)
    public void setColorJagodowy() {
        setColor(jagodowy);
        saveColor();
    }

    @OnClick(R.id.activityMain_BananowyButton)
    public void setColorBananowy() {
        setColor(bananowy);
        saveColor();
    }

    //SET-SAVE COLORS
    private void setColor(int color) {
        currentColor = color;
        linearLayout.setBackgroundColor(color);
    }

    public void saveColor(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("background_color", currentColor);
        editor.apply(); // albo editor.commit();
    }

   //TERMS
    public void checkTerms(){
        if (!areTermsAccepted()) {
            showTermsDialog();
        }
    }

    private void showTermsDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Terms")
                .setMessage("Kochaj albo rzuć!")
                .setPositiveButton("Kochaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        storeTermsAccepted();
                        dialog.dismiss();
                    }
                })
                .setNeutralButton("Rzuć", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "night-night!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .create()
                .show();

    }

    private void storeTermsAccepted() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("terms_accepted", true);
        editor.apply();
    }

    private boolean areTermsAccepted() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("terms_accepted", false);
    }
}
