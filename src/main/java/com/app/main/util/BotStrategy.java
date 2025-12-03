package com.app.main.util;

import com.app.main.controller.BotController;

/**
 * This interface is for define strategies for the bot
 * of the game
 */
public interface BotStrategy {
    
    public void applyStrategy(BotController botController);
}
