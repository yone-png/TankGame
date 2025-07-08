package com.tedu.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;
import com.tedu.element.Play;
/**
 * @说明 加载器（工具：用户读取配置文件的工具），工具类大多提供static方法
 */
public class GameLoad {
	//资源管理器
	private static ElementManager em=ElementManager.getManager();
	
	
	//图片集合 使用map来进行存储 枚举类型配合移动（扩展）
	public static Map<String, ImageIcon> imgMap=new HashMap<>();
	
	
	//Collections用于集合排序的工具类，可以为所有的对象类型的记录进行排序
	//排序只能为Collection的子类
	
	
	//用户读取文件的类
	private static Properties pro=new Properties();
	
	/**
	 * @说明 传入地图id有加载方法依据文件规则自动产生地图文件名称，加载文件
	 * @param mapId	文件编号
	 */
	public static void MapLoad(int mapId) {
		//得到文件路径
		String mapName="com/tedu/text/"+mapId+".map";
		//使用io流来获取文件对象
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream maps=classLoader.getResourceAsStream(mapName);
		if(maps==null) {
			System.out.println("配置文件读取异常");
			return;
		}
		try {
			//以后用的都是xml和json
			pro.clear();
			pro.load(maps);
			//可以直接动态的获取所有的key，有key就可以获取value
			Enumeration<?> names=pro.propertyNames();
			while(names.hasMoreElements()) {//获取是无序的
				String key=names.nextElement().toString();
//				System.out.println(pro.getProperty(key));
				//自动创建和加载我们的地图
				String [] arrs=pro.getProperty(key).split(";");
				for (int i = 0; i < arrs.length; i++) {
					ElementObj element=new MapObj().creatElement(key+","+arrs[i]);
					em.addElement(element, GameElement.MAPS);
				}
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	/**
	 * @说明 加载图片代码
	 * 加载图片 代码和图片之间差一个路径问题
	 */
	public static void loadImg() {//可以带参数，不同的关也可能需要不一样的图片资源
		String texturl="com/tedu/text/GameData.pro";//文件的命名可以更加有规律
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream texts=classLoader.getResourceAsStream(texturl);
		//imgMap用于存放数据
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set=pro.keySet();
			for(Object o:set) {
				String url=pro.getProperty(o.toString());
				imgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	/**
	 * 加载玩家
	 */
	// 修改 loadPlay 方法
	public static void loadPlay(int playerCount) {
	    loadObj();
	    
	    // 玩家1始终加载
	    String play1Str = "500,500,up";
	    ElementObj obj1 = getObj("play");
	    Play player1 = (Play) obj1.creatElement(play1Str);
	    player1.setPlayerId(1);
	    em.addElement(player1, GameElement.PLAY);
	    
	    // 根据玩家数量决定是否加载玩家2
	    if (playerCount > 1) {
	        String play2Str = "100,500,up";
	        ElementObj obj2 = getObj("play2");
	        Play player2 = (Play) obj2.creatElement(play2Str);
	        player2.setPlayerId(2);
	        em.addElement(player2, GameElement.PLAY);
	    }
	}
	//加载敌人npc
	public static void loadEnemy() {
		loadObj();
		Random ra = new Random();
		for(int i=0;i<5;i++) {
			ElementObj obj=getObj("enemy");
			int x = ra.nextInt(600);
			int y = ra.nextInt(200);
			ElementObj enemy=obj.creatElement(""+x+","+y+",edown");
			em.addElement(enemy, GameElement.ENEMY);
		}
		
	}
	
	public static void loadBoss() {
		loadObj();
		Random ra = new Random();
		ElementObj obj=getObj("boss");
		int x = ra.nextInt(600);
		int y = ra.nextInt(200);
		ElementObj boss=obj.creatElement(""+x+","+y+",boss");
		em.addElement(boss, GameElement.BOSS);
	}
		
	
	public static ElementObj getObj(String str) {
		try {
			Class<?> class1=objMap.get(str);
			Object newInstance = class1.newInstance();
			Object newInstance1 = class1.getDeclaredMethods();
			if(newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 扩展：使用配置文件来实例化对象，通过固定的key（字符串来实力胡）
	 * @param arg
	 */
	private static Map<String,Class<?>> objMap=new HashMap<>();
	public static void loadObj() {
		String texturl="com/tedu/text/obj.pro";
		ClassLoader classLoader=GameLoad.class.getClassLoader();
		InputStream texts=classLoader.getResourceAsStream(texturl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set =pro.keySet();
			for(Object o:set) {
				String classUrl=pro.getProperty(o.toString());
				Class<?> forName=Class.forName(classUrl);
				objMap.put(o.toString(), forName);
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	
	//用于测试
	public static void main(String[] arg) {
		MapLoad(1);
	}
	
}
