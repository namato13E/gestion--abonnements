package Camping;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DbConfig {
    private String url;
    private String login;
    private String password;

    public static DbConfig charger(String chemin) {
        try (Reader reader = new FileReader(chemin)) {
            return new Gson().fromJson(reader, DbConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Fichier de configuration introuvable : " + chemin, e);
        }
    }

    public String getUrl()      { return url; }
    public String getLogin()    { return login; }
    public String getPassword() { return password; }
}
