package bean;

//create new button object
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIColumn;
import javax.faces.component.UIData;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;

@ManagedBean(name = "componente")

public class BeanComponentes {

    public HtmlPanelGroup buttonPlaceHolder;

    public HtmlPanelGroup getButtonPlaceHolder() {
        return buttonPlaceHolder;
    }

    public void setButtonPlaceHolder(HtmlPanelGroup buttonPlaceHolder) {
        this.buttonPlaceHolder = buttonPlaceHolder;
    }

    public String myAction() {
        System.out.println("myAction called from the dynamic Button");

//retrive the dynamically added button
        HtmlCommandButton button = (HtmlCommandButton) buttonPlaceHolder.getChildren().get(0);
        button.setValue("I clicked an Action");

        return "Index2";
    }

    public String addButton() {
        System.out.println("adding Button to the placeholder pannel");

//create new button object
        HtmlCommandButton commandButton = new HtmlCommandButton();
        commandButton.setValue("Click Me");

        HtmlCommandButton commandButton2 = new HtmlCommandButton();
        commandButton.setValue("Click Me2");

        UIData dtTable = new UIData();
        UIColumn column = new UIColumn();
        UIColumn column2 = new UIColumn();

// assign random id to avoid duplicate id for subsquent click
        commandButton.setId("button" + Math.round(Math.random() * 1000));
        commandButton2.setId("button" + Math.round(Math.random() * 1000));
        dtTable.setId("dtTable" + Math.round(Math.random() * 1000));
        column.setId("column" + Math.round(Math.random() * 1000));
        column2.setId("column" + Math.round(Math.random() * 1000));

//bind the method
        MethodBinding mb = FacesContext.getCurrentInstance()
                .getApplication().createMethodBinding("#{componente.myAction}", new Class[0]);
        commandButton.setAction(mb);

// add button the place holder tag created in the JSP
        buttonPlaceHolder.getChildren().add(dtTable);
        dtTable.getChildren().add(column);
        dtTable.getChildren().add(column2);
        column.getChildren().add(commandButton);
        column2.getChildren().add(commandButton2);

        return "Index2";
    }

}
