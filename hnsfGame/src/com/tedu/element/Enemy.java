package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Enemy extends ElementObj{

	private boolean eleft=false;//左
	private boolean eup=false; //上
	private boolean eright=false; //右
	private boolean edown=true; //下
	private String fx="edown";
	private long lastMoveTime = 0;
	private long lastShotTime = 0;
	private static final int Mdis = 10; // 移动间隔(毫秒)
	private static final int Sdis = 200; // 射击间隔(毫秒)

	    
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), 
				this.getY(), this.getW(), this.getH(), null);
	}
	@Override
//	public ElementObj creatElement(String str) {
//		Random ran=new Random();
//		int x=ran.nextInt(700);
//		int y=ran.nextInt(500);
//		this.setX(x);
//		this.setY(y);
//		this.setW(35);
//		this.setH(35);
//		this.setIcon(new ImageIcon("image/tank/bot/bot_down.png"));
//		return this;
//	}
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
	public void die() {
		ElementObj obj=GameLoad.getObj("die");
		ElementObj element =obj.creatElement(""+this.getX()+","+this.getY()+",die");
		ElementManager.getManager().addElement(element, GameElement.DIE);
	}
		
	
		private Random rand = new Random();

		@Override
		public void onCollision(ElementObj other) {
			 if (other instanceof MapObj) {
		            // 碰撞后回退位置
		            this.setX(this.getX() - (eleft ? -4 : (eright ? 4 : 0)));
		            this.setY(this.getY() - (eup ? -4 : (edown ? 4 : 0)));
		            
		            // 随机选择新方向
		            String[] directions = {"eup", "edown", "eleft", "eright"};
		            int index = rand.nextInt(directions.length);
		            this.fx = directions[index];
		            updateDirection();
		        }
		        if (other instanceof PlayFile) {
		            this.setLive(false);
		        }
		}
		// 根据方向设置移动状态
	    private void updateDirection() {
	        eleft = fx.equals("eleft");
	        eright = fx.equals("eright");
	        eup = fx.equals("eup");
	        edown = fx.equals("edown");
	    }
		
		public void add(long gameTime) {//时间可以进行控制
			if (gameTime - lastShotTime < Sdis) {
	            return;
	        }
	        lastShotTime = gameTime;
	        
			ElementObj obj=GameLoad.getObj("file");
			ElementObj element =obj.creatElement(this.toString());
			ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
		}
		
		@Override
		public String toString() {//这里是偷懒直接使用toString,建议自己定义一个方法
			int x=this.getX();
			int y=this.getY();
			switch(this.fx) { //子弹再发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
			case "eup":x+=this.getIcon().getIconWidth()*3/8-2;y-=10;break;
			//一般不会写20等数值，一般情况下图片大小就是显示大小；一般情况用图片大小参与运算
			case "eleft":y+=this.getIcon().getIconHeight()*2/5;x-=10;break;
			case "eright":x+=this.getIcon().getIconWidth()+5;y+=this.getIcon().getIconHeight()*2/5;break;
			case "edown":y+=this.getIcon().getIconHeight()+5;x+=this.getIcon().getIconWidth()*2/5;break;
			}
			return "x:"+x+",y:"+y+",f:"+this.fx;
		}
		
		@Override
		protected void updateImage() {
			this.setIcon(GameLoad.imgMap.get(fx));
		}
		
		@Override
		public void move(long gametime) {
			if(gametime-lastMoveTime>Mdis)
			{
				lastMoveTime = gametime;
				updateDirection();
		        
		        // 根据方向移动
		        if (eleft) {
		            this.setX(this.getX() - 2);
		        } else if (eright) {
		            this.setX(this.getX() + 2);
		        } else if (eup) {
		            this.setY(this.getY() - 2);
		        } else if (edown) {
		            this.setY(this.getY() + 2);
		        }
		        
		        // 随机改变方向(5%的几率)
		        if (rand.nextInt(100) < 5) {
		            String[] directions = {"eup", "edown", "eleft", "eright"};
		            int index = rand.nextInt(directions.length);
		            this.fx = directions[index];
		        }
			
			}
		}
}
