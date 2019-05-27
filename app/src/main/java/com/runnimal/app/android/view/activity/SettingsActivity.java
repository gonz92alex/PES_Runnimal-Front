package com.runnimal.app.android.view.activity;

import android.content.Intent;
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

        initLangButton();
    }




    private void initLangButton() {
        //TODO: Hacer que seleccione el idioma por defecto, ya que parece que esto no funciona
        String[] langArray = getResources().getStringArray(R.array.array_language);
        int currentLangPosition = IntStream.range(0, langArray.length - 1) //
                .filter(i -> Locale.forLanguageTag(langArray[i]).equals(getResources().getConfiguration().getLocales().get(0))) //
                .findFirst() //
                .orElse(0);
        langSpinner.setSelection(currentLangPosition);

        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String langCode = (String) langSpinner.getSelectedItem();
                Locale langLocale = Locale.forLanguageTag(langCode);

                Configuration config = new Configuration();

                //TODO: implementar bien
                Locale.setDefault(langLocale);
                config.locale = langLocale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                /*
                Locale.setDefault(langLocale);
                LocaleList langLocaleList = new LocaleList(langLocale);
                config.setLocales(langLocaleList);
                LocaleList.setDefault(langLocaleList);
                getApplicationContext().createConfigurationContext(config);
                */
                /*
                Locale.setDefault(langLocale);
                Configuration config = new Configuration();
                config.setLocale(langLocale);
                getApplicationContext().createConfigurationContext(config);
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

}
