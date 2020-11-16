package uy.com.fire.quasar.operation.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopSecretDto implements Serializable {

	private static final long serialVersionUID = 443270469804799101L;

	@JsonProperty("satellites")
	private List<SatelliteDto> satelites;

	public List<SatelliteDto> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<SatelliteDto> satelites) {
		this.satelites = satelites;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TopSecretDto [satelites=" + satelites + "]";
	}

}
