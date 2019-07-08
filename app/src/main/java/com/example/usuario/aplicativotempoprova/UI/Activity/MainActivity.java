package com.example.usuario.aplicativotempoprova.UI.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.usuario.aplicativotempoprova.App.ClimaAtual.ClimaAtualR;
import com.example.usuario.aplicativotempoprova.App.DB.ClimaAtualdb;
import com.example.usuario.aplicativotempoprova.App.DB.HoraItemdb;
import com.example.usuario.aplicativotempoprova.App.DB.TempoCincoDias;
import com.example.usuario.aplicativotempoprova.App.InfoCidade;
import com.example.usuario.aplicativotempoprova.App.TempoCincoDias.CincoDiasResposta;
import com.example.usuario.aplicativotempoprova.App.TempoCincoDias.ItemHora;
import com.example.usuario.aplicativotempoprova.App.TempoDias.ListaItem;
import com.example.usuario.aplicativotempoprova.App.TempoDias.VariosTempoDiasResposta;
import com.example.usuario.aplicativotempoprova.UI.Fragmento.FragmentoAbout;
import com.example.usuario.aplicativotempoprova.UI.Fragmento.VariosFragmentoDias;
import com.example.usuario.aplicativotempoprova.R;
import com.example.usuario.aplicativotempoprova.Servico.ApiServico;
import com.example.usuario.aplicativotempoprova.Utils.ApiCliente;
import com.example.usuario.aplicativotempoprova.Utils.AppUtil;
import com.example.usuario.aplicativotempoprova.Utils.Constants;
import com.example.usuario.aplicativotempoprova.Utils.DbUtil;
import com.example.usuario.aplicativotempoprova.Utils.MyApplication;
import com.example.usuario.aplicativotempoprova.Utils.SnackbarUtil;
import com.example.usuario.aplicativotempoprova.Utils.TextViewFactory;
import com.github.pwittchen.prefser.library.rx2.Prefser;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindArray;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextSwitcher tempTextView;
    TextSwitcher descriptionTextView;
    TextSwitcher humidityTextView;
    @BindArray(R.array.mdcolor_500)
    @ColorInt
    int[] colors;
    @BindArray(R.array.mdcolor_500_alpha)
    @ColorInt
    int[] colorsAlpha;
    LottieAnimationView animationView;
    Toolbar toolbar;
    MaterialSearchView searchView;
    AppCompatTextView cityNameTextView;
    TextSwitcher windTextView;
    SwipeRefreshLayout swipeContainer;
    AppCompatImageView noCityImageView;
    RelativeLayout emptyLayout;
    NestedScrollView nestedScrollView;
    BottomAppBar bar;
    private FastAdapter<TempoCincoDias> mFastAdapter;
    private ItemAdapter<TempoCincoDias> mItemAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private String defaultLang = "en";
    private List<TempoCincoDias> fiveDayWeathers;
    private ApiServico apiService;
    private TempoCincoDias todayFiveDayWeather;
    private Prefser prefser;
    private Box<ClimaAtualdb> currentWeatherBox;
    private Box<TempoCincoDias> fiveDayWeatherBox;
    private Box<HoraItemdb> itemHourlyDBBox;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();
    private boolean isLoad = false;
    private InfoCidade cityInfo;
    private String apiKey;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initSearchView();
        initValues();
        setupTextSwitchers();
        initRecyclerView();
        showStoredCurrentWeather();
        showStoredFiveDayWeather();
        checkLastUpdate();
    }

    private void initSearchView() {
        searchView.setVoiceSearch(false);
        searchView.setHint(getString(R.string.search_label));
        searchView.setCursorDrawable(R.drawable.custom_curosr);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                requestWeather(query, true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initValues() {
        prefser = new Prefser(this);
        apiService = ApiCliente.getCliente().create(ApiServico.class);
        BoxStore boxStore = MyApplication.getBoxStore();
        currentWeatherBox = boxStore.boxFor(ClimaAtualdb.class);
        fiveDayWeatherBox = boxStore.boxFor(TempoCincoDias.class);
        itemHourlyDBBox = boxStore.boxFor(HoraItemdb.class);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                cityInfo = prefser.get(Constants.CIDADE_INFO, InfoCidade.class, null);
                if (cityInfo != null) {
                    long lastStored = prefser.get(Constants.LAST_STORED_CURRENT, Long.class, 0L);
                    if (AppUtil.isTimePass(lastStored)) {
                        requestWeather(cityInfo.getNome(), false);
                    } else {
                        swipeContainer.setRefreshing(false);
                    }
                } else {
                    swipeContainer.setRefreshing(false);
                }
            }

        });
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutFragment();
            }
        });
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
    }

    private void setupTextSwitchers() {
        tempTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.TempTextView, true, typeface));
        tempTextView.setInAnimation(MainActivity.this, R.anim.slide_in_right);
        tempTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_left);
        descriptionTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.DescriptionTextView, true, typeface));
        descriptionTextView.setInAnimation(MainActivity.this, R.anim.slide_in_right);
        descriptionTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_left);
        humidityTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.HumidityTextView, false, typeface));
        humidityTextView.setInAnimation(MainActivity.this, R.anim.slide_in_bottom);
        humidityTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_top);
        windTextView.setFactory(new TextViewFactory(MainActivity.this, R.style.WindSpeedTextView, false, typeface));
        windTextView.setInAnimation(MainActivity.this, R.anim.slide_in_bottom);
        windTextView.setOutAnimation(MainActivity.this, R.anim.slide_out_top);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mItemAdapter = new ItemAdapter<>();
        mFastAdapter = FastAdapter.with(mItemAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mFastAdapter);
        recyclerView.setFocusable(false);
        /*
        mFastAdapter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TempoCincoDias item;
                FragmentoHora fragHora = new FragmentoHora();
                fragHora.setTempoCincoDias(item);
                AppUtil.showFragment(fragHora, getSupportFragmentManager(), true);
            }
        });
        */
    }

    private void showStoredCurrentWeather() {
        Query<ClimaAtualdb> query = DbUtil.getClimaAtualdbQuery(currentWeatherBox);
        query.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<ClimaAtualdb>>() {
                    @Override
                    public void onData(@NonNull List<ClimaAtualdb> data) {
                        if (data.size() > 0) {
                            hideEmptyLayout();
                            ClimaAtualdb currentWeather = data.get(0);
                            if (isLoad) {
                                tempTextView.setText(String.format(Locale.getDefault(), "%.0f°", currentWeather.getTempo()));
                                descriptionTextView.setText(AppUtil.getTempoStatus(currentWeather.getIdTempo(), AppUtil.isRTL(MainActivity.this)));
                                humidityTextView.setText(String.format(Locale.getDefault(), "%d%%", currentWeather.getHumidade()));
                                windTextView.setText(String.format(Locale.getDefault(), getResources().getString(R.string.wind_unit_label), currentWeather.getVelocidadeVento()));
                            } else {
                                tempTextView.setCurrentText(String.format(Locale.getDefault(), "%.0f°", currentWeather.getTempo()));
                                descriptionTextView.setCurrentText(AppUtil.getTempoStatus(currentWeather.getIdTempo(), AppUtil.isRTL(MainActivity.this)));
                                humidityTextView.setCurrentText(String.format(Locale.getDefault(), "%d%%", currentWeather.getHumidade()));
                                windTextView.setCurrentText(String.format(Locale.getDefault(), getResources().getString(R.string.wind_unit_label), currentWeather.getVelocidadeVento()));
                            }
                            animationView.setAnimation(AppUtil.getTempoAnimacao(currentWeather.getIdTempo()));
                            animationView.playAnimation();
                        }
                    }
                });
    }

    private void showStoredFiveDayWeather() {
        Query<TempoCincoDias> query = DbUtil.getTempoCincoDiasQuery(fiveDayWeatherBox);
        ((Query) query).subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<TempoCincoDias>>() {
                    @Override
                    public void onData(@NonNull List<TempoCincoDias> data) {
                        if (data.size() > 0) {
                            todayFiveDayWeather = data.remove(0);
                            mItemAdapter.clear();
                            mItemAdapter.add(data);
                        }
                    }
                });
    }

    private void checkLastUpdate() {
        cityInfo = prefser.get(Constants.CIDADE_INFO, InfoCidade.class, null);
        if (cityInfo != null) {
            cityNameTextView.setText(String.format("%s, %s", cityInfo.getNome(), cityInfo.getPais()));
            if (prefser.contains(Constants.LAST_STORED_CURRENT)) {
                long lastStored = prefser.get(Constants.LAST_STORED_CURRENT, Long.class, 0L);
                if (AppUtil.isTimePass(lastStored)) {
                    requestWeather(cityInfo.getNome(), false);
                }
            } else {
                requestWeather(cityInfo.getNome(), false);
            }
        } else {
            showEmptyLayout();
        }

    }


    private void requestWeather(String cityName, boolean isSearch) {
        if (AppUtil.isNetworkConnected()) {
            getCurrentWeather(cityName, isSearch);
            getFiveDaysWeather(cityName);
        } else {
            SnackbarUtil
                    .with(swipeContainer)
                    .setMessage(getString(R.string.no_internet_message))
                    .setDuration(SnackbarUtil.LENGTH_LONG)
                    .showError();
            swipeContainer.setRefreshing(false);
        }
    }

    private void getCurrentWeather(String cityName, boolean isSearch) {
        apiKey = getResources().getString(R.string.open_weather_map_api);
        disposable.add(
                apiService.getTempoAtual(
                        cityName, Constants.UNITS, defaultLang, apiKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn((Scheduler) AndroidScheduler.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ClimaAtualR>() {
                            @Override
                            public void onSuccess(ClimaAtualR climaAtualR) {
                                isLoad = true;
                                storeCurrentWeather(climaAtualR);
                                storeCityInfo(climaAtualR);
                                swipeContainer.setRefreshing(false);
                                if (isSearch) {
                                    prefser.remove(Constants.LAST_STORE_VARIOS_DIAS);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                swipeContainer.setRefreshing(false);
                                try {
                                    HttpException error = (HttpException) e;
                                    handleErrorCode(error);
                                } catch (Exception exception) {
                                    e.printStackTrace();
                                }
                            }
                        })

        );
    }

    private void handleErrorCode(HttpException error) {
        if (error.code() == 404) {
            SnackbarUtil
                    .with(swipeContainer)
                    .setMessage(getString(R.string.no_city_found_message))
                    .setDuration(SnackbarUtil.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.search_label), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchView.showSearch();
                        }
                    })
                    .showWarning();

        } else if (error.code() == 401) {
            SnackbarUtil
                    .with(swipeContainer)
                    .setMessage(getString(R.string.invalid_api_key_message))
                    .setDuration(SnackbarUtil.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.ok_label), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .showError();

        } else {
            SnackbarUtil
                    .with(swipeContainer)
                    .setMessage(getString(R.string.network_exception_message))
                    .setDuration(SnackbarUtil.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.retry_label), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cityInfo != null) {
                                requestWeather(cityInfo.getNome(), false);
                            } else {
                                searchView.showSearch();
                            }
                        }
                    })
                    .showWarning();
        }
    }

    private void showEmptyLayout() {
        Glide.with(MainActivity.this).load(R.drawable.no_city).into(noCityImageView);
        emptyLayout.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.GONE);
    }

    private void hideEmptyLayout() {
        emptyLayout.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }


    private void storeCurrentWeather(ClimaAtualR response) {
        ClimaAtualdb currentWeather = new ClimaAtualdb();
        currentWeather.setTempo(response.getMain().getTempos());
        currentWeather.setHumidade(response.getMain().getHumidade());
        currentWeather.setDescricao(response.getTempo().get(0).getDescricao());
        currentWeather.setMain(response.getTempo().get(0).getMain());
        currentWeather.setIdTempo(response.getTempo().get(0).getId());
        currentWeather.setDirecaoVento(response.getVento().getDirecao());
        currentWeather.setVelocidadeVento(response.getVento().getVelocidade());
        currentWeather.setArmazenarTempo(System.currentTimeMillis());
        prefser.put(Constants.LAST_STORED_CURRENT, System.currentTimeMillis());
        if (!currentWeatherBox.isEmpty()) {
            currentWeatherBox.removeAll();
            currentWeatherBox.put(currentWeather);
        } else {
            currentWeatherBox.put(currentWeather);
        }
    }

    private void storeCityInfo(ClimaAtualR response) {
        InfoCidade cityInfo = new InfoCidade();
        cityInfo.setPais(response.getSys().getPais());
        cityInfo.setId(response.getId());
        cityInfo.setNome(response.toString());
        prefser.put(Constants.CIDADE_INFO, cityInfo);
        cityNameTextView.setText(String.format("%s, %s", cityInfo.getNome(), cityInfo.getPais()));
    }

    private void getFiveDaysWeather(String cityName) {
        disposable.add(
                apiService.getVariosTempoDias(
                        cityName, Constants.UNITS, defaultLang, 5, apiKey)
                        .subscribeOn(Schedulers.io())
                        .observeOn((Scheduler) AndroidScheduler.mainThread())
                        .subscribeWith(new DisposableSingleObserver<VariosTempoDiasResposta>() {
                            @Override
                            public void onSuccess(VariosTempoDiasResposta response) {
                                handleFiveDayResponse(response, cityName);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void handleFiveDayResponse(VariosTempoDiasResposta response, String cityName) {
        fiveDayWeathers = new ArrayList<>();
        List<ListaItem> list = response.getLista();
        int day = 0;
        for (ListaItem item : list) {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            Calendar newCalendar = AppUtil.addDias(calendar, day);
            TempoCincoDias tempoCincoDias = new TempoCincoDias();
            tempoCincoDias.setIdTempo(item.getTempo().get(0).getId());
            tempoCincoDias.setTd(item.getTd());
            tempoCincoDias.setTempoMaximo(item.getTempotempo().getMaximo());
            tempoCincoDias.setTempoMinimo(item.getTempotempo().getMinimo());
            tempoCincoDias.setTempo(item.getTempotempo().getDia());
            tempoCincoDias.setColor(colors[day]);
            tempoCincoDias.setColorAlpha(colorsAlpha[day]);
            tempoCincoDias.setTimestampStart(AppUtil.getStartDiaTimeStamp(newCalendar));
            tempoCincoDias.setTimestampEnd(AppUtil.getEndDiaTimestamp(newCalendar));
            fiveDayWeathers.add(tempoCincoDias);
            day++;
        }
        getFiveDaysHourlyWeather(cityName);
    }

    private void getFiveDaysHourlyWeather(String cityName) {
        disposable.add(
                apiService.getTempoCincoDias(cityName, Constants.UNITS, defaultLang, apiKey).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<CincoDiasResposta>() {
                    @Override
                    public void onSuccess(CincoDiasResposta cincoDiasResposta) {
                        handleFiveDayHourlyResponse(cincoDiasResposta);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                })
        );
    }

    private void handleFiveDayHourlyResponse(CincoDiasResposta response) {
        if (!fiveDayWeatherBox.isEmpty()) {
            fiveDayWeatherBox.removeAll();
        }
        if (!itemHourlyDBBox.isEmpty()) {
            itemHourlyDBBox.removeAll();
        }
        for (TempoCincoDias fiveDayWeather : fiveDayWeathers) {
            long fiveDayWeatherId = fiveDayWeatherBox.put(fiveDayWeather);
            ArrayList<ItemHora> listItemHourlies = new ArrayList(response.getLista());
            for (ItemHora itemHourly : listItemHourlies) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTimeInMillis(itemHourly.getTd() * 1000L);
                if (calendar.getTimeInMillis()
                        <= fiveDayWeather.getTimestampEnd()
                        && calendar.getTimeInMillis()
                        > fiveDayWeather.getTimestampStart()) {
                    HoraItemdb itemHourlyDB = new HoraItemdb();
                    itemHourlyDB.setTd(itemHourly.getTd());
                    itemHourlyDB.setTempoCincoDiasId(fiveDayWeatherId);
                    itemHourlyDB.setTempo(itemHourly.getMain().getTempos());
                    itemHourlyDB.setTempoCodg(itemHourly.getTempo().get(0).getId());
                    itemHourlyDBBox.put(itemHourlyDB);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @OnClick(R.id.next_days_button)
    public void multipleDays() {
        AppUtil.showFragment(new VariosFragmentoDias(), getSupportFragmentManager(), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void showAboutFragment() {
        AppUtil.showFragment(new FragmentoAbout(), getSupportFragmentManager(), true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        Context newContext = MyApplication.localeManager.setLocale(base);
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newContext));
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.search_text_view)
    public void handleSearchTextClick() {
        searchView.showSearch();
    }
}