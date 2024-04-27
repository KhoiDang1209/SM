package image;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackGroundScene {
    Image background;
    ImageView backgroundView;

    public BackGroundScene() {
        // Sử dụng đường dẫn tương đối từ thư mục resources
        background = new Image(getClass().getResource("/org/example/sm/background.jpg").toExternalForm());
        backgroundView = new ImageView(background);
    }

    public double getBackgroundWidth() {
        return background.getWidth();
    }

    public double getBackgroundHeight() {
        return background.getHeight();
    }

    public ImageView getBackgroundView() {
        return backgroundView;
    }
}
