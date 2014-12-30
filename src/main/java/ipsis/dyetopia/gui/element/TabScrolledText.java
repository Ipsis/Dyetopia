package ipsis.dyetopia.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.MathHelper;
import ipsis.dyetopia.reference.Textures;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * This is a straight copy of CoFHCore/src/main/java/cofh/core/gui/element/TabScrolledText.java
 */
public abstract class TabScrolledText extends TabBase {

    protected List<String> myText;
    protected int firstLine = 0;
    protected int maxFirstLine = 0;
    protected int numLines = 0;

    public TabScrolledText(GuiBase gui, int side, String infoString) {

        super(gui, side);

        maxHeight = 92;
        myText = getFontRenderer().listFormattedStringToWidth(infoString, maxWidth - 16);
        numLines = Math.min(myText.size(), (maxHeight - 24) / getFontRenderer().FONT_HEIGHT);
        maxFirstLine = myText.size() - numLines;

        if (side == LEFT)
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_LEFT, 256, 256);
        else
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_RIGHT, 256, 256);
    }

    public abstract String getIcon();

    public abstract String getTitle();

    @Override
    public void draw() {

        if (!isVisible()) {
            return;
        }
        drawBackground();
        drawTabIcon(getIcon());
        if (!isFullyOpened()) {
            return;
        }
        if (firstLine > 0) {
            gui.drawIcon("Icon_Up_Active", posXOffset() + maxWidth - 20, posY + 16, 1);
        } else {
            gui.drawIcon("Icon_Up_Inactive", posXOffset() + maxWidth - 20, posY + 16, 1);
        }
        if (firstLine < maxFirstLine) {
            gui.drawIcon("Icon_Dn_Active", posXOffset() + maxWidth - 20, posY + 76, 1);
        } else {
            gui.drawIcon("Icon_Dn_Inactive", posXOffset() + maxWidth - 20, posY + 76, 1);
        }
        getFontRenderer().drawStringWithShadow(getTitle(), posXOffset() + 18, posY + 6, headerColor);
        for (int i = firstLine; i < firstLine + numLines; i++) {
            getFontRenderer().drawString(myText.get(i), posXOffset() + 2, posY + 20 + (i - firstLine) * getFontRenderer().FONT_HEIGHT, textColor);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addTooltip(List<String> list) {

        if (!isFullyOpened()) {
            list.add(getTitle());
            return;
        }
    }

    @Override
    public boolean onMousePressed(int mouseX, int mouseY, int mouseButton) {

        if (!isFullyOpened()) {
            return false;
        }
        if (side == LEFT) {
            mouseX += currentWidth;
        }
        mouseX -= currentShiftX;
        mouseY -= currentShiftY;

        if (mouseX < 108) {
            return false;
        }
        if (mouseY < 52) {
            firstLine = MathHelper.clampI(firstLine - 1, 0, maxFirstLine);
        } else {
            firstLine = MathHelper.clampI(firstLine + 1, 0, maxFirstLine);
        }
        return true;
    }

    @Override
    public boolean onMouseWheel(int mouseX, int mouseY, int movement) {

        if (!isFullyOpened()) {
            return false;
        }
        if (movement > 0) {
            firstLine = MathHelper.clampI(firstLine - 1, 0, maxFirstLine);
            return true;
        } else if (movement < 0) {
            firstLine = MathHelper.clampI(firstLine + 1, 0, maxFirstLine);
            return true;
        }
        return false;
    }
}
