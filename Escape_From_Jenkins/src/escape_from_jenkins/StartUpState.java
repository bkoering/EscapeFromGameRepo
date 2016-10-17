package escape_from_jenkins;

import java.util.Iterator;

import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.tiled.TiledMap;


class StartUpState extends BasicGameState {
	TiledMap map; 
	int Base, WaterCollision, MazeLVL1;


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		//System.out.println("Did this get hit?");

		try{
//			map = new TiledMap("escape/resource/escapeBoard.tmx");
			map = new TiledMap("/Escape_From_Jenkins/src/escape/resource/testBoard.tmx");

			} 
		catch (SlickException e){

			System.out.println("Slick Exception Error: map failed to load");

		}
//		Base = map.getLayerIndex("Base");
		Base = map.getLayerIndex("Tile Layer 1");
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		 		//map.render(0, 0, grassLayer);
 				map.render(0, 0, Base);

	
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {

		
		
	}

	@Override
	public int getID() {
		return EscapeGame.STARTUPSTATE;
	}
	
}