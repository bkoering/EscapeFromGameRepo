package escape_from_jenkins;

import java.util.Iterator;
import java.util.List;
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
	int Base, Gnome, Plane, Maze, Start, Water;
	
	TiledMap map;
	Tile tile;

	//long startTime = System.currentTimeMillis();
	long currentTime;
	long tDelta;
	long countDownToScruffy;
	
	int[][] mazeCollision = new int[21][22];
	int[][] gnomeCollision = new int[21][22];
	int[][] dMap = new int[21][22];
	int[][] waterCollision = new int[21][22];


	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		try{
			map = new TiledMap("src/escape/resource/GBlvl1.tmx");
			} 
		catch (SlickException e){
			System.out.println("Slick Exception Error: map failed to load");
		}
		EscapeGame eg = (EscapeGame)game;
		eg.startTime = System.currentTimeMillis();
		
		
		//grab map layers
		Base = map.getLayerIndex("Base");
		Gnome = map.getLayerIndex("gnome");
		Maze = map.getLayerIndex("maze");
		Plane = map.getLayerIndex("plane");
		Start = map.getLayerIndex("startZones");
		Water = map.getLayerIndex("waterCollision");
				
		for(int i = 0; i < 21; i++){ 
			for(int j = 0; j < 22; j++){ 
				if(map.getTileId(i, j, Maze) > 0){ 
					mazeCollision[i][j] = 1; 
				} 
			}
		}
		
		for(int i = 0; i < 21; i++){ 
			for(int j = 0; j < 22; j++){ 
				if(map.getTileId(i, j, Gnome) > 0){ 
					gnomeCollision[i][j] = 1; 
				} 
			}
		}
		for(int i = 0; i < 21; i++){ 
			for(int j = 0; j < 22; j++){ 
				if(map.getTileId(i, j, Water) > 0){ 
					waterCollision[i][j] = 1; 
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
			map.render(0, 0, Maze);
			map.render(0, 0, Start);
			
			if (eg.hasPlane == false){
				map.render(0, 0, Plane);
			}
	
			for (int i =0; i<4; i++){
				eg.cat[i].render(g);
			}
			
			for (int i =0; i<9; i++){
				eg.log[i].render(g);
			}
			
			eg.player.render(g);
			eg.oldMan.render(g);
			if(eg.releaseScruffy==true)
				eg.scruffy.render(g);
			
		g.drawString("Lives: " + Lives, 10, 30);
		
		if (countDownToScruffy >=0)
			g.drawString("Incoming Scruffy: " + countDownToScruffy, 10, 50);
		else
			g.drawString("Incoming Scruffy: 0", 10, 50);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Input input = container.getInput();
		EscapeGame eg = (EscapeGame)game;
	
		int xPos;
		int yPos;
		int xPosOldMan;
		int yPosOldMan;
		int xPosScruf;
		int yPosScruf;
		
		xPos = (int) Math.floor(eg.player.getX()/32);
		yPos = (int) Math.floor(eg.player.getY()/32);
		
		xPosOldMan = (int) Math.floor(eg.oldMan.getX()/32);
		yPosOldMan = (int) Math.floor(eg.oldMan.getY()/32);
		
		xPosScruf = (int) Math.floor(eg.scruffy.getX()/32);
		yPosScruf = (int) Math.floor(eg.scruffy.getY()/32);
		
		
		currentTime = System.currentTimeMillis();
		tDelta = currentTime - eg.startTime;
		countDownToScruffy = (long) (30.0 - (tDelta / 1000.0));
		if (countDownToScruffy <= 0)
			eg.releaseScruffy = true;
		
		//moving the kid-----------------------
		if ((input.isKeyDown(Input.KEY_LEFT)) && !(input.isKeyDown(Input.KEY_RIGHT)) &&
				!(input.isKeyDown(Input.KEY_UP)) && !(input.isKeyDown(Input.KEY_DOWN))){
			
			if((eg.player.getCoarseGrainedMinX() > 0)) {
				if(xPos-1 > 0){
					if(mazeCollision[xPos-1][yPos] == 0)
						eg.player.setPosition(eg.player.getX()-5, eg.player.getY());
				}
				else
					eg.player.setPosition(eg.player.getX()-5, eg.player.getY());
			}
		}
		//-------------------------
		if ((input.isKeyDown(Input.KEY_RIGHT)) && !(input.isKeyDown(Input.KEY_LEFT)) &&
				!(input.isKeyDown(Input.KEY_UP)) && !(input.isKeyDown(Input.KEY_DOWN))){
			
			if((eg.player.getCoarseGrainedMaxX()< eg.getScreenWidth()) ){
				if(xPos < 20){
					if(mazeCollision[xPos+1][yPos] == 0)
						eg.player.setPosition(eg.player.getX()+5, eg.player.getY());
				}
				else
					eg.player.setPosition(eg.player.getX()+5, eg.player.getY());
			}
			
		}
		//------------------------------------
		if ((input.isKeyDown(Input.KEY_UP)) && !(input.isKeyDown(Input.KEY_DOWN)) &&
				!(input.isKeyDown(Input.KEY_LEFT)) && !(input.isKeyDown(Input.KEY_RIGHT))) {
			
			if(eg.player.getCoarseGrainedMinY() > 0)
			{
				if(yPos > 0){
					if(mazeCollision[xPos][yPos-1] == 0)
						eg.player.setPosition(eg.player.getX(), eg.player.getY()-5);
				}
				else
					eg.player.setPosition(eg.player.getX(), eg.player.getY()-5);
			}
		}
		//----------------------------------------
		if ((input.isKeyDown(Input.KEY_DOWN)) && !(input.isKeyDown(Input.KEY_UP)) &&
				!(input.isKeyDown(Input.KEY_LEFT)) && !(input.isKeyDown(Input.KEY_RIGHT))){
			
			if(eg.player.getCoarseGrainedMaxY()< eg.getScreenHeight()){
				if(yPos < 21){
					if(mazeCollision[xPos][yPos+1] == 0)
						eg.player.setPosition(eg.player.getX(), eg.player.getY()+5);
					}
				else
					eg.player.setPosition(eg.player.getX(), eg.player.getY()+5);
			}
		}
		

		//lose a life if collide with gnome and return to start
		if(gnomeCollision[xPos][yPos] == 1){
			Lives--;
			eg.player.setPosition(656, 688);
			eg.oldMan.setPosition(16, 496);
			eg.scruffy.setPosition(16, 496);
			eg.hasPlane = false;
			eg.releaseScruffy=false;
			eg.startTime = System.currentTimeMillis();
		}
		
		
		//log collision in the water section 
		if(waterCollision[xPos][yPos] == 1){//in water section
			boolean onlog = false;
			for(int i=0; i<9;i++){
					if(eg.player.collides(eg.log[i]) != null){ //on the log
						eg.player.setVelocity(eg.log[i].getVelocity());
						eg.player.update(delta);
					onlog=true;
					}
			}
			if (!onlog) {
					Lives--;
					eg.player.setPosition(656, 688);
					eg.oldMan.setPosition(16, 496);
					eg.hasPlane = false;
					eg.releaseScruffy=false;
					eg.startTime = System.currentTimeMillis();
					
			}
		}
		//cat collision
		for(int i=0; i<4; i++){
			if (eg.player.collides(eg.cat[i]) != null){
				Lives--;
				eg.player.setPosition(656, 688);
				eg.oldMan.setPosition(16, 496);
				eg.scruffy.setPosition(16, 496);
				eg.hasPlane = false;
				eg.releaseScruffy=false;
				eg.startTime = System.currentTimeMillis();
			}	
		}
		
		//HERE COMES OLD MAN JENKINS! - a.k.a. the path finding section--------------
		//fill map with "walls"
		for(int j = 0; j < 22; j++){  //y
			for(int i = 0; i < 21; i++){ //x
				if((map.getTileId(i, j, map.getLayerIndex("maze")) > 0 || 
						(map.getTileId(i, j, map.getLayerIndex("gnome"))) > 0)){ 
					dMap[i][j] = 100; 
				} 
				else
					dMap[i][j] = -1;
			}
		}
		
		
		//set player position
		dMap[xPos][yPos]=0;

		//DEBUG*****************
//		System.out.println("");
//		System.out.println("");
//		System.out.println("WALLS MAP");
//
//		for(int j=0;j<22;j++){
//			for(int i=0;i<21;i++){
//				System.out.print(dMap[i][j] + "   ");
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
		//********************************
		
		//fill tile map with weights
		List<Tile> list = new ArrayList<Tile>();
		list.add(new Tile(xPos,yPos));
		Iterator<Tile> iterate = list.iterator();
				
		while(iterate.hasNext()){
			tile = iterate.next();
			
			//fill right
			if((tile.getX()+1 < 21) && (dMap[tile.getX()+1][tile.getY()] != 100)){
				if(dMap[tile.getX()+1][tile.getY()] == -1 ||
				   dMap[tile.getX()+1][tile.getY()] > dMap[tile.getX()][tile.getY()]+1){
						dMap[tile.getX()+1][tile.getY()] = dMap[tile.getX()][tile.getY()]+1;
						list.add(new Tile(tile.getX()+1, tile.getY()));
						iterate = list.iterator();
				}
			}
			//fill left
			if((tile.getX()-1 >= 0) && (dMap[tile.getX()-1][tile.getY()] != 100)){
				if(dMap[tile.getX()-1][tile.getY()] == -1 || 
				   dMap[tile.getX()-1][tile.getY()] > dMap[tile.getX()][tile.getY()]+1){
						dMap[tile.getX()-1][tile.getY()] = dMap[tile.getX()][tile.getY()]+1;
						list.add(new Tile(tile.getX()-1, tile.getY()));
						iterate = list.iterator();
				}
			}
			//fill down
			if((tile.getY()+1 < 22) && (dMap[tile.getX()][tile.getY()+1] != 100)){
				if(dMap[tile.getX()][tile.getY()+1] == -1 || 
				   dMap[tile.getX()][tile.getY()+1] > dMap[tile.getX()][tile.getY()]+1){
						dMap[tile.getX()][tile.getY()+1] = dMap[tile.getX()][tile.getY()]+1;
						list.add(new Tile(tile.getX(), tile.getY()+1));
						iterate = list.iterator();
				}
			}
			//fill up
			if((tile.getY()-1 >= 0) && (dMap[tile.getX()][tile.getY()-1] != 100)){
				if(dMap[tile.getX()][tile.getY()-1] == -1 || 
				   dMap[tile.getX()][tile.getY()-1] > dMap[tile.getX()][tile.getY()]+1){
					 dMap[tile.getX()][tile.getY()-1] = dMap[tile.getX()][tile.getY()]+1;
				     list.add(new Tile(tile.getX(), tile.getY()-1));
				     iterate = list.iterator();
				}
			}
		}//while iterate end

		int bestMove,bestMoveS;
		boolean tUp, tDown, tLeft, tRight;
		boolean tUpS, tDownS, tLeftS, tRightS;
		tUp = tDown = tLeft = tRight = false;
		tUpS = tDownS = tLeftS = tRightS = false;
		
		if (eg.player.collides(eg.oldMan) == null){ //check for lowest value tile around old man jenkins
			
			bestMove = 300; //set to some ridiculously high number so it will have to pick one of the 4 directions
			
			if(xPosOldMan+1 != 21){
				if(bestMove >= dMap[xPosOldMan+1][yPosOldMan])//check right
				{
					bestMove = dMap[xPosOldMan+1][yPosOldMan];
					tUp = tDown = tLeft = false;
					tRight=true;
				}
			}
			
			if(xPosOldMan-1 > 0){
				if(bestMove >= dMap[xPosOldMan-1][yPosOldMan])//check left
				{
					bestMove = dMap[xPosOldMan-1][yPosOldMan];
					tUp = tDown = tRight = false;
					tLeft=true;
				}
			}
			
			if(yPosOldMan-1 > 0){
				if(bestMove >= dMap[xPosOldMan][yPosOldMan-1])//check up
				{
					bestMove = dMap[xPosOldMan][yPosOldMan-1];
					tDown = tLeft = tRight = false;
					tUp=true;
				}
			}
			
			if(yPosOldMan+1 != 22){
				if(bestMove >= dMap[xPosOldMan][yPosOldMan+1])//check down
				{
					bestMove = dMap[xPosOldMan][yPosOldMan+1];
					tLeft = tUp = tRight = false;
					tDown=true;
				}
			}
			
			//System.out.println(eg.oldMan.getX());
			if (tUp == true && (eg.oldMan.getY()>160))
				eg.oldMan.setPosition(eg.oldMan.getX(), eg.oldMan.getY()-1); //move up
			if (tDown == true)
				eg.oldMan.setPosition(eg.oldMan.getX(), eg.oldMan.getY()+1); //move down
			if (tLeft == true)
				eg.oldMan.setPosition(eg.oldMan.getX()-1, eg.oldMan.getY()); //move left
			if (tRight == true)
				eg.oldMan.setPosition(eg.oldMan.getX()+1, eg.oldMan.getY()); //move right
			
		}
		else 
		{	
				Lives--;
				eg.player.setPosition(656, 688);
				eg.oldMan.setPosition(16, 496);
				eg.scruffy.setPosition(16, 496);
				eg.hasPlane = false;
				eg.releaseScruffy=false;
				eg.startTime = System.currentTimeMillis();
		}
		
		//INCOMING SCRUFFY 
		if(eg.releaseScruffy == true){
			if (eg.player.collides(eg.scruffy) == null){ //check for lowest value tile around old man jenkins
				
				bestMoveS = 300; //set to some ridiculously high number so it will have to pick one of the 4 directions
				
				if(xPosScruf+1 != 21){
					if(bestMoveS >= dMap[xPosScruf+1][yPosScruf])//check right
					{
						bestMoveS = dMap[xPosScruf+1][yPosScruf];
						tUpS = tDownS = tLeftS = false;
						tRightS=true;
					}
				}
				
				if(xPosScruf-1 > 0){
					if(bestMoveS >= dMap[xPosScruf-1][yPosScruf])//check left
					{
						bestMoveS = dMap[xPosScruf-1][yPosScruf];
						tUpS = tDownS = tRightS = false;
						tLeftS=true;
					}
				}
				
				if(yPosScruf-1 > 0){
					if(bestMoveS >= dMap[xPosScruf][yPosScruf-1])//check up
					{
						bestMoveS = dMap[xPosScruf][yPosScruf-1];
						tDownS = tLeftS = tRightS = false;
						tUpS=true;
					}
				}
				
				if(yPosScruf+1 != 22){
					if(bestMoveS >= dMap[xPosScruf][yPosScruf+1])//check down
					{
						bestMoveS = dMap[xPosScruf][yPosScruf+1];
						tLeftS = tUpS = tRightS = false;
						tDownS=true;
					}
				}
				
				if (tUpS == true && (eg.scruffy.getY()>160))
					eg.scruffy.setPosition(eg.scruffy.getX(), eg.scruffy.getY()-3); //move up
				if (tDownS == true)
					eg.scruffy.setPosition(eg.scruffy.getX(), eg.scruffy.getY()+3); //move down
				if (tLeftS == true)
					eg.scruffy.setPosition(eg.scruffy.getX()-3, eg.scruffy.getY()); //move left
				if (tRightS == true)
					eg.scruffy.setPosition(eg.scruffy.getX()+3, eg.scruffy.getY()); //move right
				
			}
			else 
			{	//this stays the same as old man jenkins section since it all still applies
					Lives--;
					eg.player.setPosition(656, 688);
					eg.oldMan.setPosition(16, 496);
					eg.scruffy.setPosition(16, 496);
					eg.hasPlane = false;
					eg.releaseScruffy=false;
					eg.startTime = System.currentTimeMillis();
			}
			
		}
		
		//DEBUGGING************
//		System.out.println("");
//		System.out.println("");
//		System.out.println("DIJKSTRAS MAP");
//
//		for(int j=0;j<22;j++){
//			for(int i=0;i<21;i++){
//				System.out.print(dMap[i][j] + "   ");
//			}
//			System.out.println("");
//		}
//		System.out.println("");
//		System.out.println("");
//		System.out.println("");
		//************************

		
		
		
		//win state activation and game over state activation-----------
		if ((xPos == 11) && (yPos ==0)){
			eg.hasPlane = true;
			//System.out.println("hasplane");
		}
		if((xPos == 20) && (yPos == 21) && (eg.hasPlane == true)) {
			eg.WinState = true;
			//System.out.println("winstate");
		}
		
		if (Lives <= 0 || eg.WinState == true) {		
			game.enterState(EscapeGame.GAMEOVERSTATE);
		}
		
		
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
