package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.database.DatabaseSetupTestGlobal;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.INotificationDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.*;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationDaoTest {
    private static ITacheDao iTacheDao;
    private static IUtilisateurDao iUtilisateurDao;
    private static INotificationDao iNotificationDao;

    @BeforeAll
    static void setup() {
        // DatabaseSetupTestDatabaseSetupTest.createDatabaseIfNotExists();
        //ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-test.xml");
        DatabaseSetupTestGlobal.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db-test-global/beans-test-global.xml");
        iTacheDao = context.getBean("tacheDao", ITacheDao.class);
        iUtilisateurDao = context.getBean("utilisateurDao", IUtilisateurDao.class);
        iNotificationDao = context.getBean("notificationDao", INotificationDao.class);
    }

    @Test
    void testSaveAndFindAll() {
        Parent createur= new Parent("Patrick", "Cissé", "patrick@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant responsable= new Parent("Alphonse", "Cissé", "alphonse@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant superviseur= new Parent("Germaine", "Cissé", "germaine@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        int createurId = iUtilisateurDao.save(createur);
        createur.setId(createurId);

        int responsableId = iUtilisateurDao.save(responsable);
        responsable.setId(responsableId);

        int superviseurId = iUtilisateurDao.save(superviseur);
        superviseur.setId(superviseurId);

        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();

        boolean isCreateur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("patrick@cisse-test-dao.com"));
        boolean isResponsable = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("alphonse@cisse-test-dao.com"));
        boolean isSuperviseur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("germaine@cisse-test-dao.com"));

        assertTrue(isCreateur, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isResponsable, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isSuperviseur, "L'utilisateur inséré doit apparaître dans findAll()");

        Tache tache = new Tache();
        if(isCreateur && isResponsable && isSuperviseur) {
            tache.setNom("Vaisselle-test-dao");
            tache.setDescription("Laver, sécher et ranger les assiettes");
            tache.setStatut(StatutTache.INITIALIZED);
            tache.setDateCreation(LocalDateTime.now());
            tache.setDateLimite(LocalDateTime.now().plusDays(2));
            tache.setCreateur(createur);
            tache.setResponsable(responsable);
            tache.setSuperviseur(superviseur);


            int tacheId = iTacheDao.save(tache);
            tache.setId(tacheId);
            List<Tache> taches = iTacheDao.findAll();
            boolean isTache = taches.stream()
                    .anyMatch(u ->  Objects.equals(u.getId(), tacheId));
            // Vérifie que l'utilisateur est présent dans findAll()
            assertTrue(isTache, "La tache insérée doit apparaître dans findAll()");
            if(isTache) {
                Notification notification = new Notification();
                notification.setMessage("le parent " +
                        " " + tache.getCreateur().getPrenom()
                        + tache.getCreateur().getNom()
                        +" a marqué la tache " + tache.getNom()
                        + "comme " + tache.getStatut());
                notification.setDateCreation(LocalDateTime.now());
                notification.setTache(tache);

                int notificationId = iNotificationDao.save(notification);
                notification.setIdentifiant(notificationId);
                List<Notification> notifications = iNotificationDao.findAll();
                boolean isNotification = notifications.stream()
                        .anyMatch(u ->  Objects.equals(u.getIdentifiant(), notificationId));
                // Vérifie que l'utilisateur est présent dans findAll()
                assertTrue(isNotification, "La notification insérée doit apparaître dans findAll()");
            }

        }
    }

    @AfterEach
    void cleanUp() {
        /*Optional<Utilisateur> fetched = iUtilisateurDao.findByEmail("alice.test@example.com");
        fetched.ifPresent(user -> iUtilisateurDao.deleteById(user.getId()));/*/
        // List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
       /* utilisateurs.stream()
                .filter(u -> "alice.test@example.com".equals(u.getEmail()))
                .forEach(u -> iUtilisateurDao.deleteById(u.getId()));*/


        /*List<Notification> notifications = iNotificationDao.findAll();
        notifications.stream()
                .forEach(u -> iNotificationDao.deleteById(u.getIdentifiant()));
        List<Tache> taches = iTacheDao.findAll();
        taches.stream()
                .forEach(u -> iTacheDao.deleteById(u.getId()));
        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
        utilisateurs.stream()
                .forEach(u -> iUtilisateurDao.deleteById(u.getId()));*/
    }
}
