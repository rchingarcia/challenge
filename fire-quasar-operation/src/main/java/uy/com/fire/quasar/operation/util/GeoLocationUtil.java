package uy.com.fire.quasar.operation.util;

import java.util.Arrays;
import java.util.List;

import uy.com.fire.quasar.operation.exception.NotFoundPositionException;
import uy.com.fire.quasar.operation.model.Position;

public class GeoLocationUtil {

	private static final double EPSILON = 1e-4;

	private GeoLocationUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static List<Position> getTwoCircleIntersection(float x0, float y0, float radio0, float x1, float y1,
			float radio1) {

		/*
		 * distanceX and distanceY are the vertical and horizontal distances between the
		 * circle centers.
		 */
		float distanceX = x1 - x0;
		float distanceY = y1 - y0;

		/* Determine the straight-line distance between the centers. */
		float distance = (float) Math.sqrt((distanceY * distanceY) + (distanceX * distanceX));

		/* Check for solvability. */
		if (distance > (radio0 + radio1)) {
			/* no solution. circles do not intersect. */
			throw new NotFoundPositionException();
		}
		if (distance < Math.abs(radio0 - radio1)) {
			/* no solution. one circle is contained in the other */
			throw new NotFoundPositionException();
		}

		/*
		 * 'point 2' is the point where the line through the circle intersection points
		 * crosses the line between the circle centers.
		 */

		/* Determine the distance from point 0 to point 2. */
		float distancePoint1ToPoint2 = (float) (((radio0 * radio0) - (radio1 * radio1) + (distance * distance))
				/ (2.0 * distance));

		/* Determine the coordinates of point 2. */
		float point2X = x0 + (distanceX * distancePoint1ToPoint2 / distance);
		float point2Y = y0 + (distanceY * distancePoint1ToPoint2 / distance);

		/*
		 * Determine the distance from point 2 to either of the intersection points.
		 */
		float h = (float) Math.sqrt((radio0 * radio0) - (distancePoint1ToPoint2 * distancePoint1ToPoint2));

		/*
		 * Now determine the offsets of the intersection points from point 2.
		 */
		float offsetX = -distanceY * (h / distance);
		float offsetY = distanceX * (h / distance);

		/* Determine the absolute intersection points. */
		float intersectionPointX0 = point2X + offsetX;
		float intersectionPointX1 = point2X - offsetX;
		float intersectionPointY0 = point2Y + offsetY;
		float intersectionPointY1 = point2Y - offsetY;

		return Arrays.asList(new Position(intersectionPointX0, intersectionPointY0),
				new Position(intersectionPointX1, intersectionPointY1));

	}

	public static Position get3rdCircleIntersection(float x0, float y0, float x1, float y1, float x2, float y2,
			float radio) {

		/*
		 * Lets determine if circle 3 intersects at either of the above intersection
		 * points.
		 */
		float distanceX = x0 - x2;
		float distanceY = y0 - y2;
		float distancePoint1 = (float) Math.sqrt((distanceY * distanceY) + (distanceX * distanceX));

		distanceX = x1 - x2;
		distanceY = y1 - y2;
		float distancePoint2 = (float) Math.sqrt((distanceY * distanceY) + (distanceX * distanceX));

		if (Math.abs(distancePoint1 - radio) < EPSILON) {
			return new Position(x0, y0);

		} else if (Math.abs(distancePoint2 - radio) < EPSILON) {
			return new Position(x1, y1);
		} else {
			throw new NotFoundPositionException();
		}
	}

}
