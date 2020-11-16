package uy.com.fire.quasar.operation.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto implements Serializable {

	private static final long serialVersionUID = -5622849311301225552L;

	@JsonProperty("position")
	private PositionDto position;

	@JsonProperty("message")
	private String message;

	public ResponseDto(PositionDto position, String message) {
		super();
		this.position = position;
		this.message = message;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ResponseDto [position=" + position + ", message=" + message + "]";
	}

}
