package escape_from_jenkins;
import java.util.ArrayList;
//import slick.jar;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.util.ResourceLoader;

import escape_from_jenkins.StartUpState;

import org.newdawn.slick.tiled.TiledMap;


//import bounce.BounceGame;
//import bounce.Ball;
//import bounce.Bang;
//import bounce.Bar;
//import bounce.Block;
//import bounce.GameOverState;
//import bounce.PlayingState;
//import bounce.StartUpState;
import jig.Entity;
import jig.ResourceManager;

public class EscapeGame extends StateBasedGame {

		private TiledMap map; 
		int Base, WaterCollision, MazeLVL1;
		
		public static final int STARTUPSTATE = 0;
		public static final int PLAYINGSTATE = 1;
		public static final int GAMEOVERSTATE = 2;
		
//		public static final String GAME_BOARD = "escape/resource/escapeBoard.tmx";
//		public static final String GRASS_TILE = "escape/resource/grass";

		public final int ScreenWidth;
		public final int ScreenHeight;
		//Tile[][] tileSet;
		

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
			// pre-load resources 
			//tileSet = new Tile[21][26];
			//ResourceLoader.getResourceAsStream(GAME_BOARD);
			
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
				app = new AppGameContainer(new EscapeGame("EFOMJ", 672, 832));
				app.setDisplayMode(832, 672, false);
				app.setVSync(true);
				app.start();
				
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}	
	}
