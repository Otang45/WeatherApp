package otang.weather.api;

public class ApiClient {
	public static final String WEATHER_BASE_URL = "https://api.openweathermap.org";

	public static ApiInterface getApiInterface() {
		return RetrofitInstance.getClient(WEATHER_BASE_URL).create(ApiInterface.class);
	}

}