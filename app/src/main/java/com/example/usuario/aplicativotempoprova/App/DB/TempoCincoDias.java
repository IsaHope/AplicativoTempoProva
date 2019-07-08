package com.example.usuario.aplicativotempoprova.App.DB;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.Constants;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TempoCincoDias extends AbstractItem<TempoCincoDias.MyViewHolder> {

    @Id
    private long id;
    private int td;
    private double tempo;
    private double tempoMinimo;
    private double tempoMaximo;
    private int idTempo;
    private long timestampStart;
    private long timestampEnd;

    private @ColorInt
    int color;

    private @ColorInt
    int colorAlpha;

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

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public double getTempoMinimo() {
        return tempoMinimo;
    }

    public void setTempoMinimo(double tempoMinimo) {
        this.tempoMinimo = tempoMinimo;
    }

    public double getTempoMaximo() {
        return tempoMaximo;
    }

    public void setTempoMaximo(double tempoMaximo) {
        this.tempoMaximo = tempoMaximo;
    }

    public int getIdTempo() {
        return idTempo;
    }

    public void setIdTempo(int idTempo) {
        this.idTempo = idTempo;
    }

    public long getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(long timestampStart) {
        this.timestampStart = timestampStart;
    }

    public long getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(long timestampEnd) {
        this.timestampEnd = timestampEnd;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColorAlpha() {
        return colorAlpha;
    }

    public void setColorAlpha(int colorAlpha) {
        this.colorAlpha = colorAlpha;
    }

    @NonNull
    @Override
    public MyViewHolder getViewHolder(@NonNull View v){
        return new MyViewHolder(v);
    }

    public int getType(){
        return R.id.fastadapter_item_adapter;
    }

    @Override
    public int getLayoutRes(){
        return R.layout.weather_day_item;
    } //


    protected static class MyViewHolder extends FastAdapter.ViewHolder<TempoCincoDias>{
        Context context;
        View view;

        @BindView(R.id.day_name_text_view)
        AppCompatTextView diaNommeTextoView;

        @BindView(R.id.temp_text_view)
        AppCompatTextView tempoTextoView;

        @BindView(R.id.min_temp_text_view)
        AppCompatTextView minimoTempoTextoView;

        @BindView(R.id.max_temp_text_view)
        AppCompatTextView maximoTempoTextoView;

        @BindView(R.id.weather_image_view)
        AppCompatImageView tempoImagemView;

        @BindView(R.id.card_view)
        AppCompatTextView cardView;

        @BindView(R.id.shadow_view)
        AppCompatTextView shadowView;

        MyViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
            this.context = view.getContext();
        }

        @Override
        public void bindView(@NonNull TempoCincoDias item, @NonNull List<Object> loads){
            cardView.setBackgroundColor(item.getColor());
            int[] cores = {
                    Color.TRANSPARENT,
                    item.getColorAlpha(),
                    Color.TRANSPARENT
            };
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTimeInMillis(item.getTd() * 1000L);
            if(AppUtil.isRTL(context)){
                diaNommeTextoView.setText(Constants.DIA_DA_SEMANA_INGLES[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            }
            else{
                diaNommeTextoView.setText(Constants.DIAS_DA_SEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
            }
            tempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", item.getTempo()));
            minimoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", item.getTempoMinimo()));
            maximoTempoTextoView.setText(String.format(Locale.getDefault(), "%.0f", item.getTempoMaximo()));
            AppUtil.setIconeTempo(context, tempoImagemView, item.idTempo);
            GradientDrawable shape = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, cores);
            shape.setShape(GradientDrawable.OVAL);
            shadowView.setBackground(shape);
        }

        @Override
        public void unbindView(@NotNull TempoCincoDias item) {

        }
    }
}
