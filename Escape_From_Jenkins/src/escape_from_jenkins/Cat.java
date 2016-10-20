package escape_from_jenkins;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


 class Cat extends Entity {

	private Vector velocity;
	private int countdown;

	public Cat(final float x, final float y, final float vx, final float vy) {
		super(x, y);
		addImageWithBoundingBox(ResourceManager
				.getImage(EscapeGame.CAT_IMG));
		velocity = new Vector(vx, vy);
		countdown = 0;
	}

	public void setVelocity(final Vector v) {
		velocity = v;
	}

	public Vector getVelocity() {
		return velocity;
	}


	public void bounce(float surfaceTangent) {
		countdown = 500;
		velocity = velocity.bounce(surfaceTangent);
	}


	public void update(final int delta) {
		translate(velocity.scale(delta));
		if (countdown > 0) {
			countdown -= delta;
			if (countdown <= 0) {
				addImageWithBoundingBox(ResourceManager
						.getImage(EscapeGame.CAT_IMG));
				removeImage(ResourceManager
						.getImage(EscapeGame.CAT_IMG));
			}
		}
	}
}
