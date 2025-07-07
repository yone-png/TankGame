package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class Enemy extends ElementObj{

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), 
				this.getY(), this.getW(), this.getH(), null);
	}
	@Override
	public ElementObj creatElement(String str) {
		Random ran=new Random();
		int x=ran.nextInt(700);
		int y=ran.nextInt(500);
		this.setX(x);
		this.setY(y);
		this.setW(35);
		this.setH(35);
		this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
		return this;
	}
	
//	@Override
//	public void die() {
//		ElementManager em=ElementManager.getManager();
//		ImageIcon icon=new ImageIcon("image/tank/play2/player2_up.png");
//		ElementObj obj=new Enemy();//实例化对象
//		em.addElement(obj,GameElement.DIE);//直接添加
//	}
		
	
		private Random rand = new Random();

		@Override
		public void onCollision(ElementObj other) {
		    if (other instanceof MapObj) {
		        // 随机选择新方向
		        String[] directions = {"up", "down", "left", "right"};
		        int index = rand.nextInt(directions.length);
		        // 这里需要实现enemy的转向逻辑（根据具体实现调整）
		        // enemy.changeDirection(directions[index]);
		    }
		    if (other instanceof PlayFile) {
			       this.setLive(false);
			    }
		}
}
