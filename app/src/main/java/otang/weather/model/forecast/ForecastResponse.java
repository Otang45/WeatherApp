package otang.weather.model.forecast;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ForecastResponse {

	@SerializedName("cod")
	private String cod;

	@SerializedName("message")
	private Long message;

	@SerializedName("cnt")
	private Long cnt;

	@SerializedName("list")
	private List<Lists> list;

	@SerializedName("city")
	private City city;

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getCod() {
		return cod;
	}

	public void setMessage(Long message) {
		this.message = message;
	}

	public Long getMessage() {
		return message;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setList(List<Lists> list) {
		this.list = list;
	}

	public List<Lists> getList() {
		return list;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public City getCity() {
		return city;
	}

}
