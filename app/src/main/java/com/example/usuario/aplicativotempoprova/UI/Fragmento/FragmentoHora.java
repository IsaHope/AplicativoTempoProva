package com.example.usuario.aplicativotempoprova.UI.Fragmento;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.usuario.aplicativotempoprova.App.DB.HoraItemdb;
import com.example.usuario.aplicativotempoprova.App.DB.TempoCincoDias;
import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.MyApplication;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.card.MaterialCardView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class FragmentoHora extends DialogFragment {
    @BindView(R.id.card_view)
    MaterialCardView cardView;
    @BindView(R.id.day_name_text_view)
    AppCompatTextView diaNomeTextoView;
    @BindView(R.id.temp_text_view)
    AppCompatTextView temposTextoView;
    @BindView(R.id.min_temp_text_view)
    AppCompatTextView minimoTempoTextoView;
    @BindView(R.id.max_temp_text_view)
    AppCompatTextView maximoTempoTextoView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.animation_view)
    LottieAnimationView animacaoView;
    @BindView(R.id.chart)
    LineChart chart;
    private FastAdapter<HoraItemdb> mFastAdapt;
    private ItemAdapter<HoraItemdb> mItemAdpt;
    private TempoCincoDias tempoCincoDias;
    private Box<HoraItemdb> horaItemdbBox;
    private Activity activity;
    private Typeface typeface;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup cont, Bundle salvarInstStado){
        View view = inflater.inflate(R.layout.fragment_hourly, cont, false);
        ButterKnife.bind(this, view);
        setVariaveis();
        initRecyclerView();
//      showHoraItemdb();
        return view;
    }

    private void setVariaveis(){
        BoxStore boxStore = MyApplication.getBoxStore();
        horaItemdbBox = boxStore.boxFor(HoraItemdb.class);
        activity = getActivity();
        cardView.setCardBackgroundColor(tempoCincoDias.getColor());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(tempoCincoDias.getTd() * 1000L);
        if(AppUtil.isRTL(activity)){
            diaNomeTextoView.setText(com.example.usuario.aplicativotempoprova.Utils.Constants.DIA_DA_SEMANA_INGLES[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }else{
            diaNomeTextoView.setText(com.example.usuario.aplicativotempoprova.Utils.Constants.DIAS_DA_SEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }
        temposTextoView.setText(String.format(Locale.getDefault(), "%.0f", tempoCincoDias.getTempo()));
        minimoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", tempoCincoDias.getTempoMinimo()));
        maximoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", tempoCincoDias.getTempoMaximo()));
        animacaoView.setAnimation(AppUtil.getTempoAnimacao(tempoCincoDias.getIdTempo()));
        animacaoView.playAnimation();
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Vazir.ttf");
    }

    private void initRecyclerView(){
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
        mItemAdpt = new ItemAdapter<>();
        mFastAdapt = FastAdapter.with(mItemAdpt);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFastAdapt);
    }
/*
    private void showHoraItemdb(){
        Query<HoraItemdb> query = DbUtil.get
        DownloadManager.Query<HoraItemdb> query = DbUtil.getHoraItemdbQuery(horaItemdbBox, tempoCincoDias.getId());
        ((Query) query).subscribe().on(AndroidScheduler.mainThread()).observer(new DataObserver<List<HoraItemdb>>(){
           @Override
            public void onData(@NonNull List<HoraItemdb> data){
               if(data.size() > 0){
                   mItemAdpt.clear();
                   mItemAdpt.add(data);
                   setChartValues(data);
               }
           }
        });
    }
*/
    private void setChartValues(List<HoraItemdb> horaItemdbList) {
        List<Entry> entries = new ArrayList<>();
        int i = 0;
        if (AppUtil.isRTL(activity)) {
            int j = horaItemdbList.size() - 1;
            while (j >= 0) {
                entries.add(new Entry(i, (float) horaItemdbList.get(j).getTempo()));
                i++;
                j++;
            }
        } else {
            for (HoraItemdb horaItemdb : horaItemdbList) {
                entries.add(new Entry(i, (float) horaItemdb.getTempo()));
                i++;
            }
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setLineWidth(4f);
        dataSet.setCircleRadius(7f);
        dataSet.setHighlightEnabled(false);
        dataSet.setCircleColor(Color.parseColor("#33b5e5"));
        dataSet.setValueTextSize(12);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setValueTypeface(typeface);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f", value);
            }
        });

        LineData lineData = new LineData(dataSet);
        chart.getDescription().setEnabled(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.setScaleEnabled(false);
        chart.setData(lineData);
        chart.animateY(1000);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wmlp = new WindowManager.LayoutParams();
        wmlp.copyFrom(dialog.getWindow().getAttributes());
        wmlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(wmlp);
        return dialog;
    }

    public void setTempoCincoDias(TempoCincoDias tempoCincoDias) {
        this.tempoCincoDias = tempoCincoDias;
    }

    @OnClick(R.id.close_button)
    void close() {
        dismiss();
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}
