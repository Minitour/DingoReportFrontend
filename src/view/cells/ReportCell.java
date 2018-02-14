package view.cells;

import model.Report;
import ui.UIListViewCell;
import ui.UIView;

/**
 * Created by Antonio Zaitoun on 23/12/2017.
 */
public class ReportCell extends UIListViewCell<Report,UIView> {

    @Override
    public UIView load(Report item) {
        ReportCellView reportCellView = new ReportCellView();
        reportCellView.setReport(item);
        return reportCellView;
    }
}
