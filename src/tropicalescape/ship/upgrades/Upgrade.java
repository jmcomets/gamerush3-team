package tropicalescape.ship.upgrades;

public class Upgrade {

	private String name;
	private int cost;

	public Upgrade(int cost, String name) {
		this.cost = cost;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public String getDescription() {
		return name;
	}

}
