package escape_from_jenkins;
import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.util.ResourceLoader;

import escape_from_jenkins.PlayingState;
import escape_from_jenkins.StartUpState;

import org.newdawn.slick.tiled.TiledMap;
import jig.Entity;
import jig.ResourceManager;

public class EscapeGame extends StateBasedGame {

		private TiledMap map; 
		int Base, WaterCollision, MazeLVL1;
		int i,j;
		
		boolean WinState = false;

		public static final int STARTUPSTATE = 0;
		public static final int PLAYINGSTATE = 1;
		public static final int GAMEOVERSTATE = 2;
		
		public static final String CAT_IMG = "escape/resource/cats.png";
		public static final String LOG_IMG = "escape/resource/log.png";
		public static final String PLAYER_IMG= "escape/resource/kid.png";


		public final int ScreenWidth;
		public final int ScreenHeight;
		//Tile[][] tileSet;
		Cat cat[];
		Log log[];
		Player player;
		

		public EscapeGame(String title, int width, int height) {
			super(title);
			ScreenHeight = height;
			ScreenWidth = width;

			Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
					
		}
		
		public int getScreenHeight() {
			return ScreenHeight;
		}
		public int getScreenWidth() {
			return ScreenWidth;
		}

		@Override
		public void initStatesList(GameContainer container) throws SlickException {
			
			addState(new StartUpState());
			//addState(new GameOverState());
			addState(new PlayingState());
			
			// pre-load resources 
			ResourceManager.loadImage(CAT_IMG);
			ResourceManager.loadImage(LOG_IMG);
			ResourceManager.loadImage(PLAYER_IMG);

			j=0;

			cat = new Cat[4];
			log = new Log[9];
			
			player = new Player(656, 688, 0f, 0f);
			
			//create cats
			for (i=0; i<4; i++)
			{
				j=300*i;
				
				if(i<2)
					cat[i] = new Cat((16+j), 624, .05f, 0f);	//offset by 16 bits for everything
				
				else{
					//j=0;
					j=300*(i-1);	
					cat[i] = new Cat((16+j), 557, -.05f, 0f);	//offset by 16 bits for everything
				}
					
			}
			
			//create logs
			for (i =0; i<9; i++){
				j=250*i;
				
				if(i<3) 
				{
					log[i] = new Log((32+j), 80, .05f, 0f);
				}
				
				else if(i<6) 
				{
					j=250*(i-2);
					log[i] = new Log((32+j), 112, -.05f, 0f);
				}
				
				else if(i<9)
				{
					j=250*(i-6);
					log[i] = new Log((32+j), 144, .05f, 0f);
				}
			}
			
			


			//tileSet = new Tile[21][26];
			
//			try{
//
//				map = new TiledMap(GAME_BOARD);
//
//				} 
//			catch (SlickException e){
//
//				System.out.println("Slick Exception Error: map failed to load");
//
//			}
//			Base = map.getLayerIndex("Base");
//--------------------------------------------------------------
//			for(int i = 0; i < 26; i++){ 
//				for(int j = 0; j < 21; j++){ 
//				
//					if(map.getTileId(i, j, map.getLayerIndex("Collision")) > 0){ 
//					
//						tileSet[i][j] = new Tile(); 
//						tileSet[i][j].setCollision();
//						}
//				
//					else {
//						tileSet[i][j] = new Tile();
//
//					}
//				}//for j end
//			}//for i end
			
		}
		
	
	
		public static void main(String[] args) {
			AppGameContainer app;
			try {
				app = new AppGameContainer(new EscapeGame("Escape From Old Man Jenkins", 672, 704));
				app.setDisplayMode(672, 704, false);
				app.setVSync(true);
				app.start();
				
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}	
	}
