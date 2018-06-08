package componenteDinamico;

import java.util.ArrayList;
import java.util.List;

public class MockDb {

    public static List<Header> getHeaderList() {
        List<Header> headerList = new ArrayList<Header>();

        List<SubCol> subColList_1 = new ArrayList<SubCol>();
        subColList_1.add(new SubCol("Col 1", "Data 1"));
        subColList_1.add(new SubCol("Col 2", "Data 2"));
        subColList_1.add(new SubCol("Col 3", "Data 3"));
        headerList.add(new Header("Header 1", subColList_1));

        List<SubCol> subColList_2 = new ArrayList<SubCol>();
        subColList_2.add(new SubCol("Col 1", "Data 1"));
        subColList_2.add(new SubCol("Col 2", "Data 2"));
        subColList_2.add(new SubCol("Col 3", "Data 3"));
        headerList.add(new Header("Header 2", subColList_2));

        List<SubCol> subColList_3 = new ArrayList<SubCol>();
        subColList_3.add(new SubCol("Col 1", "Data 1"));
        subColList_3.add(new SubCol("Col 2", "Data 2"));
        subColList_3.add(new SubCol("Col 3", "Data 3"));
        subColList_3.add(new SubCol("Col 4", "Data 4"));
        headerList.add(new Header("Header 3", subColList_3));

        List<SubCol> subColList_4 = new ArrayList<SubCol>();
        subColList_4.add(new SubCol("Col 1", "Data 1"));
        subColList_4.add(new SubCol("Col 2", "Data 2"));
        subColList_4.add(new SubCol("Col 3", "Data 3"));
        subColList_4.add(new SubCol("Col 4", "Data 4"));
        subColList_4.add(new SubCol("Col 5", "Data 5"));
        headerList.add(new Header("Header 4", subColList_4));

        return headerList;
    }

    public static List<DataValue> getValueList() {
        /*Create 5 rows for data array for 15 columns*/
        List<DataValue> valueList = new ArrayList<DataValue>();
        for (int i = 1; i <= 5; i++) {
            DataValue data = new DataValue("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15");
            valueList.add(data);
        }
        return valueList;
    }
}
