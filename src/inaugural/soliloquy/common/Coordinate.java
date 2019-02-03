package inaugural.soliloquy.common;

import soliloquy.common.specs.ICoordinate;

public class Coordinate implements ICoordinate {
	
	private int x;
	
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(ICoordinate arg0) {
		return coordinateCount(this) - coordinateCount(arg0);
	}

	@Override
	public ICoordinate makeClone() {
		return new Coordinate(getX(), getY());
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	private int coordinateCount(ICoordinate coordinate)
	{
		int digitsSum = (coordinate.getX() + coordinate.getY());
		int coordinatesHitherto = (digitsSum * (digitsSum + 1))/2;
		return 1 + coordinatesHitherto + coordinate.getX();
	}
}
