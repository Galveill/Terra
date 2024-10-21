package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Envoltorio para enviar los datos al servidor en formato JSON.
 * @author Adrián
 */
public class MessageWrapper {
    /** El código del usuario. */
    private final String uid;
    /** Los códigos de los tesoros. */
    private final String[] codes;

    /**
     * Constructor parametrizado.
     * @param uid El código del usuario.
     * @param codes Los códigos de los tesoros.
     */
    public MessageWrapper(String uid, String[] codes)
    {
        this.uid = uid;
        this.codes = codes;
    }

    /**
     * @return El código del usuario.
     */
    public String getUid() {
        return uid;
    }

    /**
     * @return Los códigos de los tesoros.
     */
    public String[] getCodes() {
        return codes;
    }

    /**
     * @return La representación en JSON del mensaje.
     */
    public String getJSON()
    {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
