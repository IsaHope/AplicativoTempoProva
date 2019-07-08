package com.example.usuario.aplicativotempoprova.Utils;

import android.app.DownloadManager;

import com.example.usuario.aplicativotempoprova.App.DB.ClimaAtualdb;
import com.example.usuario.aplicativotempoprova.App.DB.HoraItemdb;
import com.example.usuario.aplicativotempoprova.App.DB.TempoCincoDias;
import com.example.usuario.aplicativotempoprova.App.DB.VariosTemposDias;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class DbUtil {

    public static Query<ClimaAtualdb> getClimaAtualdbQuery(Box<ClimaAtualdb> climaAtualdbBox) {
        return climaAtualdbBox.query().build();
    }

    public static Query<TempoCincoDias> getTempoCincoDiasQuery(Box<TempoCincoDias> tempoCincoDiasBox) {
        return tempoCincoDiasBox.query().build();
    }
/*
    public static DownloadManager.Query getHoraItemdbQuery(Box<HoraItemdb> horaItemdbBox, long tempoCincoDiasId) {
        return horaItemdbBox.query()
                .equal(HoraItemdb_.tempoCincoDiasId, tempoCincoDiasId)
                .build();
    }
*/
    public static Query<VariosTemposDias> getVariosTemposDiasQuery(Box<VariosTemposDias> variosTemposDiasBox) {
        return variosTemposDiasBox.query().build();
    }
}
