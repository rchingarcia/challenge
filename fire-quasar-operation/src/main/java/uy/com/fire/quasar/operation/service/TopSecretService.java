package uy.com.fire.quasar.operation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import uy.com.fire.quasar.operation.converter.PositionConverter;
import uy.com.fire.quasar.operation.dao.SatelliteDao;
import uy.com.fire.quasar.operation.dto.ResponseDto;
import uy.com.fire.quasar.operation.dto.SatelliteDto;
import uy.com.fire.quasar.operation.exception.NotFoundPositionException;
import uy.com.fire.quasar.operation.exception.SatelliteNameNotFound;
import uy.com.fire.quasar.operation.exception.ValidationException;
import uy.com.fire.quasar.operation.model.Position;
import uy.com.fire.quasar.operation.util.GeoLocationUtil;
import uy.com.fire.quasar.operation.validator.TopSecretValidator;

@Service
public class TopSecretService {

	@Autowired
	private TopSecretValidator topSecretValidator;

	@Autowired
	private SatelliteDao satelliteDao;

	@Autowired
	private PositionConverter positionConverter;

	private List<SatelliteDto> satellitesDto = new ArrayList<>();

	public TopSecretService() {
		super();
	}

	public ResponseEntity<ResponseDto> getTopSecretLocation(List<SatelliteDto> satellitesDto) {
		this.satellitesDto = deleteRepeatedStatellitesNamesAndOrderByNames(satellitesDto);
		try {
			topSecretValidator.validateSatellitesDto(satellitesDto);
			List<Float> distances = this.satellitesDto.stream().map(SatelliteDto::getDistance)
					.collect(Collectors.toList());

			List<String[]> messages = this.satellitesDto.stream().map(SatelliteDto::getMessage)
					.collect(Collectors.toList());

			return new ResponseEntity<>(
					new ResponseDto(positionConverter.mapEntityToDto(getLocation(distances)), getMessage(messages)),
					HttpStatus.OK);
		} catch (ValidationException | NotFoundPositionException | SatelliteNameNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	private List<SatelliteDto> deleteRepeatedStatellitesNamesAndOrderByNames(List<SatelliteDto> satellitesDto) {
		return satellitesDto.stream().filter(distinctByKey(SatelliteDto::getName))
				.sorted(Comparator.comparing(SatelliteDto::getName)).collect(Collectors.toList());
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public Position getLocation(List<Float> distances) {
		List<Position> positions = this.satellitesDto.stream()
				.map(satelliteDto -> satelliteDao.getSatellitePosition(satelliteDto.getName()))
				.collect(Collectors.toList());
		return calculateIssuerPosition(positions.get(0), positions.get(1), positions.get(2), distances);
	}

	private Position calculateIssuerPosition(Position kenobiPosition, Position satoPosition, Position skywalkerPosition,
			List<Float> distances) {

		List<Position> intersectionPoints = GeoLocationUtil.getTwoCircleIntersection(kenobiPosition.getX(),
				kenobiPosition.getY(), distances.get(0), satoPosition.getX(), satoPosition.getY(), distances.get(1));
		return GeoLocationUtil.get3rdCircleIntersection(intersectionPoints.get(0).getX(),
				intersectionPoints.get(0).getY(), intersectionPoints.get(1).getX(), intersectionPoints.get(1).getY(),
				skywalkerPosition.getX(), skywalkerPosition.getY(), distances.get(2));
	}

	public String getMessage(List<String[]> messages) {
		messages = calculateArrayPhaseShift(messages);
		return decodeTopSecretMessage(messages);
	}

	private String decodeTopSecretMessage(List<String[]> messages) {
		StringBuilder decodedMessage = new StringBuilder();
		String[] message1 = messages.get(0);
		String[] message2 = messages.get(1);
		String[] message3 = messages.get(2);
		int messageLength = message1.length;
		for (int i = 0; i < messageLength; i++) {
			if (!message1[i].isEmpty()) {
				decodedMessage.append(message1[i]);
				decodedMessage.append(" ");
			} else if (!message2[i].isEmpty()) {
				decodedMessage.append(message2[i]);
				decodedMessage.append(" ");
			} else if (!message3[i].isEmpty()) {
				decodedMessage.append(message3[i]);
				decodedMessage.append(" ");
			}
		}

		return decodedMessage.toString();
	}

	private List<String[]> calculateArrayPhaseShift(List<String[]> messages) {
		Integer arrayMessageMaxLength = messages.stream().map(message -> message.length)
				.max(Comparator.comparing(Integer::valueOf)).orElse(0);
		messages = messages.stream()
				.map(message -> addEmptyStringToMessageArryIfItHasDiferenceLength(arrayMessageMaxLength, message))
				.collect(Collectors.toList());
		return messages;
	}

	private String[] addEmptyStringToMessageArryIfItHasDiferenceLength(Integer arrayMessageMaxLength,
			String[] message) {
		int diferenceLength = arrayMessageMaxLength - message.length;
		if (diferenceLength > 0) {
			message = Stream.concat(Arrays.stream(message), Arrays.stream(getEmptiStringArray(diferenceLength)))
					.toArray(String[]::new);
		}
		return message;
	}

	private String[] getEmptiStringArray(int diferenceLength) {
		List<String> emptyStringList = new ArrayList<>();
		for (int i = 0; i < diferenceLength; i++) {
			emptyStringList.add("");
		}
		return emptyStringList.stream().toArray(String[]::new);
	}

}
