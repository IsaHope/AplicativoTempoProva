package com.example.usuario.aplicativotempoprova.App.DB;

import android.content.Context;
import android.view.View;

import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class HoraItemdb extends AbstractItem<HoraItemdb.MyViewHolder> {

    @Id
    private long id;
    private long TempoCincoDiasId;
    private int Td;
    private double tempo;
    private int tempoCodg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTempoCincoDiasId() {
        return TempoCincoDiasId;
    }

    public void setTempoCincoDiasId(long tempoCincoDiasId) {
        TempoCincoDiasId = tempoCincoDiasId;
    }

    public int getTd() {
        return Td;
    }

    public void setTd(int td) {
        Td = td;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public int getTempoCodg() {
        return tempoCodg;
    }

    public void setTempoCodg(int tempoCodg) {
        this.tempoCodg = tempoCodg;
    }

    @NonNull
    @Override
    public MyViewHolder getViewHolder(@NonNull View v){
        return new MyViewHolder(v);
    }

    @Override
    public int getType(){
        return R.id.fastadapter_item_adapter;
    }

    @Override
    public int getLayoutRes(){
        return R.layout.weather_hourly_item;
    }

    protected static class MyViewHolder extends FastAdapter.ViewHolder<HoraItemdb>{
        View view;
        Context context;

        @BindView(R.id.time_text_view)
        AppCompatTextView timeTextoView;

        @BindView(R.id.weather_image_view)
        AppCompatImageView tempoImagemView;

        @BindView(R.id.temp_text_view)
        AppCompatTextView tempoTextoView;

        MyViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
            this.context = view.getContext();
        }

        @Override
        public void bindView(@NonNull HoraItemdb itemh, @NonNull List<Object> loads){
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTimeInMillis(itemh.getTd() * 1000L);
            timeTextoView.setText(AppUtil.getTime(calendar, context));
            tempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", itemh.getTempo()));
            AppUtil.setIconeTempo(context, tempoImagemView, itemh.tempoCodg);
        }

        @Override
        public void unbindView(@NonNull HoraItemdb itemh){

        }
    }
}
