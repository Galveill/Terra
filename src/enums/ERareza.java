package enums;

/**
 * El nivel de rareza del elemento.
 * @author Adrián.
 */
public enum ERareza {
    COMUN("Común"),
    NORMAL("Normal"),
    INFRECUENTE("Infrecuente"),
    RARO("Raro"),
    MITICO("Mítico"),
    
    UNICO("Único");


    /** El texto representativo */
    private String txt;

    /**
     * Constructor básico para darle valor.
     * @param txt El texto representativo.
     */
    private ERareza(String txt)
    {
        this.txt = txt;
    }

    public String toString()
    {
        return this.txt;
    }
}
