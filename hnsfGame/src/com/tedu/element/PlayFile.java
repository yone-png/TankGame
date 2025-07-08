package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 玩家子弹类，本类的实体对象是由玩家对象调用和创建
 * @子类的开发步骤
 * 	1.继承元素基类，重写show方法
 * 	2.选择性重写其他方法如move等
 * 	3.思考并定义子类特有的属性
 */
public class PlayFile extends ElementObj{
	private int attack=1;//攻击力
	private int moveNum=3;//移动速度值
	private String fx;
	//剩下的扩展；可以扩展出多种子弹：激光，导弹等。（玩家类需要由子弹类型）
	
	public PlayFile() {}//一个空的默认构造方法
	//对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值结束对象实体
	@Override	//传递一个固定格式 {x:3,y:5,f:up} json格式
	public ElementObj creatElement(String str) {//定义字符串的规则
		String[] split=str.split(",");
		for(String str1 :split) {//x:3
			String[] split2=str1.split(":");//0下标是x,y,f 1下标是值
			switch(split2[0]) {
			case "x":this.setX(Integer.parseInt(split2[1]));break;
			case "y":this.setY(Integer.parseInt(split2[1]));break;
			case "f":this.fx=split2[1];break;
			//子弹速度过快需要解决
			}
		}
		this.setW(10);
		this.setH(10);
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		int x=this.getX();
		g.setColor(Color.red);//new Color(255,255,255)
		g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
	}

	@Override
	protected void move(long gametime) {
		if(this.getX()<0 || this.getX()>900  ||this.getY()<0 ||this.getY()>600) {
			this.setLive(false);
			return;
		}
		switch(this.fx) {
		case "eup":this.setY(this.getY()-this.moveNum);break;
		case "eleft":this.setX(this.getX()-this.moveNum);break;
		case "eright":this.setX(this.getX()+this.moveNum);break;
		case "edown":this.setY(this.getY()+this.moveNum);break;
		
		case "up":this.setY(this.getY()-this.moveNum);break;
		case "left":this.setX(this.getX()-this.moveNum);break;
		case "right":this.setX(this.getX()+this.moveNum);break;
		case "down":this.setY(this.getY()+this.moveNum);break;
		}

	}
	/**
	 * 对于子弹来说：1.出边界 2.碰撞 3.玩家放保险
	 * 处理方式：当打到死亡的条件时，值进行修改死亡状态的操作
	 */
	@Override
	public void onCollision(ElementObj other) {
		if (other instanceof MapObj ) {
	       this.setLive(false);
	    }
		if(other instanceof Enemy) {
			this.setLive(false);
		}
	}
	
	
	
//	@Override
//	public void die() {
//		ElementManager em=ElementManager.getManager();
//		ImageIcon icon=new ImageIcon("image/tank/play2/player2_up.png");
//		ElementObj obj=new Play(this.getX(),this.getY(),50,50,icon);//实例化对象
//		em.addElement(obj,GameElement.DIE);//直接添加
//	}
//	/**子弹变装*/
//	private long time=0;
//	protected void updateImage(long gameTime) {
//		if(gameTime-time>5) {
//			time=gameTime;//为下次变装做准备
//			this.setW(this.getW()+2);
//			this.setH(this.getH()+2);
//			//放图片就型
//		}
//	}
	
	
	
}
























