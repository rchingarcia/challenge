package uy.com.fire.quasar.operation.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import uy.com.fire.quasar.operation.constant.Constants;
import uy.com.fire.quasar.operation.exception.SatelliteNameNotFound;
import uy.com.fire.quasar.operation.model.Position;
import uy.com.fire.quasar.operation.model.Satellite;

@Component
public class SatelliteDao {

	private Map<String, Satellite> satellites = new HashMap<>();

	public SatelliteDao() {
		super();
		this.satellites.put(Constants.KENOBI, new Satellite(Constants.KENOBI, new Position(-500, -200)));
		this.satellites.put(Constants.SKYWALKER, new Satellite(Constants.SKYWALKER, new Position(100, -100)));
		this.satellites.put(Constants.SATO, new Satellite(Constants.SATO, new Position(500, 100)));
	}

	public Position getSatellitePosition(String name) {
		Satellite satellite = satellites.get(name);
		if (satellite == null) {
			throw new SatelliteNameNotFound();
		}
		return satellite.getPosition();

	}

}
