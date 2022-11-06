If you have JDK 11 and above. JavaFX runtime is not supported. You need to download it from here https://gluonhq.com/products/javafx/

Run on command line

javac --module-path %YOUR_PATH_TO_JAVAFX_LIB_FOLDER% --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web ImageViewer.java

Then run application

java --module-path  %YOUR_PATH_TO_JAVAFX_LIB_FOLDER%  --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics,javafx.media,javafx.swing,javafx.web ImageViewer.java