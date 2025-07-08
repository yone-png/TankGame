package com.tedu.show;

import javax.swing.*;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;

import java.awt.*;

public class GameHomePage extends JPanel {
    private JFrame frame;
    private int playerCount = 1; // 默认单人模式
    
    public GameHomePage(JFrame frame) {
        this.frame = frame;
        setLayout(null); // 使用绝对布局
        
        // 添加背景
        ImageIcon background = new ImageIcon("image/icon.png");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, GameJFrame.GameX, GameJFrame.GameY);
        add(bgLabel);
        
        // 创建按钮 - 确保按钮在背景上方
        JButton singlePlayerBtn = createButton("单人模式", 250, 400);
        JButton dualPlayerBtn = createButton("双人模式", 400, 400);
        
        // 添加按钮到面板（在背景之后添加，确保按钮在上层）
        add(dualPlayerBtn);
        add(singlePlayerBtn);
        
        
        // 添加按钮监听
        singlePlayerBtn.addActionListener(e -> {
            playerCount = 1;
            startGame();
        });
        
        dualPlayerBtn.addActionListener(e -> {
            playerCount = 2;
            startGame();
        });
    }
    
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 50);
        button.setFont(new Font("宋体", Font.BOLD, 20));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true); // 确保按钮不透明
        return button;
    }
    
    private void startGame() {
        frame.dispose(); // 关闭首页
        
        // 创建游戏主窗体
        GameJFrame gameFrame = new GameJFrame();
        gameFrame.setPlayerCount(playerCount); // 传递玩家数量
        
        // 设置游戏面板
        GameMainJPanel gamePanel = new GameMainJPanel();
        gameFrame.setjPanel(gamePanel);
        
        // 设置监听器
        GameListener listener = new GameListener();
        gameFrame.setKeyListener(listener);
        
        // 设置游戏线程 - 传递玩家数量
        GameThread gameThread = new GameThread(playerCount);
        gameFrame.setThead(gameThread);
        
        // 启动游戏
        gameFrame.start();
    }
}