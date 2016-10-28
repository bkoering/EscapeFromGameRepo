package escape_from_jenkins;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


 class Player extends Entity {

	private int countdown;

	public Player(final float x, final float y) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(EscapeGame.PLAYER_IMG));
		countdown = 0;
	}
}
