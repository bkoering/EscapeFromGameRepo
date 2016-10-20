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
import escape_from_jenkins.EscapeGame;

import org.newdawn.slick.tiled.TiledMap;


class StartUpState extends BasicGameState {
	TiledMap map; 
	int Base, Gnome, Plane, Maze, WaterCollision, MazeLVL1;


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		//load lvl1 map
		try{
			map = new TiledMap("src/escape/resource/GBlvl1.tmx");

			} 
		catch (SlickException e){

			System.out.println("Slick Exception Error: map failed to load");

		}
		//grab map layers
		Base = map.getLayerIndex("Base");
		Gnome = map.getLayerIndex("gnome");
		Maze = map.getLayerIndex("maze");
		Plane = map.getLayerIndex("plane");

	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.setSoundOn(false);
	}


	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {
		
			EscapeGame eg = (EscapeGame)game;
		
 			map.render(0, 0, Base);
 			map.render(0, 0, Gnome);
 			map.render(0, 0, Plane);
 			map.render(0, 0, Maze);
 			
 			for (int i =0; i<4; i++)
 			{
 				eg.cat[i].render(g);
 			}

	
	}

	
	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		Input input = container.getInput();
		EscapeGame eg = (EscapeGame)game;
			

		for (int i =0; i<2; i++)
		{
			if (eg.cat[i].getX()>672)
			{
				eg.cat[i].setPosition(-16, 624);
			}
			
			eg.cat[i].update(delta);	
		}
		for (int i =2; i<4; i++)
		{
			if (eg.cat[i].getX()<-16)
			{
				eg.cat[i].setPosition(672, 556);//565
			}
			
			eg.cat[i].update(delta);
		}
	}//end of update function

	@Override
	public int getID() {
		return EscapeGame.STARTUPSTATE;
	}
	
}