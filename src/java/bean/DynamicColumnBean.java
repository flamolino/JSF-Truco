package bean;

import componenteDinamico.Header;
import componenteDinamico.MockDb;
import componenteDinamico.SubCol;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;

@ManagedBean(name = "DynamicColumnBean")
@ViewScoped
public class DynamicColumnBean {

    private DataTable myDataTable;

    public DataTable getMyDataTable() {
        return myDataTable;
    }

    public void setMyDataTable(DataTable myDataTable) {
        this.myDataTable = myDataTable;
    }

    @PostConstruct
    public void init() {
        myDataTable = new DataTable();
        myDataTable.setValue(MockDb.getValueList());
        myDataTable.setVar("mydata");

        ColumnGroup columnGroup = new ColumnGroup();
        myDataTable.getChildren().add(columnGroup);
        columnGroup.setType("header");
        List<Header> headerList = MockDb.getHeaderList();

        /*Create Column for Header*/
        Row headerRow = new Row();
        columnGroup.getChildren().add(headerRow);
        for (Header header : headerList) {
            Column column = new Column();
            /*Make sure to set column span*/
            column.setColspan(header.getSubColList().size());
            headerRow.getChildren().add(column);
            column.setHeaderText(header.getName());
        }

        FacesContext fc = FacesContext.getCurrentInstance();
        Application application = fc.getApplication();
        ExpressionFactory ef = application.getExpressionFactory();
        ELContext elc = fc.getELContext();

        /*Create Column for Sub Column of current header*/
        Row subColRow = new Row();
        columnGroup.getChildren().add(subColRow);
        for (Header header : headerList) {
            for (SubCol subCol : header.getSubColList()) {
                Column column = new Column();
                subColRow.getChildren().add(column);
                column.setHeaderText(subCol.getName());
            }
        }

        /*Create data row for 15 columns*/
        for (int i = 1; i <= 15; i++) {
            ValueExpression valueExp = ef.createValueExpression(elc, "#{mydata.data" + i + "}", Object.class);
            HtmlOutputText output = (HtmlOutputText) application.createComponent(HtmlOutputText.COMPONENT_TYPE);
            output.setValueExpression("value", valueExp);
            Column dataColumn = new Column();
            dataColumn.getChildren().add(output);
            myDataTable.getChildren().add(dataColumn);
        }
    }
}
