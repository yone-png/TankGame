package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 监听类，用于监听用户操作KeyListener
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	
	 private Set<Integer> set=new HashSet<Integer>();
	
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
		List<ElementObj> paly=em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:paly) {
			obj.keyClick(true,e.getKeyCode());
		}
	}
	/**
	 * 松开
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if(!set.contains(e.getKeyCode())) {
			return;
		}
		set.remove(e.getKeyCode());
		List<ElementObj> paly=em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:paly) {
			obj.keyClick(false,e.getKeyCode());
		}
	}

}
