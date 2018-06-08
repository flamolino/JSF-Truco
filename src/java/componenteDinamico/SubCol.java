package componenteDinamico;

public class SubCol {

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
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    private String name;
    private String data;

    public SubCol() {
    }

    public SubCol(String name, String data) {
        this.name = name;
        this.data = data;
    }
    //getter & setter
}
