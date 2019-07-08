package com.example.usuario.aplicativotempoprova.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.usuario.aplicativotempoprova.R;
import com.github.pwittchen.prefser.library.rx2.Prefser;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.os.ConfigurationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class AppUtil {

    public static long getStartDiaTimeStamp(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        newCalendar.set(Calendar.HOUR_OF_DAY, 0);
        newCalendar.set(Calendar.MINUTE, 0);
        newCalendar.set(Calendar.SECOND, 0);
        newCalendar.set(Calendar.MILLISECOND, 0);
        return newCalendar.getTimeInMillis();
    }

    public static long getEndDiaTimestamp(Calendar calendar){
        Calendar newCalendar = Calendar.getInstance(TimeZone.getDefault());
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        newCalendar.set(Calendar.HOUR_OF_DAY, 23);
        newCalendar.set(Calendar.MINUTE, 59);
        newCalendar.set(Calendar.SECOND, 59);
        newCalendar.set(Calendar.MILLISECOND, 0);
        return newCalendar.getTimeInMillis();
    }

    public static Calendar addDias(Calendar calen, int dias){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(calen.getTimeInMillis());
        calendar.add(Calendar.DATE, dias);
        return calendar;
    }

    public static void setIconeTempo(Context context, AppCompatImageView imagemView, int tempoCodeg){
        if(tempoCodeg / 100 == 2){
            Glide.with(context).load(R.drawable.ic_storm_weather).into(imagemView);
        }else if(tempoCodeg / 100 == 3){
            Glide.with(context).load(R.drawable.ic_rainy_weather).into(imagemView);
        }else if(tempoCodeg / 100 == 5) {
            Glide.with(context).load(R.drawable.ic_rainy_weather).into(imagemView);
        }else if(tempoCodeg / 100 == 6) {
            Glide.with(context).load(R.drawable.ic_snow_weather).into(imagemView);
        }else if(tempoCodeg / 100 == 7) {
            Glide.with(context).load(R.drawable.ic_unknown).into(imagemView);
        }else if(tempoCodeg == 800) {
            Glide.with(context).load(R.drawable.ic_clear_day).into(imagemView);
        }else if(tempoCodeg == 801) {
            Glide.with(context).load(R.drawable.ic_few_clouds).into(imagemView);
        }else if(tempoCodeg == 803) {
            Glide.with(context).load(R.drawable.ic_broken_clouds).into(imagemView);
        }else if(tempoCodeg / 100 == 8) {
            Glide.with(context).load(R.drawable.ic_cloudy_weather).into(imagemView);
        }
    }

    public static void showFragment(Fragment fragment, FragmentManager fragmentManager, boolean withAnimation){
        FragmentTransaction transactionF = fragmentManager.beginTransaction();
        if(withAnimation){
            transactionF.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim);
        }else{
            transactionF.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        transactionF.add(android.R.id.content, fragment).addToBackStack(null).commit();
    }

    @SuppressLint("StringFormatMatches")
    public static String getTime(Calendar calendar, Context context){
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        String horaString;
        if(hora < 10){
            horaString = String.format(Locale.getDefault(), context.getString(R.string.zero_label, hora));
        }else{
            horaString = String.format(Locale.getDefault(), "%d", hora);
        }

        String minutoString;
        if(minuto < 10){
            minutoString = String.format(Locale.getDefault(), context.getString(R.string.zero_label), minuto);
        }else{
            minutoString = String.format(Locale.getDefault(), "%d", minuto);
        }

        return horaString + ":" + minutoString;
    }

    public static int getTempoAnimacao(int tempoCodeg){
        if(tempoCodeg / 100 == 2) {
            return R.raw.storm_weather;
        }else if (tempoCodeg / 100 == 3){
            return R.raw.rainy_weather;
        }else if (tempoCodeg / 100 == 5){
            return R.raw.rainy_weather;
        }else if(tempoCodeg / 100 == 6){
            return R.raw.snow_weather;
        }else if(tempoCodeg / 100 == 7){
            return R.raw.unknown;
        }else if(tempoCodeg == 800){
            return R.raw.clear_day;
        }else if(tempoCodeg == 801){
            return R.raw.few_clouds;
        }else if(tempoCodeg == 803){
            return R.raw.broken_clouds;
        }else if(tempoCodeg / 100 == 8){
            return R.raw.cloudy_weather;
        }
        return R.raw.unknown;
    }

    public static String getTempoStatus(int tempoCodeg, boolean isRTL){
        if(tempoCodeg / 100 == 2){
            if(isRTL){
                return Constants.TEMPO_STATUS_INGLES[0];
            }else{
                return Constants.TEMPO_STATUS[0];
            }
        }else if(tempoCodeg / 100 == 3){
            if(isRTL){
                return Constants.TEMPO_STATUS_INGLES[1];
            }else{
                return Constants.TEMPO_STATUS[1];
            }
        }else if(tempoCodeg / 100 == 5) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[2];
            } else {
                return Constants.TEMPO_STATUS[2];
            }
        }else if(tempoCodeg / 100 == 6) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[3];
            } else {
                return Constants.TEMPO_STATUS[3];
            }
        }else if(tempoCodeg / 100 == 7) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[4];
            } else {
                return Constants.TEMPO_STATUS[4];
            }
        }else if(tempoCodeg == 800) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[5];
            } else {
                return Constants.TEMPO_STATUS[5];
            }
        }else if(tempoCodeg == 801) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[6];
            } else {
                return Constants.TEMPO_STATUS[6];
            }
        }else if(tempoCodeg == 803) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[7];
            } else {
                return Constants.TEMPO_STATUS[7];
            }
        }else if(tempoCodeg / 100 == 8) {
            if (isRTL) {
                return Constants.TEMPO_STATUS_INGLES[8];
            } else {
                return Constants.TEMPO_STATUS[8];
            }
        }
        if(isRTL) {
            return Constants.TEMPO_STATUS_INGLES[4];
        }else {
            return Constants.TEMPO_STATUS[4];
        }
    }

    public static boolean isTimePass(long lastStored) {
        return System.currentTimeMillis() - lastStored > Constants.TIME_TO_PASS;
    }

    public static void showSetAppIdDialog(Context context, Prefser prefser, View.OnKeyListener oKListener){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_set_appid);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
        wmlp.copyFrom(dialog.getWindow().getAttributes());
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(wmlp);
        dialog.findViewById(R.id.open_openweather_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ite = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Constants.OPEN_TEMPO_MAPA_WEBSITE));
                context.startActivity(ite);
            }
        });
        dialog.findViewById(R.id.store_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatEditText apiKeyEditText = dialog.findViewById(R.id.api_key_edit_text);
                String apiKey = apiKeyEditText.getText().toString();
                if(!apiKey.equals("")){
                    prefser.put(Constants.API_KEY, apiKey);
                    //listener.setApiKey();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setTextoWithLinks(TextView textView, CharSequence html) {
        textView.setText(html);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    TextView widget = (TextView) v;
                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = Spannable.Factory.getInstance()
                            .newSpannable(widget.getText())
                            .getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public static CharSequence fromHtml(String htmlText) {
        if (TextUtils.isEmpty(htmlText)) {
            return null;
        }
        CharSequence spann;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spann = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spann = Html.fromHtml(htmlText);
        }
        return trim(spann);
    }

    private static CharSequence trim(CharSequence charSeq) {
        if (TextUtils.isEmpty(charSeq)) {
            return charSeq;
        }
        int end = charSeq.length() - 1;
        while (Character.isWhitespace(charSeq.charAt(end))) {
            end--;
        }
        return charSeq.subSequence(0, end + 1);
    }

    static boolean isAtUltimaVersao(int versao){
        return Build.VERSION.SDK_INT >= versao;
    }

    public static boolean isRTL(Context context) {
        Locale locale = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0);
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    @SuppressLint("StaticFieldLeak")
    private static Application applicationSFL;


    private static void init(final Application app) {
        if (applicationSFL == null) {
            if (app == null) {
                applicationSFL = getApplicationByReflect();
            } else {
                applicationSFL = app;
            }
        } else {
            if (app != null && app.getClass() != applicationSFL.getClass()) {
                applicationSFL = app;
            }
        }
    }

    public static Application getApp() {
        if (applicationSFL != null) return applicationSFL;
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected() {
        NetworkInfo infoNet = getActiveNetworkInfo();
        return infoNet != null && infoNet.isConnected();
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager conneMan =
                (ConnectivityManager) getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conneMan == null) return null;
        return conneMan.getActiveNetworkInfo();
    }
}