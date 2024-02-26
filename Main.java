public class Main {
    public static void main(String[] args) {
        // Create the Fractal Art
        new ArtWindow();

        // Image manipulation (1280x720 resolution)
        PixImage form = new PixImage("art.png"); // Do not change the filename
        PixImage image1 = new PixImage("image.png"); // Adjust accordingly to input image
        form = ImageFilters.transform(form, image1);
        form.saveImage("art.png"); // Do not change the filename
        form.showImage();
    }
}
