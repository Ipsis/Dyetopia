package ipsis.dyetopia.gui;

import ipsis.dyetopia.network.message.MessageGuiWidget;

/**
 * Implement on containers that can handle gui messages
 */
public interface IGuiMessageHandler {

    void handleGuiWidget(MessageGuiWidget message);
}
