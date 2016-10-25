package escape_from_jenkins;

import java.util.Iterator;
import java.util.ArrayList;

import jig.ResourceManager;
import jig.Vector;
import jig.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.util.ResourceLoader;

import escape_from_jenkins.PlayingState;
import escape_from_jenkins.StartUpState;



class PlayingState extends BasicGameState {
	int Lives;
	TiledMap map; 
	int Base, Gnome, Plane, Maze, Start,GnomesWater;
	//int i,j;
	
	//TileSet[][] mazeTile = new TileSet[21][22];
	//float mazeCollision[][] = new float[21][22];
	int[][] mazeCollision = new int[21][22];
	//mazeCollision[][] = {{0}{0}};
	
	
	
	
	
	
	//mazeTile.tileHeight = 32;
	
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
		
		
		for(int i = 0; i < 21; i++){ 
			for(int j = 0; j < 22; j++){ 
				if(map.getTileId(i, j, map.getLayerIndex("maze")) > 0){ 
					mazeCollision[i][j] = 1; 
				} 
			}
		}
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
			
			
			for (int i =0; i<4; i++)
			{
				eg.cat[i].render(g);
			}
			
			for (int i =0; i<9; i++)
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
		
		boolean up, down, left, right;
		up = down = left = right = false;
	
		int xPos;
		int yPos;
		
		xPos = (int) Math.floor(eg.player.getX()/32);
		yPos = (int) Math.floor(eg.player.getY()/32);
		
		
		//moving the kid
		if ((input.isKeyDown(Input.KEY_LEFT)) && (up == false)&&(right == false)&&(down == false)){
			left = true;
			if((eg.player.getX() > 16) && (xPos-1 >= 0)) {
				if(mazeCollision[xPos-1][yPos] == 0)
					eg.player.setPosition(eg.player.getX()-10, eg.player.getY());
			}
			left = false;
		}
		//-------------------------
		if ((input.isKeyDown(Input.KEY_RIGHT)) && (up == false) && (left == false)&&(down == false)){
			right = true;
			if((eg.player.getX() < eg.getScreenWidth()-16) && (xPos+1 < 21)){
					if(mazeCollision[xPos+1][yPos] == 0)
						eg.player.setPosition(eg.player.getX()+10, eg.player.getY());
			}
			right = false;
		}
		//------------------------------------
		if ((input.isKeyDown(Input.KEY_UP)) && (left == false)&&(right == false)&&(down == false)) {
			up = true;
			if((eg.player.getY() > 16) && (yPos-1 >= 0))
				if(mazeCollision[xPos][yPos-1] == 0){
					eg.player.setPosition(eg.player.getX(), eg.player.getY()-16);
			}
			up = false;
		}
		//----------------------------------------
		if ((input.isKeyDown(Input.KEY_DOWN)) && (up == false)&&(right == false)&&(left == false)){
			down = true;
			if((eg.player.getY() < eg.getScreenHeight()-16) && (yPos+1 < 22)){
					if(mazeCollision[xPos][yPos+1] == 0)
						eg.player.setPosition(eg.player.getX(), eg.player.getY()+10);
			}
			down = false;
		}
		
		
//		if (bg.ball.getCoarseGrainedMaxY() > bg.ScreenHeight) //bottom of screen
//		{
//			bg.ball.setPosition(bg.getScreenWidth() / 2, bg.getScreenHeight() / 2);	
//			Lives--;
//		}
		
//		if(flag==false && bg.Level3==true)
//			eg.WinState = true;
		
//		if (Lives == 0 || bg.WinState == true) {		
//			((GameOverState)game.getState(BounceGame.GAMEOVERSTATE)).setUserScore(bounces);
//			game.enterState(BounceGame.GAMEOVERSTATE);
//		}
		
		
		//update cat position-----------------
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
				eg.cat[i].setPosition(672, 557);//565
			}
			
			eg.cat[i].update(delta);
		}
		
		///update log position--------------------------
		for (int i =0; i<3; i++)
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
