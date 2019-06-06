package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.runnimal.app.android.R;

import java.util.Locale;
import java.util.stream.IntStream;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity {


    @BindView(R.id.button_save)
    Button buttonSave;
    @BindView(R.id.spinner_settings_lang)
    Spinner langSpinner;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected int getBottomMenuItemId() {
        return -1;
    }

    @Override
    protected void initView() {
        initSaveButton();
        initLangButton();
    }

    private void initSaveButton() {
        buttonSave.setOnClickListener(view -> {
            String langCode = (String) langSpinner.getSelectedItem();
            Locale langLocale = Locale.forLanguageTag(langCode);

            SharedPreferences languageDetail = getSharedPreferences("language", MODE_PRIVATE);
            SharedPreferences.Editor editor = languageDetail.edit();
            editor.putString("lan", langCode);
            editor.apply();

            Configuration config = new Configuration();

            //TODO: implementar bien
            Locale.setDefault(langLocale);
            config.locale = langLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        });
    }


    private void initLangButton() {
        //TODO: Hacer que seleccione el idioma por defecto, ya que parece que esto no funciona
        String[] langArray = getResources().getStringArray(R.array.array_language);
        int currentLangPosition = IntStream.range(0, langArray.length - 1) //
                .filter(i -> Locale.forLanguageTag(langArray[i]).equals(getResources().getConfiguration().getLocales().get(0))) //
                .findFirst() //
                .orElse(0);
        langSpinner.setSelection(currentLangPosition);
    }

}