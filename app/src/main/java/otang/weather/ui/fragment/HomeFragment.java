package otang.weather.ui.fragment;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.github.mikephil.charting.data.Entry;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import java.util.TimeZone;
import otang.weather.R;
import otang.weather.api.ApiClient;
import otang.weather.api.ApiInterface;
import otang.weather.databinding.ActivityMainBinding;
import otang.weather.databinding.HomeFragmentBinding;
import otang.weather.model.forecast.ForecastResponse;
import otang.weather.model.weather.WeatherResponse;
import otang.weather.util.AppUtils;
import otang.weather.util.ColourUtils;
import otang.weather.util.DpUtils;
import otang.weather.util.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

	private HomeFragmentBinding binding;

	public HomeFragment() {
	}

	private ApiInterface apiInterface;
	private PrefUtils prefUtils;
	private ActivityMainBinding mainBinding;
	private DpUtils dpUtils;

	String lat, lon, unit, lang, appid;

	double latitude = 0;
	double longitude = 0;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		binding = HomeFragmentBinding.inflate(LayoutInflater.from(getActivity()));
		mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(getActivity()));
		dpUtils = new DpUtils(getActivity());
		prefUtils = new PrefUtils(getActivity());
		apiInterface = ApiClient.getApiInterface();
		get();
	}

	private void get() {
		latitude = (double) prefUtils.getFloat("lat");
		longitude = (double) prefUtils.getFloat("lon");
		lat = String.valueOf(latitude);
		lon = String.valueOf(longitude);
		unit = "metric";
		lang = "id";
		appid = getString(R.string.weather_api_key);
		if (latitude != 0 && longitude != 0) {
			getWeather();
			getForecast();
		} else {
			binding.srl.setRefreshing(false);
			loadData();
			AppUtils.showToast(getActivity(), "No Location");
		}

	}

	private void loadData() {
		int anim = prefUtils.getInteger("anim");
		String temp = prefUtils.getString("temp");
		String desc = prefUtils.getString("desc");
		String sunrise = prefUtils.getString("sunrise");
		String sunset = prefUtils.getString("sunset");
		String humi = prefUtils.getString("humi");
		String press = prefUtils.getString("press");
		String min = prefUtils.getString("min");
		String max = prefUtils.getString("max");
		String visib = prefUtils.getString("visib");
		String wind = prefUtils.getString("wind");
		binding.lav.setAnimation(anim);
		binding.tvTemp.setText(temp);
		binding.tvDesc.setText(desc);
		binding.tvSunrise.setText(sunrise);
		binding.tvSunset.setText(sunset);
		binding.tvHumidity.setText(humi);
		binding.tvPressure.setText(press);
		binding.tvTempMin.setText(min);
		binding.tvTempMax.setText(max);
		binding.tvVisibility.setText(visib);
		binding.tvWindSpeed.setText(wind);
	}

	private void getWeather() {
		apiInterface.getTodayWeather(lat, lon, unit, lang, appid).enqueue(new Callback<WeatherResponse>() {
			@Override
			public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
				if (response.body() != null) {
					WeatherResponse weatherResponse = response.body();
					int anim = AppUtils.getWeatherAnimation(weatherResponse.getWeather().get(0).getId());
					String temp = String.valueOf(Math.round(weatherResponse.getMain().getTemp())) + "°";
					String desc = weatherResponse.getWeather().get(0).getDescription();
					String sunrise = getTime(weatherResponse.getSys().getSunrise());
					String sunset = getTime(weatherResponse.getSys().getSunset());
					String humi = String.valueOf(weatherResponse.getMain().getHumidity()) + "%";
					String press = String.valueOf(weatherResponse.getMain().getPressure()) + " hPa";
					String min = String.valueOf(Math.round(weatherResponse.getMain().getTempMin()) + "°C");
					String max = String.valueOf(Math.round(weatherResponse.getMain().getTempMax()) + "°C");
					String visib = String.valueOf(weatherResponse.getVisibility()) + " Meter";
					String wind = String.valueOf(weatherResponse.getWind().getSpeed()) + " Km/h";
					binding.lav.setAnimation(anim);
					binding.tvTemp.setText(temp);
					binding.tvDesc.setText(desc);
					binding.tvSunrise.setText(sunrise);
					binding.tvSunset.setText(sunset);
					binding.tvHumidity.setText(humi);
					binding.tvPressure.setText(press);
					binding.tvTempMin.setText(min);
					binding.tvTempMax.setText(max);
					binding.tvVisibility.setText(visib);
					binding.tvWindSpeed.setText(wind);
					// Save data
					prefUtils.saveAs("anim", anim);
					prefUtils.saveAs("temp", temp);
					prefUtils.saveAs("desc", desc);
					prefUtils.saveAs("sunrise", sunrise);
					prefUtils.saveAs("sunset", sunset);
					prefUtils.saveAs("humi", humi);
					prefUtils.saveAs("press", press);
					prefUtils.saveAs("min", min);
					prefUtils.saveAs("max", max);
					prefUtils.saveAs("visib", visib);
					prefUtils.saveAs("wind", wind);
				}
			}

			@Override
			public void onFailure(Call<WeatherResponse> call, Throwable throwable) {
				AppUtils.showToast(getActivity(), throwable.getMessage());
			}
		});
	}

	private void getForecast() {
		apiInterface.getForecastWeather(lat, lon, unit, lang, appid).enqueue(new Callback<ForecastResponse>() {
			@Override
			public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
				if (response.body() != null) {
					binding.srl.setRefreshing(false);
					getChart(response.body());
				}
			}

			@Override
			public void onFailure(Call<ForecastResponse> call, Throwable throwable) {
				binding.srl.setRefreshing(false);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
		return binding.getRoot();
	}

	private NavHostFragment navHostFragment;
	private NavController navController;
	private LineChart lineChart;

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);
		setupShadowView();
		navHostFragment = mainBinding.navHost.getFragment();
		navController = navHostFragment.getNavController();
		binding.tvForecast.setOnClickListener(v -> {
			navController.navigate(R.id.forecastFragment);
		});
		binding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				get();
			}
		});
	}

	private String getTime(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		Date date = new Date(time * 1000L);
		return sdf.format(date);
	}

	private void getChart(ForecastResponse forecastWeather) {
		List<Entry> entries = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			float data = forecastWeather.getList().get(i).getMain().getTemp().floatValue();
			entries.add(new Entry(i, data));
		}
		LineDataSet dataSet = new LineDataSet(entries, "Temp");
		dataSet.setLineWidth(2f);
		dataSet.setColor(getActivity().getColor(R.color.colorSurfaceInverse));
		dataSet.setCircleRadius(8f);
		dataSet.setHighlightEnabled(true);
		dataSet.setHighLightColor(getActivity().getColor(R.color.colorTertiary));
		dataSet.setCircleColor(getActivity().getColor(R.color.colorPrimary));
		dataSet.setValueTextSize(12);
		dataSet.setValueTextColor(getActivity().getColor(R.color.colorPrimary));
		dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
		dataSet.setValueFormatter(new ValueFormatter() {
			@Override
			public String getFormattedValue(float value) {
				return String.format(Locale.getDefault(), "%.0f", value);
			}
		});
		LineData lineData = new LineData(dataSet);
		lineChart = binding.lc;
		lineChart.getDescription().setEnabled(false);
		lineChart.getLegend().setEnabled(false);
		lineChart.getAxisLeft().setDrawGridLines(false);
		lineChart.getAxisRight().setDrawGridLines(false);
		lineChart.getAxisLeft().setDrawAxisLine(false);
		lineChart.getAxisRight().setDrawAxisLine(false);
		lineChart.getAxisLeft().setDrawLabels(false);
		lineChart.getAxisRight().setDrawLabels(false);
		lineChart.getXAxis().setDrawLabels(false);
		lineChart.getXAxis().setDrawGridLines(false);
		lineChart.getXAxis().setDrawAxisLine(false);
		lineChart.setScaleEnabled(true);
		lineChart.setTouchEnabled(true);
		lineChart.setData(lineData);
		lineChart.animateX(1000);
	}

	private void setupShadowView() {
		float corner = dpUtils.dp20();
		float dp0 = dpUtils.dp0();
		float dp5 = dpUtils.dp5();
		float dp8 = dpUtils.dp8();
		float dp10 = dpUtils.dp10();
		float dp16 = dpUtils.dp16();
		// Card 1
		binding.sl1.setCornerRadius(corner, corner, corner, corner);
		binding.sl1.setShadowColor(getActivity().getColor(R.color.colorPrimary));
		binding.sl1.setShadowDx(dp0);
		binding.sl1.setShadowDy(dp5);
		binding.sl1.setShadowMargin((int) dp8, (int) dp5, (int) dp8, (int) dp16);
		binding.sl1.setShadowRadius(dp8);
		// Card 2
		binding.sl2.setCornerRadius(corner, corner, corner, corner);
		binding.sl2.setShadowColor(getActivity().getColor(R.color.colorPrimary));
		binding.sl2.setShadowDx(dp0);
		binding.sl2.setShadowDy(dp5);
		binding.sl2.setShadowMargin((int) dp8, (int) dp5, (int) dp8, (int) dp16);
		binding.sl2.setShadowRadius(dp8);
		// Card 3
		binding.sl3.setCornerRadius(corner, corner, corner, corner);
		binding.sl3.setShadowColor(getActivity().getColor(R.color.colorPrimary));
		binding.sl3.setShadowDx(dp0);
		binding.sl3.setShadowDy(dp5);
		binding.sl3.setShadowMargin((int) dp8, (int) dp5, (int) dp8, (int) dp16);
		binding.sl3.setShadowRadius(dp8);
	}
}