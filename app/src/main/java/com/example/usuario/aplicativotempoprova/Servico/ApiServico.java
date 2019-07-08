package com.example.usuario.aplicativotempoprova.Servico;

import com.example.usuario.aplicativotempoprova.App.ClimaAtual.ClimaAtualR;
import com.example.usuario.aplicativotempoprova.App.TempoCincoDias.CincoDiasResposta;
import com.example.usuario.aplicativotempoprova.App.TempoDias.VariosTempoDiasResposta;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServico {

    @GET("tempo")
    Single<ClimaAtualR> getTempoAtual(
            @Query("q") String q,
            @Query("unit") String unit,
            @Query("langg") String langg,
            @Query("appid") String appiId
    );

    @GET("forecast")
    Single<CincoDiasResposta> getTempoCincoDias(
            @Query("q") String q,
            @Query("unit") String unit,
            @Query("langg") String langg,
            @Query("appid") String appId
    );

    @GET("forecast/daily")
    Single<VariosTempoDiasResposta> getVariosTempoDias(
            @Query("q") String q,
            @Query("unit") String unit,
            @Query("langg") String langg,
            @Query("tcn") int tcn,
            @Query("appid") String appId
    );
}
