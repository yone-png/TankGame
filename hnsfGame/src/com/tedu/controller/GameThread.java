package com.tedu.controller;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
 * 		游戏判定；游戏地图切换 资源释放和重新读取
 * @继承 使用继承的方法实现多线程（一般建议使用接口实现）
 */

public class GameThread  extends Thread{
	private ElementManager em;
	public GameThread() {
		em=ElementManager.getManager();
	}
	@Override
	public void run() {//游戏的run方法 主线程
		while(true) {
		//游戏开始前 读进度条，加载游戏资源（场景资源）
			gameLoad();
		//游戏进行时 游戏过程中
			gameRun();
		//游戏场景结束 游戏资源回收（场景资源）
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 游戏的加载
	 */
	private void gameLoad() {
		GameLoad.loadImg();
		GameLoad.MapLoad(3); //可以变为变量，每一关重新加载 加载地图
		//加载主角
		GameLoad.loadPlay();//也可以带参数，单机还是2人
		//加载敌人NPC等
		GameLoad.loadEnemy();
		//全部加载完，游戏启动
	}
	/**
	 * @说明 游戏进行时
	 * @任务说明 游戏过程中要做的事情：1.自动化玩家的移动，碰撞，死亡
	 * 							  2.新元素的增加（NPC死亡后出现的道具）
	 * 							  3.暂停等等。。。
	 * 先实现主角的移动
	 */
	private void gameRun() {
		long gameTime=0L;//给int类型就可以了
		while(true) {//预留扩展true可以变为变量，用于控制关卡结束等
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> enemys=em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> files=em.getElementsByKey(GameElement.PLAYFILE);
			List<ElementObj> maps=em.getElementsByKey(GameElement.MAPS);
			List<ElementObj> plays=em.getElementsByKey(GameElement.PLAY);
			moveAndUpdate(all, gameTime); //游戏元素自动化方法
			
			// 玩家坦克 vs 墙体
	        for (ElementObj play : plays) {
	            for (ElementObj map : maps) {
	                if (play.pk(map)) {
	                    play.onCollision(map);
	                    map.onCollision(play);
	                }
	            }
	        }
	        
	        // 敌人 vs 墙体
	        for (ElementObj enemy : enemys) {
	            for (ElementObj map : maps) {
	                if (enemy.pk(map)) {
	                    enemy.onCollision(map);
	                    map.onCollision(enemy);
	                }
	            }
	        }
			
			ElementPK(plays, enemys);
			ElementPK(enemys,files);
			ElementPK(files,maps);
			
			gameTime++;//唯一的时间控制
			try {
				sleep(10);//默认理解为一秒刷新100次
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	private void ElementPK(List<ElementObj> listA,List<ElementObj> listB) {
		//在这里使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态
		for(int i=listA.size()-1;i>=0;i--) {
			ElementObj a=listA.get(i);
			for(int j=listB.size()-1;j>=0;j--) {
				ElementObj b=listB.get(j);
				if(a.pk(b)) {
					//如果是boos，那么也一枪一个吗？？？？
					//将setLive（false）变为一个受攻击方法，还可以传入另外一个对象的攻击力
					//当受攻击方法里执行时，如果血量减为0，再设置生存为false
					//扩展	
					a.onCollision(b);
					b.onCollision(a);
					
//					a.setLive(false);
//					b.setLive(false);
					break;
				}
			}
		}
	}
	
	
	//游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=list.size()-1;i>=0;i--) {
				ElementObj obj=list.get(i);//读取为基类
				if(!obj.isLive()) {
					//启动一个死亡方法（方法中可以做事情例如：死亡动画，掉装备等）
					obj.die();//需要大家自己补充掉道具等效果
					list.remove(i);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}
	
	
	
	/**
	 * 游戏切换关卡
	 */
	private void gameOver() {
		
	}
	
}






















