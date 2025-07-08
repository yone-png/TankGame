package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Tool extends ElementObj {
    private int type; // 1: 无敌，2: 加速，3: 射速加快
    private long activeTime; // 持续时间（毫秒）
    private boolean isActive; // 是否激活
    private long startTime; // 激活开始时间
    private ElementObj player; // 关联的玩家

    public Tool(int type) {
        this.type = type;
        this.activeTime = 5000; // 默认持续时间5秒
        this.isActive = false;
    }
    
    

    @Override
    public void showElement(Graphics g) {
        if (!isActive) {
            // 使用加载的图片绘制道具
            ImageIcon icon = GameLoad.imgMap.get(getImageKeyByType(type));
            if (icon != null) {
                g.drawImage(icon.getImage(), getX(), getY(), getW(), getH(), null);
            } else {
                // 图片未找到时的默认显示
                g.setColor(Color.YELLOW);
                g.fillOval(getX(), getY(), getW(), getH());
            }
        }
    }

    private String getImageKeyByType(int type) {
        switch (type) {
            case 1: return "tool_invincible";
            case 2: return "tool_speed";
            case 3: return "tool_fire_rate";
            default: return "tool_invincible"; // 默认图片
        }
    }

   

    private void applyEffect() {
        if (player instanceof Play) {
            Play play = (Play) player;
            switch (type) {
                case 1: // 无敌
                    play.setInvincible(true);
                    play.setInvincibleStartTime(System.currentTimeMillis());
                    break;
                case 2: // 加速
                    play.setSpeedBoost(true);
                    play.setSpeedBoostStartTime(System.currentTimeMillis());
                    break;
                case 3: // 射速加快
                    play.setRapidFire(true);
                    play.setRapidFireStartTime(System.currentTimeMillis());
                    break;
            }
        }
    }

    public void update() {
        if (isActive) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > activeTime) {
                deactivate();
            }
        }
    }

    private void deactivate() {
        isActive = false;
        if (player instanceof Play) {
            Play play = (Play) player;
            switch (type) {
                case 1:
                    play.setInvincible(false);
                    System.out.println("移除无敌效果");
                    break;
                case 2:
                    play.setSpeedBoost(false);
                    System.out.println("移除加速效果");
                    break;
                case 3:
                    play.setRapidFire(false);
                    System.out.println("移除射速加快效果");
                    break;
            }
        }
        this.setLive(false);
    }

    @Override
    public void onCollision(ElementObj other) {
        if (other instanceof Play && !isActive) {
            activate(other);
            System.out.println("拾取道具: " + type); // 测试字符串
        }
    }

 // 在 Tool.java 中修改以下方法
    public void activate(ElementObj player) {
        if (player instanceof Play) {
            this.player = player;
            this.isActive = true;
            this.startTime = System.currentTimeMillis();
            applyEffect();
            System.out.println("激活道具: " + type);
        }
    }
    
    @Override
    public ElementObj creatElement(String str) {
        String[] parts = str.split(",");
        this.setX(Integer.parseInt(parts[0]));
        this.setY(Integer.parseInt(parts[1]));
        String toolTypeStr = parts[2];
        int toolType = Integer.parseInt(toolTypeStr.replaceAll("tool", ""));
        this.type = toolType;
        // 加载对应的道具图片
        String imageKey = "tool_invincible";
        switch (toolType) {
            case 1:
                imageKey = "tool_invincible";
                break;
            case 2:
                imageKey = "tool_speed";
                break;
            case 3:
                imageKey = "tool_fire_rate";
                break;
        }
        ImageIcon icon = GameLoad.imgMap.get(imageKey);
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        return this;
        
        
        
    }
    
    
    
    
    
}