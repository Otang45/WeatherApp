package otang.weather.model.weather;

import com.google.gson.annotations.SerializedName;

public class Weather {

	@SerializedName("id")
	private int id;

	@SerializedName("main")
	private String main;

	@SerializedName("description")
	private String description;

	@SerializedName("icon")
	private String icon;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getMain() {
		return main;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

}
