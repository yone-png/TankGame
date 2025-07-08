package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.PlayFile;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 监听类，用于监听用户操作KeyListener
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	
	 private Set<Integer> set=new HashSet<Integer>();
	
	// 玩家1控制键
	    private static final int PLAYER1_UP = KeyEvent.VK_UP;
	    private static final int PLAYER1_DOWN = KeyEvent.VK_DOWN;
	    private static final int PLAYER1_LEFT = KeyEvent.VK_LEFT;
	    private static final int PLAYER1_RIGHT = KeyEvent.VK_RIGHT;
	    private static final int PLAYER1_FIRE = KeyEvent.VK_SPACE;
	    
	    // 玩家2控制键
	    private static final int PLAYER2_UP = KeyEvent.VK_W;
	    private static final int PLAYER2_DOWN = KeyEvent.VK_S;
	    private static final int PLAYER2_LEFT = KeyEvent.VK_A;
	    private static final int PLAYER2_RIGHT = KeyEvent.VK_D;
	    private static final int PLAYER2_FIRE = KeyEvent.VK_F;
	 
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存 根
		
	}
	/**
	 * 按下:左37上38右39下40
	 * 首先主角的移动
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		//拿到玩家集合
//		System.out.println("按下"+e.getKeyCode());
		int key=e.getKeyCode();
		if(set.contains(key)) {
			//如果包含直接结束
			return;
		}
		set.add(key);
		List<ElementObj> palys=em.getElementsByKey(GameElement.PLAY);
		for(ElementObj player:palys) {
			Play play=(Play) player;
			// 玩家1控制
            if(play.getPlayerId() == 1) {
                if(key == PLAYER1_UP || key == PLAYER1_DOWN || 
                   key == PLAYER1_LEFT || key == PLAYER1_RIGHT || 
                   key == PLAYER1_FIRE) {
                    play.keyClick(true, key);
                }
            }
            // 玩家2控制
            else if(play.getPlayerId() == 2) {
                if(key == PLAYER2_UP || key == PLAYER2_DOWN || 
                   key == PLAYER2_LEFT || key == PLAYER2_RIGHT || 
                   key == PLAYER2_FIRE) {
                    play.keyClick(true, key);
                }
           }
	}
}
	/**
	 * 松开
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		 int key = e.getKeyCode();
	        if(!set.contains(key)) return;
	        set.remove(key);
	        
	        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
	        for(ElementObj player : players) {
	            Play play = (Play)player;
	            // 玩家1控制
	            if(play.getPlayerId() == 1) {
	                if(key == PLAYER1_UP || key == PLAYER1_DOWN || 
	                   key == PLAYER1_LEFT || key == PLAYER1_RIGHT || 
	                   key == PLAYER1_FIRE) {
	                    play.keyClick(false, key);
	                }
	            }
	            // 玩家2控制
	            else if(play.getPlayerId() == 2) {
	                if(key == PLAYER2_UP || key == PLAYER2_DOWN || 
	                   key == PLAYER2_LEFT || key == PLAYER2_RIGHT || 
	                   key == PLAYER2_FIRE) {
	                    play.keyClick(false, key);
	                }
	            }
	        }
	}

}
