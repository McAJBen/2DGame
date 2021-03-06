package gamePackage;

import settingsPackage.GLOBAL;

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
		return x / GLOBAL.U_MULTIPLIER;
	}
	
	public int getY() {
		return y / GLOBAL.U_MULTIPLIER;
	}
	
	public double getXDouble() {
		return (double)x / GLOBAL.U_MULTIPLIER;
	}
	
	public double getYDouble() {
		return (double)y / GLOBAL.U_MULTIPLIER;
	}

	public void addX(double d) {
		x += d * GLOBAL.U_MULTIPLIER;
	}
	
	public void addY(double d) {
		y += d * GLOBAL.U_MULTIPLIER;
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
	
	public void PlayerFriction(double d) {
		x *= d;
		if (y > GLOBAL.PLAYER_MAX_VELOCITY) {
			y = GLOBAL.PLAYER_MAX_VELOCITY;
		}
		else if (y < -GLOBAL.PLAYER_MAX_VELOCITY) {
			y = -GLOBAL.PLAYER_MAX_VELOCITY;
		}
		if (x > GLOBAL.PLAYER_MAX_VELOCITY) {
			x = GLOBAL.PLAYER_MAX_VELOCITY;
		}
		else if (x < -GLOBAL.PLAYER_MAX_VELOCITY) {
			x = -GLOBAL.PLAYER_MAX_VELOCITY;
		}
	}
	
	public void setX(int i) {
		x = (short) (i * GLOBAL.U_MULTIPLIER);
	}

	public void setY(int i) {
		y = (short) (i * GLOBAL.U_MULTIPLIER);
	}
	
	public void setX(double d) {
		x = (short) (d * GLOBAL.U_MULTIPLIER);
	}

	public void setY(double d) {
		y = (short) (d * GLOBAL.U_MULTIPLIER);
	}

	public void setX(Position position) {
		x = position.getXShort();
	}
	
	public void setY(Position position) {
		y = position.getYShort();
	}
	
	public void setXShort(int i) {
		x = (short) i;
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
		return getMax(x, GLOBAL.U_MULTIPLIER);
	}
	
	public int getYMax() {
		return getMax(y, GLOBAL.U_MULTIPLIER);
	}
	
	private int getMax(short i, int j) {
		return (int) ((i + j) / GLOBAL.U_MULTIPLIER);
	}
	
	public int getXMaxShort() {
		return x + GLOBAL.U_MULTIPLIER;
	}
	
	public int getYMaxShort() {
		return y + GLOBAL.U_MULTIPLIER;
	}
	
	public int getXMaxM1() {
		return getMax(x, GLOBAL.U_MULTIPLIER - 1);
	}
	
	public int getYMaxM1() {
		return getMax(y, GLOBAL.U_MULTIPLIER - 1);
	}
	
	public int getXScreen() {
		return (int) (x * GLOBAL.screenUWidth);
	}
	
	public int getYScreen() {
		return (int) (y * GLOBAL.screenUHeight);
	}

	public boolean within(int mapSize) {
		return  x >= 0 && mapSize - GLOBAL.U_MULTIPLIER >= x &&
				y >= 0 && mapSize - GLOBAL.U_MULTIPLIER >= y;
	}
	
	public Position clone() {
		return new Position(this);
	}

	public String toString() {
		return x + ", " + y;
	}
	
	public boolean equals(Position p) {
		return p.x == x && p.y == y;
	}
}