package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class MapObj extends ElementObj{
	//墙需要血量
	private int hp=1;
	private String name; //墙的type，也可以使用枚举

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(),
				this.getY(),this.getW(),this.getH(), null);
		
	}
	@Override //如果可以传入 墙类型，x,y
	public ElementObj creatElement(String str) {
		String [] arr=str.split(",");
		//先写一个假图片
		ImageIcon icon=null;
		switch(arr[0]) { //设置图片信息，图片还未加载到内存中
		case "GRASS":icon=new ImageIcon("image/wall/grass.png");
					this.hp=1;
					name="GRASS";
					break;
		case "BRICK":icon=new ImageIcon("image/wall/brick.png");
					this.hp=1;
					name="BRICK";
					break;
		case "RIVER":icon=new ImageIcon("image/wall/river.png");
					this.hp=1;
					name="RIVER";
					break;
		case "IRON":icon=new ImageIcon("image/wall/iron.png");
					this.hp=4;
					name="IRON";
					break;
		}
		int x=Integer.parseInt(arr[1]);
		int y=Integer.parseInt(arr[2]);
		int w=icon.getIconWidth();
		int h=icon.getIconHeight();
		this.setH(h);
		this.setW(w);
		this.setX(x);
		this.setY(y);
		this.setIcon(icon);
		return this;
	}
	@Override
	public void setLive(boolean live) {
		//被调用一次就减少一次血
		if("IRON".equals(name)) { //水泥墙需要4下
			this.hp--;
			if(this.hp>0) {
				return;
			}
		}
		super.setLive(live);
	}
	@Override
	public void onCollision(ElementObj other) {
		if (other instanceof PlayFile && !"RIVER".equals(name)) {
		       this.setLive(false);
		    }
		if (other instanceof PlayFile && "RIVER".equals(name)) {
		       other.setLive(true);
		    }
		
		if (other instanceof Boss && !"RIVER".equals(name)) {
		       this.setLive(false);
		    }
		
	}
	
	
	
	
	
}
