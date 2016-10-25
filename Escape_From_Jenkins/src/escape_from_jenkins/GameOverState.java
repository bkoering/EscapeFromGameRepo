package escape_from_jenkins;

import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;


class GameOverState extends BasicGameState {
	

	private int timer;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 4000;
	}

	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		EscapeGame eg = (EscapeGame)game;
		
		if(eg.WinState==true)
			g.drawImage(ResourceManager.getImage(EscapeGame.WIN_BANNER), 100, 0);
		else
		g.drawImage(ResourceManager.getImage(EscapeGame.GAMEOVER_BANNER_RSC), 100, 0);
		

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		
		timer -= delta;
		if (timer <= 0)
			game.enterState(EscapeGame.STARTUPSTATE, new EmptyTransition(), new HorizontalSplitTransition() );

		
		
	}

	@Override
	public int getID() {
		return EscapeGame.GAMEOVERSTATE;
	}
	
}