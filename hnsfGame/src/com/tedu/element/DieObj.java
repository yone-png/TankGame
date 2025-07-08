package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;

public class DieObj extends ElementObj{
	private long Begintime = 0L;

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), 
				this.getY(), this.getW(), this.getH(), null);
	}

	@Override
	public ElementObj creatElement(String str) {
		String[] split=str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon2=GameLoad.imgMap.get(split[2]);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	@Override
	public void move(long gametime) {
		if(gametime-Begintime > 20)
		{
			Begintime=gametime;
			return;
		}
		if(gametime-Begintime > 15)
		{
			this.setLive(false);
		}
	}
}
