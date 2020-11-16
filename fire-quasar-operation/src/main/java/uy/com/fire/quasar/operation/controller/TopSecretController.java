package uy.com.fire.quasar.operation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.com.fire.quasar.operation.dto.ResponseDto;
import uy.com.fire.quasar.operation.dto.SatelliteDto;
import uy.com.fire.quasar.operation.dto.TopSecretDto;
import uy.com.fire.quasar.operation.service.TopSecretService;
import uy.com.fire.quasar.operation.service.TopSecretSplitService;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class TopSecretController {

	@Autowired
	TopSecretService topSecretService;

	@Autowired
	TopSecretSplitService topSecretSplitService;

	@PostMapping("/topsecret")
	public ResponseEntity<ResponseDto> getTopSecretLocation(@RequestBody TopSecretDto topSecretDto) {
		// topSecretService.calculateThreeCircleIntersection(-500, -200, 300.0, 100,
		// -100, 310.0, 500, 100, 744.55378218);
		return topSecretService.getTopSecretLocation(topSecretDto.getSatelites());
	}

	@PostMapping("/topsecret_split/{satelliteName}")
	public ResponseEntity<ResponseDto> addTopSecretSattelliteInformation(
			@PathVariable(required = true) String satelliteName, @RequestBody SatelliteDto satelliteDto) {
		satelliteDto.setName(satelliteName.toLowerCase());
		topSecretSplitService.addSatelliteInformation(satelliteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/topsecret_split/{satelliteName}")
	public ResponseEntity<ResponseDto> getTopSecretSplitLocation() {
		return topSecretSplitService.getTopSecretSplitLocation();
	}

}
