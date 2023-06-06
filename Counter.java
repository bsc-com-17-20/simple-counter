import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Practice project
class Task extends Thread {
    private boolean isCounting = false;
    private boolean isActive = true;
    private int count = 0;
    private Text countLabel;

    public Task(Text countLabel) {
        this.countLabel = countLabel;
    }

    public void setCounting(boolean isCounting) {
        this.isCounting = isCounting;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        System.out.println("closed");
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        while (isActive) {
            try {
                while (isCounting) {
                    count++;
                    String text = count + "";
                    Platform.runLater(() -> {
                        countLabel.setText(text);
                    });
                    // 1 second
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class Counter extends Application {
    boolean isActive = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        Text countLabel = new Text("0");
        countLabel.setStyle("-fx-font-size: 24;");
        Button button = new Button("Start");
        button.setPrefSize(70, 25);

        Task task = new Task(countLabel);
        task.start();

        button.setOnAction(e -> {
            isActive = !isActive;
            task.setCounting(isActive);
            if (isActive) {
                button.setText("Stop");
            } else {
                button.setText("Start");
            }
        });

        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(countLabel, button);

        Scene scene = new Scene(vBox, 250, 100);
        primaryStage.setOnCloseRequest(e -> {
            task.setActive(false);
        });
        primaryStage.setTitle("Counter");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}