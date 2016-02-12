package mapPackage;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import gamePackage.Position;
import settingsPackage.GLOBAL;

public class ElectricShot {
	
	private static int electricShotStep;
	private enum eShotType {
		HORIZONTAL, VERTICAL, BOTH
	}
	
	private eShotType direction;
	private Position position;
	
	public ElectricShot(int x, int y, eShotType i) {
		direction = i;
		position = new Position();
		position.setX(x);
		position.setY(y);
	}

	public static void move() {
		electricShotStep++;
		if (electricShotStep > GLOBAL.ELECTRIC_SHOT_TOTAL) {
			electricShotStep = 0;
		}
	}
	
	public static void paint(Graphics g, ArrayList<ElectricShot> esList) {
		if (electricityOn()) {
			for (ElectricShot es: esList) {
				es.paintElectricityShot(g);
			}
		}
	}
	
	private void paintElectricityShot(Graphics g) {
		g.setColor(MapSquare.ELECTRICITY_COLOR);
		
		switch (direction) {
		case BOTH:
			paintHorizontal(g);
			paintVertical(g);
			break;
		case HORIZONTAL:
			paintHorizontal(g);
			break;
		case VERTICAL:
			paintVertical(g);
			break;		
		}
	}
	
	private void paintVertical(Graphics g) {
		MapSquare.paintElectricity(g, 
				position.getXScreen() + (int)(GLOBAL.screenUWidth * GLOBAL.ELECTRIC_SHOT_OFFSET),
				position.getYScreen(),
				(int)(GLOBAL.screenUWidth * GLOBAL.ELECTRIC_SHOT_WIDTH),
				(int)GLOBAL.screenPixelHeight, 7);
	}

	private void paintHorizontal(Graphics g) {
		MapSquare.paintElectricity(g, 
				position.getXScreen(),
				position.getYScreen() + (int)(GLOBAL.screenUHeight * GLOBAL.ELECTRIC_SHOT_OFFSET),
				(int)GLOBAL.screenPixelWidth,
				(int)(GLOBAL.screenUHeight * GLOBAL.ELECTRIC_SHOT_WIDTH), 7);
	}
	
	public static boolean checkDeath(Position pos, ArrayList<ElectricShot> electricShots) {
		if (electricityOn()) {
			for (ElectricShot es: electricShots) {
				if(	es.within(pos.getXShort(), pos.getYShort()) ||
					es.within(pos.getXShort() + GLOBAL.PLAYER_SIZE, pos.getYShort()) ||
					es.within(pos.getXShort(), pos.getYShort() + GLOBAL.PLAYER_SIZE) ||
					es.within(pos.getXShort() + GLOBAL.PLAYER_SIZE, pos.getYShort() + GLOBAL.PLAYER_SIZE)) {
						return true;
				}
			}
		}
		return false;
	}
	
	private boolean within(int px, int py) {
		switch(direction) {
		case BOTH:
			return 	position.getXShort() < px &&
					px < position.getXMaxShort() && 
					position.getYShort() < py &&
					py < position.getYMaxShort();
		case HORIZONTAL:
			return 	position.getXShort() < px &&
					px < position.getXMaxShort() && 
					position.getYShort() + GLOBAL.ELECTRIC_SHOT_OFFSET < py &&
					py < position.getYShort() + GLOBAL.ELECTRIC_SHOT_WIDTH;
		case VERTICAL:
			return 	position.getXShort() + GLOBAL.ELECTRIC_SHOT_OFFSET < px &&
					px < position.getXShort() + GLOBAL.ELECTRIC_SHOT_WIDTH && 
					position.getYShort() < py &&
					py < position.getYMaxShort();
		}
		return false;
	}

	private static boolean electricityOn() {
		return electricShotStep < GLOBAL.ELECTRIC_SHOT_TIME;
	}

	public static ArrayList<ElectricShot> setup(ArrayList<Point> electricShotBasePoints, MapSquare[][] mapSquares) {
		ArrayList<ElectricShot> electricShots = new ArrayList<>();
		for (Point p: electricShotBasePoints) {
			for (int x = p.x - 1; x >= 0; x--) {
				if (mapSquares[x][p.y].getWallEnemy()) {
					break;
				}
				else {
					electricShots.add(new ElectricShot(x, p.y, eShotType.HORIZONTAL));
				}
			}
			for (int x = p.x + 1; x < GLOBAL.MAP_PIXEL_SIZE; x++) {
				if (mapSquares[x][p.y].getWallEnemy()) {
					break;
				}
				else {
					electricShots.add(new ElectricShot(x, p.y, eShotType.HORIZONTAL));
				}
			}
			for (int y = p.y - 1; y >= 0; y--) {
				if (mapSquares[p.x][y].getWallEnemy()) {
					break;
				}
				else {
					electricShots.add(new ElectricShot(p.x, y, eShotType.VERTICAL));
				}
			}
			for (int y = p.y + 1; y < GLOBAL.MAP_PIXEL_SIZE; y++) {
				if (mapSquares[p.x][y].getWallEnemy()) {
					break;
				}
				else {
					electricShots.add(new ElectricShot(p.x, y, eShotType.VERTICAL));
				}
			}
		}
		compare(electricShots);
		join(electricShots);
		return electricShots;
	}

	private static void compare(ArrayList<ElectricShot> electricShots) {
		for (int i = 0; i < electricShots.size() - 1; i++) {
			for (int j = i + 1; j < electricShots.size(); j++) {
				if (electricShots.get(i).equals(electricShots.get(j))) {
					electricShots.remove(j);
					j--;
				}
			}
		}
	}
	
	private static void join(ArrayList<ElectricShot> electricShots) {
		for (int i = 0; i < electricShots.size() - 1; i++) {
			switch(electricShots.get(i).direction) {
			case BOTH:
				break;
			case HORIZONTAL:
			case VERTICAL:
				for (int j = i + 1; j < electricShots.size(); j++) {
					if (electricShots.get(j).position.equals(electricShots.get(i).position)) {
						electricShots.get(i).setBoth();
						electricShots.remove(j);
						j--;
					}
				}
				break;
			}
		}
	}
	
	public boolean equals(ElectricShot es) {
		return es.direction.equals(direction) && es.position.equals(position);
	}
	
	private void setBoth() {
		direction = eShotType.BOTH;
	}
	
	
	
	
	
}
