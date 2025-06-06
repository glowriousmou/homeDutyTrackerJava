package com.oslead.solutions.homeDutyTracker.presentation;

import com.oslead.solutions.homeDutyTracker.dao.database.DatabaseSetup;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Notification;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import com.oslead.solutions.homeDutyTracker.service.interfaces.INotificationService;
import com.oslead.solutions.homeDutyTracker.service.interfaces.ITacheService;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IUtilisateurService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static IUtilisateurService  iUtilisateurService;
    private static ITacheService iTacheService;
    private static INotificationService iNotificationService;

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
            DatabaseSetup.createDatabaseIfNotExists();
            ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-service.xml");
            iUtilisateurService = context.getBean("utilisateurService", IUtilisateurService.class);
            iTacheService = context.getBean("tacheService", ITacheService.class);
            iNotificationService = context.getBean("notificationService", INotificationService.class);
            // PropertyConfigurator.configure("log4j.properties");
            // String password= UtilisateurServiceImplV1.hashPassword("1234567");
            Parent parent1= new Parent("Jean", "Cissé", "jean@cisse.com", "1234567");
            Enfant enfant1= new Parent("Blaise", "Cissé", "blaise@cisse.com", "1234567");
            Enfant enfant2= new Parent("Helene", "Cissé", "helene@cisse.com", "1234567");
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
            /*UtilisateurDAOImplV1 utilisateurDAOImplV1 = new UtilisateurDAOImplV1();
            ParentServiceImpl parentService = new ParentServiceImpl(utilisateurDAOImplV1);*/

            // int idParent = parentService.creerUser(parent1, "parent");
            int idParent = iUtilisateurService.enregistrerUtilisateur(parent1);
            int idEnfant1 = iUtilisateurService.enregistrerUtilisateur(enfant1);
            int idEnfant2= iUtilisateurService.enregistrerUtilisateur(enfant2);
            parent1.setId(idParent);
            enfant1.setId(idEnfant1);
            enfant2.setId(idEnfant2);
            if(idParent > 0 && idEnfant1 > 0 && idEnfant2>0) {
                /*TacheDAOV1ImplV1 tacheDAOImplV1 = new TacheDAOV1ImplV1();
                TacheV1ServiceImpl tacheService = new TacheV1ServiceImpl(tacheDAOImplV1);*/
                // boolean isTacheCreated1 = tacheService.creerTache(tache1);
                int tache1Id = iTacheService.enregistrerTache(tache1);
                int tache2Id = iTacheService.enregistrerTache(tache2);
                tache1.setId(tache1Id);
                tache2.setId(tache2Id);
                if (tache1Id > 0) {
                    Notification notification = new Notification();
                    notification.setMessage("Service: le parent " +
                            " " + tache1.getCreateur().getPrenom()
                            + tache1.getCreateur().getNom()
                            + " a marqué la tache " + tache1.getNom()
                            + "comme " + tache1.getStatut());
                    notification.setDateCreation(LocalDateTime.now());
                    notification.setTache(tache1);

                    int notificationId = iNotificationService.enregistrerNotification(notification);
                    notification.setIdentifiant(notificationId);
                    TacheTableUI.tableLayout(); // Affiche la table
                }

                // System.out.println("Tâche créée avec succès !");
                // logger.info("Tâche créée avec succès ! " + idParent);
                /*NotificationDAOV1Impl notificationDAOImpl= new NotificationDAOV1Impl();
                NotificationV1ServiceImpl notificationService = new NotificationV1ServiceImpl(notificationDAOImpl);
                if (isTacheCreated1) {
                    logger.info("Notification de l'enfant " + enfant1.getPrenom() + " " + enfant1.getNom() + " " + notificationService.afficherHistoriqueNotification(enfant1));
                }
                TacheTableUI.tableLayout(); // Affiche la table
            } else {
                logger.error("Une erreur s'est produite lors de la création des utilisateurs ");
            }*/

            }

        } catch (Exception e) {

            logger.error("Une erreur critique s’est produite dans l’application.", e);

        }
    }
}
