package nl.ipsen3server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
	@JsonProperty("GREEN")
	GREEN,
	@JsonProperty("RED")
	RED,
	@JsonProperty("ORANGE")
	ORANGE,
	@JsonProperty("PAUSED")
	PAUSED,
	@JsonProperty("SAVED")
	SAVED
}
