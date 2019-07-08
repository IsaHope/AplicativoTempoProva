package com.example.usuario.aplicativotempoprova.App.DB;

import android.content.Context;
import android.view.View;

import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.DateConverter;
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
public class VariosTemposDias extends AbstractItem<VariosTemposDias.MyViewHolder> {

    @Id
    private long id;
    private int td;
    private int tempoId;
    private double minimoTempo;
    private double maximooTempo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTd() {
        return td;
    }

    public void setTd(int td) {
        this.td = td;
    }

    public int getTempoId() {
        return tempoId;
    }

    public void setTempoId(int tempoId) {
        this.tempoId = tempoId;
    }

    public double getMinimoTempo() {
        return minimoTempo;
    }

    public void setMinimoTempo(double minimoTempo) {
        this.minimoTempo = minimoTempo;
    }

    public double getMaximooTempo() {
        return maximooTempo;
    }

    public void setMaximooTempo(double maximooTempo) {
        this.maximooTempo = maximooTempo;
    }

    @NonNull
    public MyViewHolder getViewHolder(@NonNull View v){
        return new MyViewHolder(v);
    }

    @Override
    public int getType(){
        return R.id.fastadapter_item_adapter;
    }

    @Override
    public int getLayoutRes(){
        return R.layout.multiple_days_item;
    }

    protected static class MyViewHolder extends FastAdapter.ViewHolder<VariosTemposDias>{
        Context context;
        View view;

        @BindView(R.id.day_name_text_view)
        AppCompatTextView diaNomeTextoView;

        @BindView(R.id.date_text_view)
        AppCompatTextView dataTextoView;

        @BindView(R.id.weather_image_view)
        AppCompatImageView tempoImagemView;

        @BindView(R.id.max_temp_text_view)
        AppCompatTextView maximoTempoTextoView;

        @BindView(R.id.min_temp_text_view)
        AppCompatTextView minimoTempoTextoView;

        MyViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
            this.context = view.getContext();
        }

        @Override
        public void bindView(@NonNull VariosTemposDias itemv, @NonNull List<Object> loads) {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTimeInMillis(itemv.getTd() * 1000L);
            if (AppUtil.isRTL(context)) {
                DateConverter converter = new DateConverter(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH));
                diaNomeTextoView.setText(com.example.usuario.aplicativotempoprova.Utils.Constants.DIA_DA_SEMANA_INGLES[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                dataTextoView.setText(String.format(Locale.getDefault(), "%d %s", converter.getIranianDay()
                        , com.example.usuario.aplicativotempoprova.Utils.Constants.NOME_MES_INGLES[converter.getIranianMonth() - 1]));
            } else {
                diaNomeTextoView.setText(com.example.usuario.aplicativotempoprova.Utils.Constants.DIAS_DA_SEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                dataTextoView.setText(String.format(Locale.getDefault(), "%s %d",
                        com.example.usuario.aplicativotempoprova.Utils.Constants.NOME_MES[calendar.get(Calendar.MONTH)], calendar.get(Calendar.DAY_OF_MONTH)));
            }
            minimoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f°", itemv.getMinimoTempo()));
            maximoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f°", itemv.getMaximooTempo()));
            AppUtil.setIconeTempo(context, tempoImagemView, itemv.getTempoId());
            }

            @Override
            public void unbindView(@NonNull VariosTemposDias itemv){

            }
        }
    }