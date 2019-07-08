package com.example.usuario.aplicativotempoprova.UI.Fragmento;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.usuario.aplicativotempoprova.App.DB.VariosTemposDias;
import com.example.usuario.aplicativotempoprova.App.InfoCidade;
import com.example.usuario.aplicativotempoprova.App.TempoDias.ListaItem;
import com.example.usuario.aplicativotempoprova.App.TempoDias.VariosTempoDiasResposta;
import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Servico.ApiServico;
import com.example.usuario.aplicativotempoprova.Utils.ApiCliente;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.DbUtil;
import com.example.usuario.aplicativotempoprova.Utils.MyApplication;
import com.github.pwittchen.prefser.library.rx2.Prefser;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class VariosFragmentoDias extends DialogFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeCont;
    private String defaultLangg = "en";
    private CompositeDisposable cDisposable = new CompositeDisposable();
    private FastAdapter<VariosTemposDias> mFastAdpter;
    private ItemAdapter<VariosTemposDias> mItemAdpter;
    private Activity activity;
    private Box<VariosTemposDias> variosTemposDiasBox;
    private Prefser prefser;
    private String apiKey;

    @Override
    public View onCreateView(@NonNull LayoutInflater lInflater, ViewGroup cont,
                             Bundle salvaInstaStado){
        View view = lInflater.inflate(R.layout.fragment_multiple_days,
                cont, false);
        ButterKnife.bind(this, view);
        initVariaveis();
        initSwipeView();
        initRecyclerView();
        showStoreVariosTemposDias();
        checkTimePass();
        return view;
    }

    private void initVariaveis(){
        activity = getActivity();
        prefser = new Prefser(activity);
        BoxStore boxStore = MyApplication.getBoxStore();
        variosTemposDiasBox = boxStore.boxFor(VariosTemposDias.class);
    }

    private void initSwipeView(){
        swipeCont.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeCont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestTempo();
            }
        });
    }

    private void requestTempo(){
        long ultmUpdate = prefser.get(com.example.usuario.aplicativotempoprova.Utils.Constants.LAST_STORE_VARIOS_DIAS, Long.class, 0L);
        if(AppUtil.isTimePass(ultmUpdate)){
            checkCidadeInfoExist();
        }else{
            swipeCont.setRefreshing(false);
        }
    }

    public void initRecyclerView(){
        @SuppressLint("WrongConstant") LinearLayoutManager llManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llManager);
        mItemAdpter = new ItemAdapter<>();
        mFastAdpter = FastAdapter.with(mItemAdpter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFastAdpter);
    }

    public void showStoreVariosTemposDias(){
        Query<VariosTemposDias> querry = DbUtil.getVariosTemposDiasQuery(variosTemposDiasBox);
        ((Query) querry).subscribe().on(AndroidScheduler.mainThread()).observer(new DataObserver<List<VariosTemposDias>>(){
            @Override
            public void onData(@NonNull List<VariosTemposDias> data){
                if(data.size() > 0){
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            data.remove(0);
                            mItemAdpter.clear();
                            mItemAdpter.add(data);
                        }
                    }, 500);
                }
            }
        });
    }

    public void checkTimePass(){
        apiKey = getResources().getString(R.string.open_weather_map_api);
        if(prefser.contains(com.example.usuario.aplicativotempoprova.Utils.Constants.LAST_STORE_VARIOS_DIAS)){
            requestTempo();
        }else{
            checkCidadeInfoExist();
        }
    }

    private void checkCidadeInfoExist(){
        InfoCidade infoCidade = prefser.get(com.example.usuario.aplicativotempoprova.Utils.Constants.CIDADE_INFO, InfoCidade.class, null);
        if(infoCidade != null){
            if(AppUtil.isNetworkConnected()){
                requestTempo(infoCidade.getNome());
            }else{
                Toast.makeText(activity, getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                swipeCont.setRefreshing(false);
            }
        }
    }

    private void requestTempo(String cidadeNome){
        ApiServico apiServico = ApiCliente.getCliente().create(ApiServico.class);
        cDisposable.add(apiServico.getVariosTempoDias(
                cidadeNome, com.example.usuario.aplicativotempoprova.Utils.Constants.UNITS, defaultLangg,16, apiKey).subscribeOn(Schedulers.io())
                .observeOn((Scheduler) AndroidScheduler.mainThread())
                .subscribeWith(new DisposableSingleObserver<VariosTempoDiasResposta>(){
                    @Override
                    public void onSuccess(VariosTempoDiasResposta resposta){
                        handlerVariosDiasResposta(resposta);
                        swipeCont.setRefreshing(false);
                    }
                    @Override
                    public void onError(Throwable e){
                        swipeCont.setRefreshing(false);
                        Log.e("MainActivity", "onError: " + e.getMessage());
                    }
                }));
    }

    private void handlerVariosDiasResposta(VariosTempoDiasResposta resposta){
        variosTemposDiasBox.removeAll();
        List<ListaItem> listaItems = resposta.getLista();
        for(ListaItem listaItem : listaItems){
            VariosTemposDias variosTemposDias = new VariosTemposDias();
            variosTemposDias.setTd(listaItem.getTd());
            variosTemposDias.setMaximooTempo(listaItem.getTempotempo().getMaximo());
            variosTemposDias.setMinimoTempo(listaItem.getTempotempo().getMinimo());
            variosTemposDias.setTempoId(listaItem.getTempo().get(0).getId());
            variosTemposDiasBox.put(variosTemposDias);
        }
        prefser.put(com.example.usuario.aplicativotempoprova.Utils.Constants.LAST_STORE_VARIOS_DIAS, System.currentTimeMillis());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle salvarInstStado){
        Dialog dialog = super.onCreateDialog(salvarInstStado);
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        cDisposable.dispose();
    }
}