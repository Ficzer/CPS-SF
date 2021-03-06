package SF.GUI;

import SF.Converters.AnalogToDigitalConverter;
import SF.EmptyFileNameException;
import SF.Signal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;


public class ACConverterWindow
{
    private Signal originalSignal = new Signal();
    private Signal sampledSignal = null;
    private Signal newSignal = null;

    public void display()
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Convert to Digital Signal");

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./signals"));
        Button openFileButton = new Button("Open file");
        Button drawButton = new Button("Draw");
        Button showDataButton = new Button("Show Errors Data");
        Button samplingButton = new Button("Sampling");
        Button quantizeButton = new Button("Quantize");
        Button saveButton = new Button("Save new signal");

        TextField samplingField = new TextField("50");
        TextField bitsField = new TextField("4");
        Label fileLabel = new Label("File");
        Label samplingLabel = new Label(" New sampling");
        Label bitsLabel = new Label(" Bits");


        openFileButton.setMinWidth(100);
        drawButton.setMinWidth(100);
        showDataButton.setMinWidth(100);

        AdvancedButtonHandler advancedButtonHandler = new AdvancedButtonHandler();
        ButtonHandler buttonHandler = new ButtonHandler();
        AnalogToDigitalConverter analogToDigitalConverter = new AnalogToDigitalConverter();

        openFileButton.setOnAction(e ->
        {
            File file = fileChooser.showOpenDialog(window);
            newSignal = null;

            if(file != null)
            {
                FileInputStream fileInputStream = null;
                try
                {
                    fileInputStream = new FileInputStream(file);
                    ObjectInputStream in = new ObjectInputStream(fileInputStream);
                    originalSignal.copy((Signal)in.readObject());
                }
                catch (FileNotFoundException e1)
                {
                    e1.printStackTrace();
                }
                catch (StreamCorruptedException e1)
                {
                    e1.printStackTrace();
                    AlertBox.display("Wrong file", "Wrong file Format");
                }
                catch (ClassNotFoundException e1)
                {
                    e1.printStackTrace();
                    AlertBox.display("Wrong file", "Wrong file Format");
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }


                fileLabel.setText(originalSignal.getName());
            }
        });

        drawButton.setOnAction(e ->
        {
            try
            {
                if(newSignal != null)
                {
                    buttonHandler.draw(originalSignal, newSignal);
                }
                else
                {
                    buttonHandler.draw(originalSignal, 10, false, true);
                }

            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of intervals.");
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }
        });

        showDataButton.setOnAction(e ->
        {
            try
            {
                AlertBox.display("Errors data", buttonHandler.generateErrorsData(newSignal, sampledSignal));
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load or generate new signal.");
            }
            catch (IllegalArgumentException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong sampling", "Original signal sampling and new sampling don't match");
            }
        });

        samplingButton.setOnAction(e ->
        {
            try
            {
                newSignal = analogToDigitalConverter.sample(originalSignal, Integer.parseInt(samplingField.getText()));
                sampledSignal = newSignal;
                AlertBox.display("Success", "The signal has been sampled");
            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of intervals.");
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }
            catch (IllegalArgumentException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Sampling error", e1.getMessage());
            }
        });

        quantizeButton.setOnAction(e ->
        {
            try
            {
                if(newSignal == null)
                {
                    newSignal = analogToDigitalConverter.quantize(originalSignal, Integer.parseInt(bitsField.getText()));
                }
                else
                {
                    newSignal = analogToDigitalConverter.quantize(newSignal, Integer.parseInt(bitsField.getText()));

                }
                AlertBox.display("Success", "The signal has been quantized");
            }
            catch (NumberFormatException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Wrong format", "Wrong format of intervals.");
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Choose file", "Please choose file to load.");
            }
        });

        saveButton.setOnAction(e ->
        {
            try
            {
                buttonHandler.save(newSignal);
                AlertBox.display("Success", "The file has been saved");
            }
            catch (EmptyFileNameException e1)
            {
                e1.printStackTrace();
            }
            catch (NullPointerException e1)
            {
                e1.printStackTrace();
                AlertBox.display("Generate file", "Please generate file to save.");
            }

        });




        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(12);

        GridPane.setConstraints(openFileButton, 0,0);
        GridPane.setConstraints(drawButton, 0, 1);
        GridPane.setConstraints(showDataButton, 0 , 2);
        GridPane.setConstraints(samplingButton, 0, 3);
        GridPane.setConstraints(quantizeButton, 0, 4);
        GridPane.setConstraints(saveButton, 0, 5);
        GridPane.setConstraints(fileLabel, 1, 0);
        GridPane.setConstraints(samplingLabel, 1, 7);
        GridPane.setConstraints(samplingField, 0, 7);
        GridPane.setConstraints(bitsLabel, 1, 8);
        GridPane.setConstraints(bitsField, 0, 8);


        grid.getChildren().addAll(openFileButton, drawButton, showDataButton,
                fileLabel, samplingButton, quantizeButton, samplingField,
                samplingLabel, bitsField, bitsLabel, saveButton);

        window.setScene(new Scene(grid, 300, 400));
        window.show();
    }
}
