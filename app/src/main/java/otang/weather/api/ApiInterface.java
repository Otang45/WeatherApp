package otang.weather.api;

import otang.weather.model.forecast.ForecastResponse;
import otang.weather.model.weather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
	@GET("/data/2.5/weather")
	Call<WeatherResponse> getTodayWeather(@Query("lat") String lat, @Query("lon") String lon,
			@Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);

	@GET("/data/2.5/forecast")
	Call<ForecastResponse> getForecastWeather(@Query("lat") String lat, @Query("lon") String lon,
			@Query("units") String units, @Query("lang") String lang, @Query("appid") String appid);
}