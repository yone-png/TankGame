package com.tedu.game;

import com.tedu.show.GameHomePage;
import com.tedu.show.GameJFrame;

import javax.swing.*;

public class GameStart {
    public static void main(String[] args) {
        // 创建首页框架
        JFrame homeFrame = new JFrame("坦克大战");
        homeFrame.setSize(GameJFrame.GameX, GameJFrame.GameY);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setLocationRelativeTo(null);
        
        // 添加首页面板
        GameHomePage homePage = new GameHomePage(homeFrame);
        homeFrame.add(homePage);
        
        // 显示首页
        homeFrame.setVisible(true);
    }
}
