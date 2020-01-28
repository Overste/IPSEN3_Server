package nl.ipsen3server.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ResponseModel {

    private String response;

    @JsonCreator
    public ResponseModel(
            @JsonProperty("response") String response
    ) {
        this.response = response;
    }

    @JsonProperty
    @NotNull
    public String getResponse() {
        return this.response;
    }

}
