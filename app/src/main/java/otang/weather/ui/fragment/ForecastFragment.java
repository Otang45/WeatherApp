package otang.weather.ui.fragment;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import otang.weather.R;
import otang.weather.adapter.ForecastAdapter;
import otang.weather.api.ApiClient;
import otang.weather.api.ApiInterface;
import otang.weather.databinding.ForecastFragmentBinding;
import otang.weather.model.forecast.ForecastResponse;
import otang.weather.util.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import otang.weather.util.AppUtils;
import retrofit2.Response;

public class ForecastFragment extends Fragment {

	private ForecastFragmentBinding binding;

	public ForecastFragment() {
	}

	private ApiInterface apiInterface;
	private PrefUtils prefUtils;

	String lat, lon, unit, lang, appid;

	double latitude = 0;
	double longitude = 0;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		prefUtils = new PrefUtils(getActivity());
		apiInterface = ApiClient.getApiInterface();
		latitude = (double) prefUtils.getFloat("lat");
		longitude = (double) prefUtils.getFloat("lon");
		lat = String.valueOf(latitude);
		lon = String.valueOf(longitude);
		unit = "metric";
		lang = "id";
		appid = getString(R.string.weather_api_key);
		get();
	}

	private void get() {
		if (latitude != 0 || longitude != 0) {
			getForecast();
		} else {
			AppUtils.showToast(getActivity(), "No Location");
		}
	}

	private void getForecast() {
		apiInterface.getForecastWeather(lat, lon, unit, lang, appid).enqueue(new Callback<ForecastResponse>() {
			@Override
			public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
				if (response.body() != null) {
					binding.rv.setHasFixedSize(true);
					binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
					ForecastAdapter adapter = new ForecastAdapter(getActivity(), response.body(), binding.rv);
					binding.rv.setAdapter(adapter);
				}
			}

			@Override
			public void onFailure(Call<ForecastResponse> call, Throwable throwable) {
				AppUtils.showToast(getActivity(), throwable.getMessage());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
		binding = ForecastFragmentBinding.inflate(inflater);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		super.onViewCreated(view, bundle);
	}
}