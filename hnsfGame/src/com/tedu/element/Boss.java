package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Boss extends ElementObj {
    private int maxHp = 30;   // BOSS最大血量
    private int currentHp = maxHp; // 当前血量
    private String fx = "edown"; // 初始方向
    private int space = 2;
    private long lastMoveTime = 0;
    private long lastShotTime = 0;
    private int Number = 0;
    private static int Mdis = 40; // 移动间隔(毫秒)
    private static int Sdis = 200; // 射击间隔(毫秒)
    private Random rand = new Random();

    @Override
    public void showElement(Graphics g) {
        // 绘制BOSS图像
        g.drawImage(this.getIcon().getImage(), 
                    this.getX(), this.getY(), 
                    this.getW(), this.getH(), null);
        
        // 绘制血条背景（红色）
        int barWidth = this.getW();
        int barHeight = 5;
        int barX = this.getX();
        int barY = this.getY() - 10;
        
        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);
        
        // 绘制当前血量（绿色）
        int currentWidth = (int)(barWidth * ((double)currentHp / maxHp));
        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, currentWidth, barHeight);
        
        // 绘制血量文本
        g.setColor(Color.WHITE);
        g.setFont(new Font("宋体", Font.BOLD, 12));
        String hpText = currentHp + "/" + maxHp;
        int textX = barX + (barWidth - g.getFontMetrics().stringWidth(hpText)) / 2;
        int textY = barY - 2;
        g.drawString(hpText, textX, textY);
    }

    @Override
    public ElementObj creatElement(String str) {
        // 初始化BOSS位置和属性
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
    public void onCollision(ElementObj other) {
        if (other instanceof PlayFile) {
            // 被子弹击中减少血量
        	other.setLive(false);
            currentHp-=other.getAttack();
            if(Number == 0)
            {
            	 if(currentHp <= maxHp*0.5)
                 {
                 	this.Mdis=20;
                 	this.Sdis=80;
                 }
            } 
            
            if (currentHp <= 0) {
            	if(Number == 0)
            	{
            		Number = 1;
            		this.space = 5;
    
            	}
            	else
            	{
            		this.die(); // 血量归零时死亡
				}
               
            }
            
            
        }
        if (other instanceof Play) {
            // 被子弹击中减少血量
            other.setLive(false);
        }
        
    }

    @Override
    public void die() {
        super.setLive(false);
        // 添加死亡效果
        ElementObj dieEffect = GameLoad.getObj("die").creatElement(
            this.getX() + "," + this.getY() + ",die");
        ElementManager.getManager().addElement(dieEffect, GameElement.DIE);
    }

    @Override
    protected void move(long gameTime) {
        if (gameTime - lastMoveTime < Mdis) {
            return;
        }
        lastMoveTime = gameTime;

        // 随机移动方向
        if (rand.nextInt(100) < 20) { // 20%几率改变方向
            String[] directions = {"eup", "edown", "eleft", "eright"};
            fx = directions[rand.nextInt(directions.length)];
        }

        // 根据方向移动
        switch (fx) {
            case "eup": 
                if (this.getY() > 0) this.setY(this.getY() - space);
                break;
            case "edown": 
                if (this.getY() < 600 - this.getH()) this.setY(this.getY() + space );
                break;
            case "eleft": 
                if (this.getX() > 0) this.setX(this.getX() - space);
                break;
            case "eright": 
                if (this.getX() < 900 - this.getW()) this.setX(this.getX() + space);
                break;
        }
    }

    @Override
    protected void add(long gameTime) {
        if (gameTime - lastShotTime < Sdis) {
            return;
        }
        lastShotTime = gameTime;
        
        // 发射子弹
        for(int i = 0;i < 4;i++)
        {
        	 ElementObj bullet1 = GameLoad.getObj("file").creatElement(this.toString(i));
             ElementManager.getManager().addElement(bullet1, GameElement.PLAYFILE);
        }
       
    }
    public void setMaxHp(int level)
    {
    	this.maxHp=level;
    	this.currentHp = maxHp;
    }
    
    public int getMaxHp()
    {
    	return this.maxHp;
    }
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
    
    public String toString(int k) {
    	int x=this.getX();
		int y=this.getY();
		String fang = new String();
		switch(k) { //子弹再发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
		case 0:x+=this.getIcon().getIconWidth()*3/8-2;y-=15;fang="eup";break;
		//一般不会写20等数值，一般情况下图片大小就是显示大小；一般情况用图片大小参与运算
		case 1:y+=this.getIcon().getIconHeight()*2/5;x-=15;fang="eleft";break;
		case 2:x+=this.getIcon().getIconWidth()+5;y+=this.getIcon().getIconHeight()*2/5;fang="eright";break;
		case 3:y+=this.getIcon().getIconHeight()+5;x+=this.getIcon().getIconWidth()*2/5;fang="edown";break;
		}
		return "x:"+x+",y:"+y+",f:"+fang;
	}
}