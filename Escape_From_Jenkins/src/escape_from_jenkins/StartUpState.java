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
	int Base, Gnome, Plane, Maze, Start;
	int i,j;


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
		Start = map.getLayerIndex("startZones");

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
 			map.render(0, 0, Start);
 			
 			
 			for (i =0; i<4; i++)
 			{
 				eg.cat[i].render(g);
 			}
 			
 			for ( i =0; i<9; i++)
 			{
 				eg.log[i].render(g);
 			}
 			
 			eg.player.render(g);
 			
 			g.drawImage(ResourceManager.getImage(EscapeGame.STARTUP_BANNER_RSC),
					100, 200);
	
	}

	
	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		Input input = container.getInput();
		EscapeGame eg = (EscapeGame)game;
		eg.WinState=false;
		eg.hasPlane=false;
		eg.releaseScruffy = false;


		if (input.isKeyDown(Input.KEY_SPACE)){
			eg.enterState(EscapeGame.PLAYINGSTATE);	
		}
		

		
		//update cat position
		for (i =0; i<2; i++)
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
				eg.cat[i].setPosition(672, 557);//565
			}
			
			eg.cat[i].update(delta);
		}
		
		///update log position
		for (i =0; i<3; i++)
		{
			if (eg.log[i].getX()>707)
			{
				eg.log[i].setPosition(-48, 80);
			}
			
			eg.log[i].update(delta);	
		}
		
		for (int i =3; i<6; i++)
		{
			if (eg.log[i].getX()<-48)
			{
				eg.log[i].setPosition(707, 112);//565
			}
			
			eg.log[i].update(delta);
		}
		
		for (int i =6; i<9; i++)
		{
			if (eg.log[i].getX()>707)
			{
				eg.log[i].setPosition(-48, 144);
			}
			
			eg.log[i].update(delta);
		}
		
	
	
		
	}//end of update function

	@Override
	public int getID() {
		return EscapeGame.STARTUPSTATE;
	}
	
}