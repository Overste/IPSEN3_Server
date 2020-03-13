package nl.ipsen3server.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Anthony Scheeres, Jesse
 */
public enum Fasen {
	@JsonProperty("IDEE")
	IDEE,
	@JsonProperty("LABIN")
	LABIN,
	@JsonProperty("LABUIT")
	LABUIT,
	@JsonProperty("VRIESKIST")
	VRIESKIST,
	@JsonProperty("HALLOFFAME")
	HALLOFFAME,
	@JsonProperty("KERKHOF")
	KERKHOF,
	@JsonProperty("VASTEDIENSTEN")
	VASTEDIENSTEN


}