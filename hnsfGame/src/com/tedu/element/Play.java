package com.tedu.element;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj{
	
	/**
	 *移动属性：
	 *1. 单属性 配合 方向枚举类型使用；一次只能移动一个方向
	 *2. 双属性 上下和左右 配合Boolean值使用 
	 *			 	约定 0不动1上2下
	 *3. 4属性 上下左右都可以 true代表移动，false不移动
	 *					同时按上和下，后按的会重置先按的
	 *
	 *@问题 1.图片要读取到内存中： 加载器 临时处理方式。手动编写存储到内存
	 *		2.什么时候进行修改图片
	 *		3.图片应该使用什么集合存储
	 */
	private boolean left=false;//左
	private boolean up=false; //上
	private boolean right=false; //右
	private boolean down=false; //下
	

	//变量专门用来记录当前主角面向的方向，默认为up
	private String fx="up";
	private boolean pkType=false;//攻击状态true攻击false停止 
	
	private int playerId=1; //玩家ID，1为玩家1，2为玩家2
	
	public Play() {}
	
	public Play(int playerId) {
        this.playerId = playerId;
    }
	
    
    public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerId() {
        return playerId;
    }

	
	@Override
	public ElementObj creatElement(String str) {
		String[] split=str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		//根据玩家ID选择图片
		String imageKey=split[2];
		if(playerId==2) {
			imageKey+="2";
		}
		ImageIcon icon2=GameLoad.imgMap.get(imageKey);
		this.setW(icon2.getIconWidth());
		this.setH(icon2.getIconHeight());
		this.setIcon(icon2);
		return this;
	}
	
	
	/**
	 * 面向对象中第1个思想： 对象自己的事情自己做
	 */
	@Override
	public void showElement(Graphics g) {
		//绘画图片
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	
	/**
	 * 重写
	 * @重点：监听的数据需要改变状态值
	 */
	public void keyClick(boolean b1,int key) {
//		System.out.println("测试"+key);
		if(b1) {
            // 玩家1控制
            if(playerId == 1) {
                handlePlayer1KeyPress(key);
            } 
            // 玩家2控制
            else if(playerId == 2) {
                handlePlayer2KeyPress(key);
            }
        } else {
            handleKeyRelease(key);
        }

	}
	
	private void handlePlayer1KeyPress(int key) {
        switch(key) {
            case KeyEvent.VK_LEFT:
                this.up = false; this.down = false;
                this.right = false; this.left = true;
                this.fx = "left"; break;
            case KeyEvent.VK_UP:
                this.right = false; this.left = false;
                this.down = false; this.up = true;
                this.fx = "up"; break;
            case KeyEvent.VK_RIGHT:
                this.up = false; this.down = false;
                this.left = false; this.right = true;    
                this.fx = "right"; break;
            case KeyEvent.VK_DOWN:
                this.right = false; this.left = false;
                this.up = false; this.down = true;        
                this.fx = "down"; break;
            case KeyEvent.VK_SPACE:
                this.pkType = true; break;
        }
    }
    
    private void handlePlayer2KeyPress(int key) {
        switch(key) {
            case KeyEvent.VK_A: // A键
                this.up = false; this.down = false;
                this.right = false; this.left = true;
                this.fx = "left"; break;
            case KeyEvent.VK_W: // W键
                this.right = false; this.left = false;
                this.down = false; this.up = true;
                this.fx = "up"; break;
            case KeyEvent.VK_D: // D键
                this.up = false; this.down = false;
                this.left = false; this.right = true;    
                this.fx = "right"; break;
            case KeyEvent.VK_S: // S键
                this.right = false; this.left = false;
                this.up = false; this.down = true;        
                this.fx = "down"; break;
            case KeyEvent.VK_F: // F键
                this.pkType = true; break;
        }
    }
    private void handleKeyRelease(int key) {
        // 玩家1按键释放
        if(playerId == 1) {
            switch(key) {
                case KeyEvent.VK_LEFT: this.left = false; break;
                case KeyEvent.VK_UP: this.up = false; break;
                case KeyEvent.VK_RIGHT: this.right = false; break;
                case KeyEvent.VK_DOWN: this.down = false; break;
                case KeyEvent.VK_SPACE: this.pkType = false; break;
            }
        } 
        // 玩家2按键释放
        else if(playerId == 2) {
            switch(key) {
                case KeyEvent.VK_A: this.left = false; break;
                case KeyEvent.VK_W: this.up = false; break;
                case KeyEvent.VK_D: this.right = false; break;
                case KeyEvent.VK_S: this.down = false; break;
                case KeyEvent.VK_F: this.pkType = false; break;
            }
        }
    }
	
	
	
	@Override
	public void move(long gametime) {
		if(this.left && this.getX()>0) {
			this.setX(this.getX()-3);
		}
		if(this.up && this.getY()>0) {
			this.setY(this.getY()-3);
		}
		if(this.right && this.getX()<900-this.getW()-15) {
			this.setX(this.getX()+3);
		}
		if(this.down && this.getY()<600-this.getH()-35) {
			this.setY(this.getY()+3);
		}
	}
	@Override
	protected void updateImage() {
		String imageKey = this.fx;
        if(playerId == 2) {
            imageKey += "2"; // 玩家2使用特殊图片
        }
        this.setIcon(GameLoad.imgMap.get(imageKey));
	}	
	/**
	 * 重写规则：1.重写的方法名称、返回值、传入参数序列必须和父类一样
	 * 			2.重写的方法访问修饰符只能比父类的更加宽泛
	 * 			3.重写的方法抛出的异常不能比父类的宽泛
	 * 
	 * 子弹的添加 需要发射者的坐标位置，发射者的方向，如果你可以变换子弹（思考怎么处理？）
	 */		
	private long filetime=0;
//	filetime 和传入的时间gameTime进行比较，赋值等操作运算，控制子弹间隔
	
	
	
	@Override	//添加子弹
	public void add(long gameTime) {//时间可以进行控制
		if(!this.pkType) {//如果是不发射状态直接return
			return;
		}
		this.pkType=false;//按一次发射一个子弹
//  	new PlayFile();//构造一个类需要做比较多的工作，可以选择一种方式，使用小工厂
//		将构造对象的多个步骤封装成为一个方法，返回值是对象实体
//		传递一个固定格式 {x:3,y:5,f:up} json格式
		ElementObj obj=GameLoad.getObj("file");
		PlayFile element =(PlayFile)obj.creatElement(this.toString());
		
		element.setPlayerId(this.playerId);//子弹设置所属玩家
		
//		装入到集合中
		ElementManager.getManager().addElement(element, GameElement.PLAYFILE);
//		如果要控制子弹速度等等，还需要代码编写
		
	}

	@Override
	public void die() {
		ElementObj obj=GameLoad.getObj("die");
		ElementObj element =obj.creatElement(""+this.getX()+","+this.getY()+",die");
		ElementManager.getManager().addElement(element, GameElement.DIE);
	}
	
	@Override
	public String toString() {//这里是偷懒直接使用toString,建议自己定义一个方法
		int x=this.getX();
		int y=this.getY();
		switch(this.fx) { //子弹再发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
		case "up":x+=this.getIcon().getIconWidth()*3/8-2;y-=10;break;
		//一般不会写20等数值，一般情况下图片大小就是显示大小；一般情况用图片大小参与运算
		case "left":y+=this.getIcon().getIconHeight()*2/5;x-=10;break;
		case "right":x+=this.getIcon().getIconWidth()+5;y+=this.getIcon().getIconHeight()*2/5;break;
		case "down":y+=this.getIcon().getIconHeight()+5;x+=this.getIcon().getIconWidth()*2/5;break;
		}
//		传递一个固定格式 {x:3,y:5,f:up} json格式
		return "x:"+x+",y:"+y+",f:"+this.fx;
	}

	
	@Override
	public void onCollision(ElementObj other) {
		if (other instanceof MapObj) {
	        // 碰撞后回退位置
	        this.setX(this.getX() - (left ? -2 : (right ? 2 : 0)));
	        this.setY(this.getY() - (up ? -2 : (down ? 2 : 0)));
	    }
		if(other instanceof Enemy) {
			this.setLive(false);
		}
		if(other instanceof PlayFile) {
			this.setLive(false);
		}
	}

}



























