package outro;

import bean.tabelas.ListaDeInscritosEmUmTorneio;
import data.qry.Select;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

@ManagedBean(name = "datatableManagedBean")
@SessionScoped
public class BeanBrack {

    // Init --------------------------------------------------------------------------------------
    private static List<List<String>> dynamicList; // Simulate fake DB.
    private static String[] dynamicHeaders; // Optional.
    private HtmlPanelGroup dynamicDataTableGroup; // Placeholder.

    // Actions -----------------------------------------------------------------------------------
    private void loadDynamicList() throws SQLException {

        Select sel = new Select();

        int maxInscritos = sel.getTorneioPorNome("oloco testeee").getLimiteDuplas();
        int qtdRodadas = maxInscritos / 2;
        int qtdColunas = 1;

        for (int i = 1; i < maxInscritos; i *= 2) {
            qtdColunas += 2;
        }

        //testMode
        maxInscritos = 8;
        qtdColunas = 7;

        String[] str = new String[qtdColunas];

        for (int i = 0; i < qtdColunas; i++) {
            str[i] = "-";
        }

        ArrayList<ListaDeInscritosEmUmTorneio> lstTor = sel.getListaDeInscritosAtualizada(10);

        dynamicHeaders = str;

        String[] strCol = new String[qtdColunas];

        ArrayList<String[]> arrStrCol = new ArrayList<>();

        Random r = new Random();

        dynamicList = new ArrayList<List<String>>();

        int lstCount = lstTor.size();
        int sep1 = 1;

        for (int i = 0; i < maxInscritos - 1; i++) {

            for (int j = 0; j < qtdColunas; j++) {

                if (j % 2 == 0) {

                    if ((j == 0 || j == qtdColunas - 1) && (i % 2 == 0)) {
                        strCol[j] = String.valueOf(r.nextInt(10));
                        lstCount--;
                    } else {
                        strCol[j] = "";
                    }

                } else {

                    if (sep1 == 4) {
                        strCol = new String[qtdColunas];
                        arrStrCol.add(strCol);
                        strCol = new String[qtdColunas];
                        arrStrCol.add(strCol);
                        strCol = new String[qtdColunas];
                        sep1 = 0;
                        break;

                    } else {

                        if (j == 1 || j == qtdColunas - 2) {
                            strCol[j] = "|";
                        }

                    }
                }

            }

            sep1++;

            System.out.println(Arrays.toString(strCol));
            arrStrCol.add(strCol);

            strCol = new String[qtdColunas];

        }

        for (int i = 0; i < arrStrCol.size(); i++) {
            dynamicList.add(Arrays.asList(arrStrCol.get(i)));
        }

    }

    private void populateDynamicDataTable() {

        // Context and Expression Factory
        FacesContext fCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = fCtx.getELContext();
        ExpressionFactory ef = fCtx.getApplication().getExpressionFactory();

        // Create <h:dataTable value="#{datatableManagedBean.dynamicList}" var="dynamicRow">.
        HtmlDataTable dynamicDataTable = new HtmlDataTable();
        ValueExpression ve = ef.createValueExpression(elCtx, "#{datatableManagedBean.dynamicList}", List.class);
        dynamicDataTable.setValueExpression("value", ve);
        dynamicDataTable.setVar("dynamicRow");

        // Iterate over columns
        for (int i = 0; i < dynamicList.get(0).size(); i++) {

            // Create <h:column>.
            HtmlColumn column = new HtmlColumn();
            dynamicDataTable.getChildren().add(column);

            // Create <h:outputText value="dynamicHeaders[i]"> for <f:facet name="header"> of column.
            HtmlOutputText header = new HtmlOutputText();
            header.setValue(dynamicHeaders[i]);
            column.setHeader(header);

            // Create <h:outputText value="#{dynamicRow[" + i + "]}"> for the body of column.
            HtmlOutputText output = new HtmlOutputText();
            ve = ef.createValueExpression(elCtx, "#{dynamicRow[" + i + "]}", String.class);
            output.setValueExpression("value", ve);
            column.getChildren().add(output);

        }

        // Add the datatable to <h:panelGroup binding="#{datatableManagedBean.dynamicDataTableGroup}">.
        dynamicDataTableGroup = new HtmlPanelGroup();
        dynamicDataTableGroup.getChildren().add(dynamicDataTable);

    }

    // Getters -----------------------------------------------------------------------------------
    public HtmlPanelGroup getDynamicDataTableGroup() throws SQLException {
        // This will be called once in the first RESTORE VIEW phase.
        if (dynamicDataTableGroup == null) {
            loadDynamicList(); // Preload dynamic list.
            populateDynamicDataTable(); // Populate editable datatable.
        }

        return dynamicDataTableGroup;
    }

    public List<List<String>> getDynamicList() {
        return dynamicList;
    }

    // Setters -----------------------------------------------------------------------------------
    public void setDynamicDataTableGroup(HtmlPanelGroup dynamicDataTableGroup) {
        this.dynamicDataTableGroup = dynamicDataTableGroup;
    }

}
