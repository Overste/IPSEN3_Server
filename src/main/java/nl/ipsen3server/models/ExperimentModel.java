
package nl.ipsen3server.models;

import com.fasterxml.jackson.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "experimentleaders",
    "fasens",
    "statussen",
    "businessOwners",
    "inovation_cost",
    "money_source",
    "organisations"
})
public class ExperimentModel {
    @Pattern(regexp = "^[0-9]*$")
    @JsonProperty("id")
    @NotNull
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("experimentleaders")
    private String experimentleaders;
    @JsonProperty("fasens")
    private String fases;
    @JsonProperty("statussen")
    private String statussen;
    @JsonProperty("businessOwners")
    private String businessOwners;
    @JsonProperty("inovation_cost")
    private String inovationCost;
    @JsonProperty("money_source")
    private String moneySource;
    @JsonProperty("organisations")
    private String organisations;
    private Enum status;
    private Enum fasen;
    private BufferedImage img;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Enum getFases() {
        return fasen;
    }

    public void setFases(Enum fasen) {
        this.fasen = fasen;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("experimentleaders")
    public String getExperimentleaders() {
        return experimentleaders;
    }

    @JsonProperty("experimentleaders")
    public void setExperimentleaders(String experimentleaders) {
        this.experimentleaders = experimentleaders;
    }

    @JsonProperty("fasens")
    public String getFasens() {
        return fases;
    }

    @JsonProperty("fasens")
    public void setFases(String fases) {
        this.fases = fases;
    }

    @JsonProperty("statussen")
    public String getStatussen() {
        return statussen;
    }

    @JsonProperty("statussen")
    public void setStatussen(String statussen) {
        this.statussen = statussen;
    }

    @JsonProperty("businessOwners")
    public String getBusinessOwners() {
        return businessOwners;
    }

    @JsonProperty("businessOwners")
    public void setBusinessOwners(String businessOwners) {
        this.businessOwners = businessOwners;
    }

    @JsonProperty("inovation_cost")
    public String getInovationCost() {
        return inovationCost;
    }

    @JsonProperty("inovation_cost")
    public void setInovationCost(String inovationCost) {
        this.inovationCost = inovationCost;
    }

    @JsonProperty("money_source")
    public String getMoneySource() {
        return moneySource;
    }

    @JsonProperty("money_source")
    public void setMoneySource(String moneySource) {
        this.moneySource = moneySource;
    }

    @JsonProperty("organisations")
    public String getOrganisations() {
        return organisations;
    }

    @JsonProperty("organisations")
    public void setOrganisations(String organisation) {
        this.organisations = organisation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}