package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.Tool;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 游戏的主要面板
 * @author renjj
 * @功能说明 主要进行元素的显示，同时进行界面的刷新(多线程)
 * 
 * @题外话 java开发实现思考的应该是：做继承或者是接口实现
 * @多线程刷新 1.本类实现线程接口
 * 			  2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
//	联动管理器
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}

	public void init() {
		em = ElementManager.getManager();//得到元素管理器对象
	}
	
	@Override
	public void paint(Graphics g) {
	    super.paint(g);
	    // 绘制所有游戏元素
	    Map<GameElement, List<ElementObj>> all = em.getGameElements();
	    for (GameElement ge : GameElement.values()) {
	        List<ElementObj> list = all.get(ge);
	        for (int i = 0; i < list.size(); i++) {
	            ElementObj obj = list.get(i);
	            obj.showElement(g);
	        }
	    }

	   

	    // 绘制玩家状态
	    List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
	    for (ElementObj playerObj : players) {
	        if (playerObj instanceof Play) {
	            Play player = (Play) playerObj;
	            StringBuilder textBuilder = new StringBuilder();

	            if (player.isInvincible()) {
	                long remainingTime = player.getInvincibleRemainingTime() / 1000;
	                textBuilder.append("无敌剩余时间: ").append(remainingTime).append("秒 ");
	            }

	            if (player.isSpeedBoosted()) {
	                long remainingTime = player.getSpeedBoostRemainingTime() / 1000;
	                textBuilder.append("加速剩余时间: ").append(remainingTime).append("秒 ");
	            }

	            if (player.isRapidFire()) {
	                long remainingTime = player.getRapidFireRemainingTime() / 1000;
	                textBuilder.append("射速加快剩余时间: ").append(remainingTime).append("秒 ");
	            }

	            if (textBuilder.length() > 0) {
	                String text = textBuilder.toString();
	                g.setColor(Color.WHITE);
	                g.setFont(new Font("Arial", Font.BOLD, 14));
	                g.drawString(text, 10, 20);
	            }
	        }
	    }

	    // 绘制道具效果倒计时
	    List<ElementObj> players1 = em.getElementsByKey(GameElement.PLAY);
	    for (ElementObj playerObj : players1) {
	        if (playerObj instanceof Play) {
	            Play player = (Play) playerObj;
	            if (player.isInvincible() || player.isSpeedBoosted() || player.isRapidFire()) {
	                long remainingTime = 0;
	                if (player.isInvincible()) {
	                    remainingTime = player.getInvincibleRemainingTime() / 1000;
	                } else if (player.isSpeedBoosted()) {
	                    remainingTime = player.getSpeedBoostRemainingTime() / 1000;
	                } else if (player.isRapidFire()) {
	                    remainingTime = player.getRapidFireRemainingTime() / 1000;
	                }
	                
	                g.setColor(Color.RED);
	                g.setFont(new Font("微软雅黑", Font.BOLD, 16));
	                g.drawString("道具效果剩余时间: " + remainingTime + "秒", 10, 20);
	            }
	        }
	    }
	}
	@Override
	public void run() { //接口实现
		while(true) {
			this.repaint();
//		一般情况下，多线程都会使用一个休眠控制速度
			try {
				Thread.sleep(10);
			}catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	
}











