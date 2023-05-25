package ObjectClass;

import java.io.Serializable;

public class Image implements Serializable {
    private byte[] imageResource;

    public Image(byte[] imageResource) {
        this.imageResource = imageResource;
    }

    public byte[] getImageResource() {
        return imageResource;
    }

    public void setImageResource(byte[] imageResource) {
        this.imageResource = imageResource;
    }
}
