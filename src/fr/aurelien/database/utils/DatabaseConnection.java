/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.aurelien.database.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe retourne une connexion à une base de données MySql Elle
 * implemente le patern Singleton
 *
 * @author formation
 */
public class DatabaseConnection {

    /**
     * Variable destinée à stocker l'instance de la connexion
     */
    private static Connection instance;

    /**
     * Constructeur Privé pour éviter de pouvoir instancioer la classe depuis
     * l'exterieur
     */
    private DatabaseConnection() {
    }

    /**
     * Retourne un objet de type Connection
     *
     * @return
     * @throws SQLException
     */
    public static Connection getInstance() throws SQLException {

        FileInputStream fis = null;
        try {
            Properties config = new Properties();
            fis = new FileInputStream("./config/app.properties");
            
            //Chargement des données du fichier dans l'objet Properties
            config.load(fis);
            fis.close();
            //Récupération des Information de configuration dans les variables
            String dbHost = config.getProperty("db.host", "localhost");
            String dbName = config.getProperty("db.name", "bibliotheque");
            String dbUser = config.getProperty("db.user", "root");
            String dbPass = config.getProperty("db.pass", "");

            
            //Si l'instance est nulle on instancie la connexion
            if (instance == null) {
                instance = DriverManager.getConnection("jdbc:mysql://"+dbHost+"/"+dbName,dbUser, dbPass);

            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

}
