package escape_from_jenkins;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

 class Scruffy extends Entity {

	private Vector velocity;
	private int countdown;

	public Scruffy(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(EscapeGame.SCRUFFY_IMG));
		velocity = new Vector(vx, vy);
		countdown = 0;
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
		}
	}
}
