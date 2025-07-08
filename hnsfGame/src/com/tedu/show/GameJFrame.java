package com.tedu.show;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tedu.controller.GameThread;
import com.tedu.game.GameStart;

/**
 * @说明 游戏窗体 主要实现的功能：关闭，显示，最大最小化
 * @author renjj
 * @功能说明   需要嵌入面板,启动主线程等等
 * @窗体说明  swing awt  窗体大小（记录用户上次使用软件的窗体样式）
 * 
 * @分析 1.面板绑定到窗体
 *       2.监听绑定
 *       3.游戏主线程启动
 *       4.显示窗体
 */
public class GameJFrame extends JFrame{
	public static int GameX = 775;//GAMEX 
	public static int GameY = 600;
	private JPanel jPanel =null; //正在现实的面板
	private KeyListener  keyListener=null;//键盘监听
	private MouseMotionListener mouseMotionListener=null; //鼠标监听
	private MouseListener mouseListener=null;
	private Thread thead=null;  //游戏主线程
	private int playerCount = 1;
	
	private JButton prevLevelBtn = new JButton("上一关");
	private JButton nextLevelBtn = new JButton("下一关");
	private JButton pauseBtn = new JButton("暂停");
	private JButton homeBtn = new JButton("返回首页");
    
    
	public GameJFrame() {
		init();
	}
	public void init() {
		this.setSize(GameX, GameY+40); //设置窗体大小
		this.setTitle("坦克大战");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并且关闭
		this.setLocationRelativeTo(null);//屏幕居中显示
		// 控制面板
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
        controlPanel.setBackground(new Color(45, 45, 45));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // 按钮样式
        styleButton(prevLevelBtn, new Color(80, 120, 200));
        styleButton(nextLevelBtn, new Color(50, 150, 50));
        styleButton(pauseBtn, new Color(220, 120, 0));
        styleButton(homeBtn, new Color(180, 70, 70));        
        
        prevLevelBtn.addActionListener(e -> loadPrevLevel());
        nextLevelBtn.addActionListener(e -> loadNextLevel());
        pauseBtn.addActionListener(e -> togglePause());
        homeBtn.addActionListener(e -> returnToHome());
        
        controlPanel.add(prevLevelBtn);
        controlPanel.add(nextLevelBtn);
        controlPanel.add(pauseBtn);
        controlPanel.add(homeBtn);
        
        this.add(controlPanel, BorderLayout.NORTH);
	}
	private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("宋体", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }
	private void loadPrevLevel() {
        if (thead instanceof GameThread) {
            ((GameThread) thead).loadPrevLevel();
        }
    }
    
    private void loadNextLevel() {
        if (thead instanceof GameThread) {
            ((GameThread) thead).loadNextLevel();
        }
    }
    private void togglePause() {
        if (thead instanceof GameThread) {
            GameThread gameThread = (GameThread) thead;
            gameThread.togglePause();
            pauseBtn.setText(gameThread.isPaused() ? "继续" : "暂停");
        }
    }
    private void returnToHome() {
        int choice = JOptionPane.showConfirmDialog(
            this, 
            "确定要返回首页吗？当前游戏进度将丢失。", 
            "返回首页", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            // 停止游戏线程
            if (thead != null && thead.isAlive()) {
                ((GameThread) thead).stopGame();
            }
            
            // 关闭当前窗口
            this.dispose();
            
            // 重新打开首页
            GameStart.main(new String[]{});
        }
    }
	/*窗体布局: 可以讲 存档，读档。button   给大家扩展的*/
	public void addButton() {
//		this.setLayout(manager);//布局格式，可以添加控件
	}	
	/**
	 * 启动方法
	 */
	public void start() {
		if(jPanel!=null) {
			this.add(jPanel);
		}
		if(keyListener !=null) {
			this.addKeyListener(keyListener);
		}
		if(thead !=null) {
			thead.start();//启动线程
		}
		this.setVisible(true);//显示界面
		//如果jp是runnable的子类实体对象
		if(this.jPanel instanceof Runnable) {
			//强制类型判定。转换不会出错
			new Thread((Runnable)this.jPanel).start();
		}
	}

	public void setPlayerCount(int playerCount) {
	    this.playerCount = playerCount;
	}

	public int getPlayerCount() {
	    return playerCount;
	}
	
	
	/*set注入：等大家学习ssm 通过set方法注入配置文件中读取的数据;讲配置文件
	 * 中的数据赋值为类的属性
	 * 构造注入：需要配合构造方法
	 * spring 中ioc 进行对象的自动生成，管理。
	 * */
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	public void setThead(Thread thead) {
		this.thead = thead;
	}
	
	
	
	
}





