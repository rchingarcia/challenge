package uy.com.fire.quasar.operation.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import uy.com.fire.quasar.operation.dto.SatelliteDto;
import uy.com.fire.quasar.operation.exception.ValidationException;

@Component
public class TopSecretValidator {

	public void validateSatellitesDto(List<SatelliteDto> satellitesDto) {
		validateMinimunNumberOfSatellites(satellitesDto);
		validateSatellitesAtributes(satellitesDto);
	}

	private void validateSatellitesAtributes(List<SatelliteDto> satellitesDto) {
		satellitesDto.stream().forEach(satelliteDto -> {
			if (!validateSatelliteName(satelliteDto.getName()) || !validateDistance(satelliteDto.getDistance())
					|| !validateMessages(satelliteDto.getMessage())) {
				throw new ValidationException();
			}
		});
	}

	private void validateMinimunNumberOfSatellites(List<SatelliteDto> satellitesDto) {
		if (satellitesDto.size() < 3) {
			throw new ValidationException();
		}

	}

	private boolean validateMessages(String[] messages) {
		return messages != null && messages.length != 0;
	}

	private boolean validateDistance(float distance) {
		return distance > 0;
	}

	private boolean validateSatelliteName(String name) {
		return name != null && !name.isEmpty();
	}
}
