package view.cells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Report;
import ui.UIView;

import java.util.ResourceBundle;

/**
 * Created by Antonio Zaitoun on 18/12/2017.
 */
public class ReportCellView extends UIView {
    private Report report;

    @FXML
    private Label reportId;

    @FXML
    private Label description;

    @FXML
    private Label date;

    @FXML
    private VBox vbox;

    public void setReport(Report report) {

        reportId.setText(""+report.getReportNum());
        description.setText(report.getDescription());
        date.setText(report.getIncidentDate().toLocaleString());

        date.heightProperty().addListener((observable, oldValue, newValue) -> {
            double reportHeight = (double) newValue;
            double descrHeight = description.getHeight();
            double dateHeight = date.getHeight();

            double spacing = vbox.getSpacing() * 2;
            vbox.setPrefHeight(reportHeight + descrHeight + dateHeight + spacing);
        });

        reportId.setTextFill(Color.WHITE);
        description.setTextFill(Color.WHITE);
        date.setTextFill(Color.WHITE);
    }

    @Override
    public void layoutSubviews(ResourceBundle bundle) {

    }

    @Override
    public void layoutBundle(ResourceBundle bundle) {

    }

    @Override
    public String resource() {
        return "/resources/xml/ListItem.fxml";
    }
}
