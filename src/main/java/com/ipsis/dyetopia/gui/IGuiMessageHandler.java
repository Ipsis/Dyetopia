package com.ipsis.dyetopia.gui;

import com.ipsis.dyetopia.network.message.MessageGuiWidget;

/**
 * Implement on containers that can handle gui messages
 */
public interface IGuiMessageHandler {

    public void handleGuiWidget(MessageGuiWidget message);
}
