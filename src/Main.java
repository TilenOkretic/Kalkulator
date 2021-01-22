import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class Main extends Application {


    static int HEIGHT = 550;
    static int WIDTH = 300;

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("Kalkulator");

        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMaxHeight(HEIGHT);
        primaryStage.setMaxWidth(WIDTH);

        BorderPane layout = new BorderPane();

        TextField field = new TextField();
        field.setAlignment(Pos.BASELINE_RIGHT);

        // numpad
        GridPane nums = new GridPane();

        Button[] btns = new Button[12];


        setup_buttons(nums,btns,field);

        nums.setHgap(10);
        nums.setVgap(10);

        //  opeations

        VBox box = new VBox(10);
        Button sum = new Button("+");
        sum.setOnAction(e -> {
            if (isValid(field)) {
                field.setText(field.getText() + "+");
            }
        });
        sum.setPrefSize(50, 50);
        Button sub = new Button("-");
        sub.setOnAction(e -> {
            if (isValid(field)) {
                field.setText(field.getText() + "-");
            }
        });
        sub.setPrefSize(50, 50);
        Button mul = new Button("*");
        mul.setPrefSize(50, 50);
        mul.setOnAction(e -> {
            if (isValid(field)) {
                field.setText(field.getText() + "*");
            }
        });
        Button div = new Button("/");
        div.setPrefSize(50, 50);
        div.setOnAction(e -> {
            if (isValid(field)) {
                field.setText(field.getText() + "/");
            }
        });
        Button eql = new Button("=");
        eql.setPrefSize(50, 50);
        eql.setOnAction(e -> {
            field.setText(refactor(field.getText()));
            process(field);
        });

        box.getChildren().add(sum);
        box.getChildren().add(sub);
        box.getChildren().add(mul);
        box.getChildren().add(div);
        box.getChildren().add(eql);

        Button clear = new Button("Clear");
        clear.setOnAction(e -> {
            field.setText("");
        });


        layout.setPadding(new Insets(10, 10, 10, 10));

        layout.setTop(field);

        layout.setLeft(nums);
        layout.getLeft().setTranslateY(20);

        layout.setRight(box);
        layout.getRight().setTranslateY(20);
        layout.getRight().setTranslateX(-15);

        layout.setBottom(clear);
        layout.getBottom().setTranslateX((WIDTH / 2) - 45);

        Scene scene = new Scene(layout, WIDTH, HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void process(TextField field) {
        List<String> elm = new ArrayList<String>(Arrays.asList(field.getText().split(" ")));
        for (int i = 0; i < elm.size(); i++) {
            double out = 0;
            if (elm.get(i).equals("+")) {
                out = Double.parseDouble(elm.get(i - 1)) + Double.parseDouble(elm.get(i + 1));
                elm.set(i, Double.toString(out));
                elm.set(i - 1, "");
                elm.set(i + 1, "");
                i = 0;
            } else if (elm.get(i).equals("-")) {
                out = Double.parseDouble(elm.get(i - 1)) - Double.parseDouble(elm.get(i + 1));
                elm.set(i, Double.toString(out));
                elm.set(i - 1, "");
                elm.set(i + 1, "");
                i = 0;
            } else if (elm.get(i).equals("*")) {
                out = Double.parseDouble(elm.get(i - 1)) * Double.parseDouble(elm.get(i + 1));
                elm.set(i, Double.toString(out));
                elm.set(i - 1, "");
                elm.set(i + 1, "");
                i = 0;
            } else if (elm.get(i).equals("/")) {
                out = Double.parseDouble(elm.get(i - 1)) / Double.parseDouble(elm.get(i + 1));
                elm.set(i, Double.toString(out));
                elm.set(i - 1, "");
                elm.set(i + 1, "");
                i = 0;
            }

            elm.removeAll(Arrays.asList("", null));
        }

        field.setText(elm.get(0));
    }

    public String refactor(String s) {
        String out = s.replaceAll(Pattern.quote("+"), " + ");
        out = out.replaceAll(Pattern.quote("*"), " * ");
        out = out.replaceAll(Pattern.quote("/"), " / ");
        out = out.replaceAll(Pattern.quote("-"), " - ");

        return out;
    }

    public boolean isValid(TextField field) {
        int index = field.getText().length() - 1;

        return field.getText().length() != 0 && field.getText().charAt(index) != '+' &&
                field.getText().charAt(index) != '-' &&
                field.getText().charAt(index) != '/' &&
                field.getText().charAt(index) != '*';
    }

    public void setup_buttons(GridPane nums, Button[] btns, TextField field){
        int y = 0;
        int b = 12;
        for (int i = 3; i >= 1; i--) {
            if (!(b - 3 == -2) && !(b - 3 == 0)) {
                int ind = (i - 1) * (y + 1);
                if (b - 3 == -1) {
                    btns[ind] = new Button("0");
                } else {
                    btns[ind] = new Button(b - 3 + "");
                }
                btns[ind].setPrefSize(50, 50);
                btns[ind].setOnAction(e -> {
                    Button btn = (Button) e.getSource();
                    field.setText(field.getText() + "" + btn.getText());
                });
                nums.add(btns[ind], i, y);
            }

            b--;
            if (i == 1 && y < 3) {
                i = 4;
                y++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
