package com.example.usuario.aplicativotempoprova.UI.Fragmento;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario.aplicativotempoprova.UI.Activity.MainActivity;
import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.LocaleManager;
import com.example.usuario.aplicativotempoprova.Utils.MyApplication;
import com.example.usuario.aplicativotempoprova.Utils.SharedPreferencesUtil;
import com.example.usuario.aplicativotempoprova.Utils.ViewAnimation;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentoAbout extends DialogFragment {

    @BindView(R.id.english_button)
    ExtendedFloatingActionButton englishButton;

    @BindView(R.id.persian_button)
    ExtendedFloatingActionButton persianButton;

    @BindView(R.id.toggle_info_button)
    ImageButton toggleInfoButton;

    @BindView(R.id.expand_layout)
    LinearLayout expandLayout;

    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.night_mode_switch)
    SwitchCompat nightModeSwitch;

    private Activity activity;
    private String currentLingua;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup cont,
                             Bundle savedInstaState){
        View view = inflater.inflate(R.layout.fragment_about, cont, false);
        ButterKnife.bind(this, view);
        initVariaveis(view);
        return view;
    }

    private void initVariaveis(View view) {
        currentLingua = MyApplication.localeManager.getLanguage();
        activity = getActivity();
        if(activity != null) {
            Drawable drawable = activity.getResources().getDrawable(R.drawable.ic_done_black_24dp);
            String versionNome = "";
            try{
                versionNome = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName;
            }
            catch (PackageManager.NameNotFoundException e){

            }
            setTextWithLInks(view.findViewById(R.id.text_application_info), getString(R.string.application_info_text, versionNome));
            setTextWithLInks(view.findViewById(R.id.text_developer_info), getString(R.string.developer_info_text));
            setTextWithLInks(view.findViewById(R.id.text_design_api), getString(R.string.design_api_text));
            setTextWithLInks(view.findViewById(R.id.text_libraries), getString(R.string.libraries_text));
            setTextWithLInks(view.findViewById(R.id.text_license), getString(R.string.license_text));

            if(currentLingua.equals(LocaleManager.LANGUAGE_ENGLISH)){
                englishButton.setIcon(drawable);
            }
            else{
                persianButton.setIcon(drawable);
            }
        }
        nightModeSwitch.setChecked(SharedPreferencesUtil.getInstance(activity).isDarkThemeEnabled());
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtil.getInstance(activity).setDarkThemeEnabled(isChecked);
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO);
                }
                activity.recreate();
            }
        });
    }

    private void setTextWithLInks(TextView textView, String htmltexto){
        AppUtil.setTextoWithLinks(textView, AppUtil.fromHtml(htmltexto));
    }

    @NonNull
    public Dialog onCreatDialog(Bundle salvarStadoInst){
        Dialog dialog = super.onCreateDialog(salvarStadoInst);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
        wmlp.copyFrom(dialog.getWindow().getAttributes());
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(wmlp);
        return dialog;
    }

    @OnClick(R.id.close_button)
    void close(){
        dismiss();
        if(getFragmentManager() != null){
            getFragmentManager().popBackStack();
        }
    }

    @OnClick({R.id.english_button, R.id.persian_button})
    void handleChangeLingua(View view){
        switch(view.getId()){
            case R.id.english_button:
                if(currentLingua.equals(LocaleManager.LANGUAGE_PERSIAN)){
                    MyApplication.localeManager.setNewLocale(activity, LocaleManager.LANGUAGE_ENGLISH);
                    restartActivity();
                }
                break;
            case R.id.persian_button:
                if(currentLingua.equals(LocaleManager.LANGUAGE_PERSIAN)){
                    MyApplication.localeManager.setNewLocale(activity, LocaleManager.LANGUAGE_PERSIAN);
                    restartActivity();
                }
                break;
        }
    }

    private void restartActivity(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @OnClick({R.id.toggle_info_button, R.id.toggle_info_layout})
    void toggleInfoLayout(){
        boolean show = toggleArrow(toggleInfoButton);
        if(show) {
            ViewAnimation.expand(expandLayout, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {

                }
            });
        }else{
            ViewAnimation.collapse(expandLayout);
        }
    }

    private boolean toggleArrow(View view){
        if(view.getRotation() == 0){
            view.animate().setDuration(200).rotation(180);
            return true;
        }else{
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }
}
