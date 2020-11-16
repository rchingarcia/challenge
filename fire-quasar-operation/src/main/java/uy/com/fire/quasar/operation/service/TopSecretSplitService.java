package uy.com.fire.quasar.operation.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import uy.com.fire.quasar.operation.dto.ResponseDto;
import uy.com.fire.quasar.operation.dto.SatelliteDto;

@Service
public class TopSecretSplitService extends TopSecretService {

	private Map<String, SatelliteDto> satellitesDto = new HashMap<>();

	public ResponseEntity<ResponseDto> getTopSecretSplitLocation() {
		return super.getTopSecretLocation(satellitesDto.values().stream().collect(Collectors.toList()));
	}

	public void addSatelliteInformation(SatelliteDto satelliteDto) {
		String satelliteName = satelliteDto.getName();
		if (satellitesDto.containsKey(satelliteName)) {
			satellitesDto.replace(satelliteName, satelliteDto);
		} else {
			satellitesDto.put(satelliteName, satelliteDto);
		}
	}

}
