package gamePackage;

public class Position {
	
	
	private short x, y;
	
	public Position() {
		x = 0; y = 0;
	}
	
	public Position(Position position) {
		x = position.x;
		y = position.y;
	}
	
	public Position(int i, int j) {
		x = (short) i;
		y = (short) j;
	}
	
	public void set(Position newPosition) {
		x = newPosition.x;
		y = newPosition.y;
	}

	public int getX() {
		return x / GLOBAL.SHORT_MULTIPLIER;
	}
	
	public int getY() {
		return y / GLOBAL.SHORT_MULTIPLIER;
	}
	
	public double getXDouble() {
		return (double)x / GLOBAL.SHORT_MULTIPLIER;
	}
	
	public double getYDouble() {
		return (double)y / GLOBAL.SHORT_MULTIPLIER;
	}

	public void addX(double d) {
		x += d * GLOBAL.SHORT_MULTIPLIER;
	}
	
	public void addY(double d) {
		y += d * GLOBAL.SHORT_MULTIPLIER;
	}

	public void addXShort(short i) {
		x += i;
	}
	
	public void addYShort(short i) {
		y += i;
	}
	
	public void addXShort(int i) {
		x += i;
	}
	
	public void addYShort(int i) {
		y += i;
	}
	
	public void subtractXShort(short i) {
		x -= i;
	}
	
	public void subtractYShort(short i) {
		y -= i;
	}
	
	public void Friction(double d) {
		x *= d;
	}
	
	public void setX(int i) {
		x = (short) (i * GLOBAL.SHORT_MULTIPLIER);
	}

	public void setY(int i) {
		y = (short) (i * GLOBAL.SHORT_MULTIPLIER);
	}
	
	public void setX(double d) {
		x = (short) (d * GLOBAL.SHORT_MULTIPLIER);
	}

	public void setY(double d) {
		y = (short) (d * GLOBAL.SHORT_MULTIPLIER);
	}

	public void setX(Position position) {
		x = position.getXShort();
	}
	
	public void setY(Position position) {
		y = position.getYShort();
	}
	
	public void setXShort(short i) {
		x = i;
	}
	
	public void setYShort(int i) {
		y = (short) i;
	}

	public short getXShort() {
		return x;
	}
	
	public short getYShort() {
		return y;
	}
	
	public int getXMaxPlayer() {
		return getMax(x, GLOBAL.PLAYER_SIZE);
	}

	public int getYMaxPlayer() {
		return getMax(y, GLOBAL.PLAYER_SIZE);
	}
	
	public int getXMax() {
		return getMax(x, GLOBAL.SHORT_MULTIPLIER);
	}
	
	public int getYMax() {
		return getMax(y, GLOBAL.SHORT_MULTIPLIER);
	}
	
	private int getMax(short i, int j) {
		return (int) ((i + j) / GLOBAL.SHORT_MULTIPLIER);
	}
	
	public int getXMaxShort() {
		return x + GLOBAL.SHORT_MULTIPLIER;
	}
	
	public int getYMaxShort() {
		return y + GLOBAL.SHORT_MULTIPLIER;
	}
	
	public int getXMaxM1() {
		return getMax(x, GLOBAL.SHORT_MULTIPLIER - 1);
	}
	
	public int getYMaxM1() {
		return getMax(y, GLOBAL.SHORT_MULTIPLIER - 1);
	}
	
	public int getXScreen() {
		return (int) (x * GLOBAL.screenShortWidth);
	}
	
	public int getYScreen() {
		return (int) (y * GLOBAL.screenShortHeight);
	}

	public boolean within(short mapPixelSize) {
		return  x >= 0 && mapPixelSize > x &&
				y >= 0 && mapPixelSize > y;
	}
	
	public Position clone() {
		return new Position(this);
	}

	public String toString() {
		return x + ", " + y;
	}
}
