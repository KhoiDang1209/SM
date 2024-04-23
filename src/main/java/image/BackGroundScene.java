package image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackGroundScene {
    Image background = new Image("file:///D:/OOP%20Project/SM/src/main/java/image/background.jpg");
    public double getBackgroundWidth()
    {
        return background.getWidth();
    }
    public double getBackgroundHeight()
    {
        return background.getHeight();
    }
    ImageView backgroundView;
    public BackGroundScene()
    {
        this.backgroundView=new ImageView(background);
    }
    public ImageView getBackgroundView() {
        return backgroundView;
    }
}
