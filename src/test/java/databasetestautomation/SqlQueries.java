package databasetestautomation;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Ace
 * @created 26-05-2026
 */
public class SqlQueries {

     /**
      * Recherche une langue dans la table countrylanguage
      * @param langue     : la langue à chercher (ex: "French")
      * @param connection : la connexion MySQL déjà ouverte
      * @return true si la langue existe ET est officielle, false sinon
      */
     public boolean getcountrylanguageInfo(String langue, Connection connection) throws SQLException {

         // Résultat final retourné à la fin de la méthode
         boolean isLanguageExist = false;

         // Statement   = objet qui envoie la requête SQL à MySQL
         // ResultSet   = tableau de résultats retourné par MySQL (lié à la connexion)
         // CachedRowSet = copie locale du ResultSet, utilisable même après fermeture de la connexion
         Statement statement = null;
         ResultSet resultSet = null;
         CachedRowSet cachedRowSet = null;

         // Création du CachedRowSet vide (conteneur hors connexion)
         cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

         // Préparation de l'objet pour envoyer des requêtes SQL
         statement = connection.createStatement();

         // Construction de la requête SQL
         // String.format remplace %s par la valeur de langue
         // Exemple : si langue = "French" → ... where language='French'
         String sqlScript = String.format(
                 "select countrycode,language,isofficial from countrylanguage where language='%s'", langue);

         // Envoi de la requête à MySQL — les résultats sont stockés dans resultSet
         resultSet = statement.executeQuery(sqlScript);

         // isBeforeFirst() retourne true s'il y a des résultats
         // !isBeforeFirst() → aucun résultat trouvé pour cette langue
         if (!resultSet.isBeforeFirst()) {
             System.out.println("Language " + langue + " does not exist");
         } else {
             try {
                 // Copie tous les résultats dans le CachedRowSet
                 // Avantage : on peut fermer la connexion et continuer à lire les données
                 cachedRowSet.populate(resultSet);
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }

             int count = 0;

             // cachedRowSet.next() avance ligne par ligne (comme un curseur)
             while (cachedRowSet.next()) {

                 // getString("colonne") lit la valeur de la colonne pour la ligne courante
                 String countryCode = cachedRowSet.getString("countrycode");
                 String language    = cachedRowSet.getString("language");
                 String isOfficial  = cachedRowSet.getString("isofficial");

                 System.out.println(String.format("Country Code: %s, Language: %s, Is Official: %s",
                         countryCode, language, isOfficial));

                 // getRow() retourne le numéro de la ligne courante (commence à 1)
                 count = cachedRowSet.getRow();

                 // La langue est validée seulement si :
                 // - Il y a au moins 1 ligne (count >= 1)
                 // - La langue correspond à celle recherchée
                 // - Elle est officielle : "T" = True dans la base world
                 if (count >= 1 && language.equals(langue) && isOfficial.equals("T")) {
                     isLanguageExist = true;
                 }
             }
         }

         // Retourne true si une langue officielle a été trouvée, false sinon
         return isLanguageExist;
     }


    public boolean getCityByPopulation(long population, Connection connection) throws SQLException {

        boolean isCityExist = false;
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSet cachedRowSet = null;

        cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        statement = connection.createStatement();

        // %d pour un entier (population est un long)
        // Pas de guillemets simples autour de %d car population est un nombre
        String sqlScript = String.format(
                "select ID, Name, District from city where Population>%d", population);

        resultSet = statement.executeQuery(sqlScript);

        // isBeforeFirst() retourne true s'il y a des résultats
        // !isBeforeFirst() → aucun résultat trouvé pour cette population
        if (!resultSet.isBeforeFirst()) {
            System.out.println("Population " + population + " does not exist");
        } else {
            try {
                cachedRowSet.populate(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            int count = 0;

            while (cachedRowSet.next()) {
                int id         = cachedRowSet.getInt("ID");
                String name    = cachedRowSet.getString("Name");
                String district = cachedRowSet.getString("District");

                System.out.println(String.format("ID: %d, Name: %s, District: %s",
                        id, name, district));

                count = cachedRowSet.getRow();
                if (count >= 1) {
                    isCityExist = true;
                }
            }
        }

        // Retourne true si une ville avec cette population a été trouvée, false sinon
        return isCityExist;
    }
}
