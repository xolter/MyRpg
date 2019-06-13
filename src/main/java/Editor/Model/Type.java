package Editor.Model;

public class Type {
    private String name;
    private int width;
    private int height;
    private boolean isBackground;
    private boolean isNPC;

    Type(String name, int width, int height, boolean isBackground, boolean isNPC) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.isBackground = isBackground;
        this.isNPC = isNPC;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isBackground() {
        return isBackground;
    }

    public boolean isNPC() {
        return isNPC;
    }
}
