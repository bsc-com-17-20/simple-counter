import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CounterImproved extends Application {
    private boolean isCounting = false;
    private boolean isRunning = true;
    private int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox(10);
        Text text = new Text("0");
        text.setStyle("-fx-font-size: 24;");
        Button button = new Button("Start");

        button.setOnAction(e -> {
            isCounting = !isCounting;
            System.out.println(isCounting);
            String textString = isCounting ? "Stop" : "Start";
            button.setText(textString);
        });

        // Counting thread
        new Thread(() -> {
            while (isRunning) {
                try {
                    while (isCounting) {
                        count++;
                        text.setText("" + count);
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // UI size style update thread (optional)
        // new Thread(() -> {
        // while (isRunning) {
        // if (primaryStage.isMaximized()) {
        // button.setPrefSize(100, 50);
        // button.setStyle("-fx-font-size: 24");
        // text.setStyle("-fx-font-size: 100;");
        // }
        // }
        // }).start();

        vBox.getChildren().addAll(text, button);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 300, 100);
        primaryStage.setTitle("Counter Improved");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            isRunning = false;
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
