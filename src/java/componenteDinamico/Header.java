package componenteDinamico;

import java.util.List;

public class Header {

    private String name;
    private List<SubCol> subColList;

    public Header() {
    }

    public Header(String name, List<SubCol> subColList) {
        this.name = name;
        this.subColList = subColList;
    }
    //getter & setter

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the subColList
     */
    public List<SubCol> getSubColList() {
        return subColList;
    }

    /**
     * @param subColList the subColList to set
     */
    public void setSubColList(List<SubCol> subColList) {
        this.subColList = subColList;
    }
}
