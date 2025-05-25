package com.oslead.solutions.homeDutyTracker.presentation;

import com.oslead.solutions.homeDutyTracker.dao.NotificationDAOImpl;
import com.oslead.solutions.homeDutyTracker.dao.TacheDAOImpl;
import com.oslead.solutions.homeDutyTracker.dao.UtilisateurDAOImpl;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import com.oslead.solutions.homeDutyTracker.service.NotificationServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.ParentServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.TacheServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.UtilisateurServiceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDateTime;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {

        try {

            try {
                //PropertyConfigurator.configure("log4j.properties");
                PropertyConfigurator.configure(
                        App.class.getClassLoader().getResource("log4j.properties")
                );
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de Log4j : " + e);
                //e.printStackTrace();
            }
           // PropertyConfigurator.configure("log4j.properties");
            String password= UtilisateurServiceImpl.hashPassword("1234567");
            Parent parent1= new Parent("Jean", "Cissé", "jean@cisse.com", password);
            Enfant enfant1= new Parent("Blaise", "Cissé", "blaise@cisse.com", password);
            Enfant enfant2= new Parent("Helene", "Cissé", "helene@cisse.com", password);
            Tache tache1 = new Tache(
                    "Menage",
                    "Passer le ballai dans le salon , la cuisine et les chambres",
                    enfant1,
                    enfant2,
                    parent1,
                    StatutTache.INITIALIZED,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(2)
            );
            Tache tache2 = new Tache(
                    "Vaisselle",
                    "Netoyer, ranger et sécher la vaisselle",
                    enfant2,
                    enfant1,
                    parent1,
                    StatutTache.INITIALIZED,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(2)
            );
            UtilisateurDAOImpl utilisateurDAOImpl = new UtilisateurDAOImpl();
            ParentServiceImpl parentService = new ParentServiceImpl(utilisateurDAOImpl);
            int idParent = parentService.creerUser(parent1, "parent");
            int idEnfant1 = parentService.creerUser(enfant1, "enfant");
            int idEnfant2= parentService.creerUser(enfant2, "enfant");
            parent1.setId(idParent);
            enfant1.setId(idEnfant1);
            enfant2.setId(idEnfant2);
            if(idParent > 0 && idEnfant1 > 0 && idEnfant2>0) {
                TacheDAOImpl tacheDAOImpl = new TacheDAOImpl();
                TacheServiceImpl tacheService = new TacheServiceImpl(tacheDAOImpl);
                boolean isTacheCreated1 = tacheService.creerTache(tache1);
                boolean isTacheCreated2 = tacheService.creerTache(tache2);

                // System.out.println("Tâche créée avec succès !");
                // logger.info("Tâche créée avec succès ! " + idParent);
                NotificationDAOImpl notificationDAOImpl= new NotificationDAOImpl();
                NotificationServiceImpl notificationService = new NotificationServiceImpl(notificationDAOImpl);
                if (isTacheCreated1) {
                    logger.info("Notification de l'enfant " + enfant1.getPrenom() + " " + enfant1.getNom() + " " + notificationService.afficherHistoriqueNotification(enfant1));
                }
                TacheTableUI.tableLayout(); // Affiche la table
            } else {
                logger.error("Une erreur s'est produite lors de la création des utilisateurs ");
            }




        } catch (Exception e) {

            logger.error("Une erreur critique s’est produite dans l’application.", e);

        }
    }
}
