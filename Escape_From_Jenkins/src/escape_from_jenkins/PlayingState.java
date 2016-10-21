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
import org.newdawn.slick.tiled.TiledMap;


class PlayingState extends BasicGameState {
	int Lives;
	TiledMap map; 
	int Base, Gnome, Plane, Maze, Start,GnomesWater;
	int i,j;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
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
		GnomesWater = map.getLayerIndex("GOCollision");
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		EscapeGame eg = (EscapeGame)game;
		Lives = 3;
		container.setSoundOn(true);
		
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
			
		g.drawString("Lives: " + Lives, 10, 30);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();
		EscapeGame eg = (EscapeGame)game;
		
		
		
		//moving the kid
		if (input.isKeyDown(Input.KEY_LEFT)){
			if(eg.player.getX() > 16)
				eg.player.setPosition(eg.player.getX()-10, eg.player.getY());
		}
		if (input.isKeyDown(Input.KEY_RIGHT)){
			if(eg.player.getX() < eg.getScreenWidth()-16)
				eg.player.setPosition(eg.player.getX()+10, eg.player.getY());
		}
		if (input.isKeyDown(Input.KEY_UP)){
			if(eg.player.getY() > 16)
				eg.player.setPosition(eg.player.getX(), eg.player.getY()-10);
		}
		if (input.isKeyDown(Input.KEY_DOWN)){
			if(eg.player.getY() < eg.getScreenHeight()-16)
				eg.player.setPosition(eg.player.getX(), eg.player.getY()+10);
		}
		

		
//		if(flag==false && bg.Level3==true)
//			eg.WinState = true;
		
//		if (Lives == 0 || bg.WinState == true) {		
//			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserScore(bounces);
//			game.enterState(BounceGame.GAMEOVERSTATE);
//		}
		
		
		//update cat position-----------------
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
		
		///update log position--------------------------
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
	}

	@Override
	public int getID() {
		return EscapeGame.PLAYINGSTATE;
	}
	
}
