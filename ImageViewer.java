import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ImageViewer extends Application {

    private static int m_Width = 600;
    private static int m_Height = 400;
    
    private String HOME_IMAGE = "https://cdn.tgdd.vn//GameApp/1385458//webp-la-gi-4-cach-chuyen-doi-anh-webp-sang-png-jpg-15-800x450.jpg";
    private ImageView m_Imageview = new ImageView(generateImage());
    private ImageView m_HistogramView = new ImageView(m_Imageview.getImage());
    private ImageView m_ResizeView = new ImageView(m_Imageview.getImage());
    private Canvas m_OverlayCanvas = null;
    private Stage m_HistogramWindow = null;
    private Stage m_ResizeWindow = null;
    private Image currentImage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        createGUI(stage);
    }

    private BufferedImage getViewImage() {
        if (m_Imageview != null) {
            return SwingFXUtils.fromFXImage(m_Imageview.getImage(), null);
        }
        return null;
    }

    private void setViewImage(BufferedImage bimage) {
        if(bimage == null) {
            return;
        }
        WritableImage wimage = SwingFXUtils.toFXImage(bimage, null);
        m_Imageview.setImage(wimage);
    }

    private void showWebpImage(BufferedImage bimage) {

        if (m_HistogramWindow == null) {
            m_HistogramWindow = new Stage();
            m_HistogramWindow.setTitle("Histogram");
            m_HistogramWindow.initModality(Modality.NONE);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(m_HistogramView);
            Scene dialogScene = new Scene(dialogVbox, 512, 512);
            m_HistogramWindow.setScene(dialogScene);
        }
        m_HistogramWindow.show();

        if (bimage == null) {
            return;
        }
        WritableImage wimage = SwingFXUtils.toFXImage(bimage, null);
        m_HistogramView.setImage(wimage);
    }

    void createGUI(Stage stage) throws FileNotFoundException {
        stage.setTitle("Image Viewer");
        VBox vbox = new VBox();

        Pane imageViewPane = new Pane();

        imageViewPane.getChildren().add(m_Imageview);

        m_OverlayCanvas = new Canvas(m_Width, m_Height);
        imageViewPane.getChildren().add(m_OverlayCanvas);
        
        Point anchor = new Point();    
 
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> Platform.exit());

        Button openButton = new Button("Open image");
        openButton.setOnAction(e -> {
			try {
				this.openImage(stage);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

        Button toWebpButton = new Button("Convert to Webp");
        toWebpButton.setOnAction(e -> {
			try {
				this.toWebp(currentImage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        Button resizeButton = new Button("Resize");
        resizeButton.setOnAction(e -> {
			try {
				this.toWebp(currentImage);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
        HBox buttons = new HBox(closeButton, openButton, toWebpButton, resizeButton);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(5));
        vbox.getChildren().addAll(imageViewPane, buttons);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void resize(Image image) {
        if (m_ResizeWindow == null) {
        	m_ResizeWindow = new Stage();
        	m_ResizeWindow.setTitle("Resize");
        	m_ResizeWindow.initModality(Modality.NONE);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(m_ResizeView);
            Scene dialogScene = new Scene(dialogVbox, 512, 512);
            m_HistogramWindow.setScene(dialogScene);
        }
        m_HistogramWindow.show();

        WritableImage wimage = SwingFXUtils.toFXImage(null, null);
        m_HistogramView.setImage(wimage);
    }
    private void toWebp(Image image) throws IOException {
    	BufferedImage bimage = getViewImage();
        java.awt.Image resultingImage = bimage.getScaledInstance(256, 256, java.awt.Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        
        showWebpImage(outputImage);
        m_HistogramView.setFitHeight(512);
        m_HistogramView.setFitWidth(512);

    }

    /**
     * Open image from filesystem using OS file chooser and update the image display.
     * 
     * @param stage Owner
     */
    private void openImage(Stage stage) throws FileNotFoundException {
        // TODO: Implement this (Assignment 1.)
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open System File");
        
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
        	Image image = new Image(file.toURI().toString());      	        
        	m_Imageview.setImage(image);
        	currentImage = image;

        	createGUI(stage);
        }
        
    }

    Image generateImage() {
        return new Image(HOME_IMAGE);
    }

}
