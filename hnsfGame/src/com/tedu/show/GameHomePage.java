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
        setLayout(new BorderLayout());
        
        // 分层面板解决背景覆盖问题
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(GameJFrame.GameX, GameJFrame.GameY));
        
        // 背景图
        ImageIcon background = new ImageIcon("image/icon.png");
        JLabel bgLabel = new JLabel(background);
        bgLabel.setBounds(0, 0, GameJFrame.GameX, GameJFrame.GameY);
        layeredPane.add(bgLabel, JLayeredPane.DEFAULT_LAYER);
        
        // 按钮面板（居中）
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.setBounds(0, 400, GameJFrame.GameX, 100);
        
        JButton singlePlayerBtn = createButton("单人模式");
        JButton dualPlayerBtn = createButton("双人模式");
        
        singlePlayerBtn.addActionListener(e -> {
            playerCount = 1;
            startGame();
        });
        
        dualPlayerBtn.addActionListener(e -> {
            playerCount = 2;
            startGame();
        });
        
        buttonPanel.add(singlePlayerBtn);
        buttonPanel.add(dualPlayerBtn);
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
        
        add(layeredPane, BorderLayout.CENTER);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("宋体", Font.BOLD, 20));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
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