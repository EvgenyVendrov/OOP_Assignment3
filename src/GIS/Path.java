package GIS;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.GpsCoord;
import gameUtils.Fruit;

public class Path {
	private ArrayList<GpsCoord> points;
	private Fruit fruitAtTheEnd;
	private double pathLenght;
	private int speedGetEaten;

	public Path() {
		points = new ArrayList<GpsCoord>();
		pathLenght = 0;
		speedGetEaten = 1;
	}

	public Path(ArrayList<GpsCoord> points) {
		this.points = points;
	}

	public ArrayList<GpsCoord> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<GpsCoord> points) {
		this.points = points;
	}

	public double getPathLenght() {
		pathLenght = this.points.get(0).distance3D(this.points.get(this.points.size() - 1));
		return pathLenght;
	}

	public void addPointToPath(GpsCoord gps) {
		points.add(gps);
	}

	public Fruit getFruitAtTheEnd() {
		return fruitAtTheEnd;
	}

	public void setFruitAtTheEnd(Fruit fruitAtTheEnd) {
		this.fruitAtTheEnd = fruitAtTheEnd;
	}

	public static Path combinePath(Path arg0, Path arg1) {
		if (!arg0.equals(arg1)) {
			Iterator<GpsCoord> it = arg1.points.iterator();
			while (it.hasNext()) {
				GpsCoord current = it.next();
				arg0.addPointToPath(current);
			}
			return arg0;
		}
		return arg0;
	}

	public void setSpeed(int speed) {
		this.speedGetEaten = speed;
	}
	public int getSpeed() {
		return(this.speedGetEaten);
	}
	
}
