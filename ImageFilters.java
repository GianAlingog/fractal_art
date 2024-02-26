public class ImageFilters {
    // This method takes in three PixImages (form, image)
    // Where form is the recursive art layered on on top of image
    public static PixImage transform(PixImage form, PixImage image) {
        PixImage copy = new PixImage(form);
        for (int i = 0; i < copy.height; i++) {
            for (int j = 0; j < copy.width; j++) {
                // If the pixel is white (or blank), take the pixels from the image
                if (copy.red[i][j] == 255 && copy.green[i][j] == 255 && copy.blue[i][j] == 255) {
                    copy.red[i][j] = image.red[i][j];
                    copy.green[i][j] = image.green[i][j];
                    copy.blue[i][j] = image.blue[i][j];
                } else { // Else, take the pixels from the recursive art (form)
                    copy.red[i][j] = form.red[i][j];
                    copy.green[i][j] = form.green[i][j];
                    copy.blue[i][j] = form.blue[i][j];
                }
            }
        }
        return copy;
    }
}
