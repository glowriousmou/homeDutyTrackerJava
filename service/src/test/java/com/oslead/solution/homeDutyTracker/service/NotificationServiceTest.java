package com.oslead.solution.homeDutyTracker.service;

import com.oslead.solutions.homeDutyTracker.dao.UtilisateurDaoImpl;
import com.oslead.solutions.homeDutyTracker.dao.database.DatabaseSetupTestGlobal;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.INotificationDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.*;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import com.oslead.solutions.homeDutyTracker.service.NotificationServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.TacheServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.UtilisateurServiceImpl;
import com.oslead.solutions.homeDutyTracker.service.interfaces.INotificationService;
import com.oslead.solutions.homeDutyTracker.service.interfaces.ITacheService;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IUtilisateurService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificationServiceTest {
    private static final Logger logger = Logger.getLogger(NotificationServiceTest.class);



    private IUtilisateurService  iUtilisateurService;
    private ITacheService iTacheService;
    private INotificationService iNotificationService;





    private  Parent createur;
    private  Enfant responsable;
    private   Enfant superviseur;
    private   Tache tache;

    @BeforeEach
    void setup() {
        DatabaseSetupTestGlobal.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-service.xml");
        iUtilisateurService = context.getBean("utilisateurService", IUtilisateurService.class);
        iTacheService = context.getBean("tacheService", ITacheService.class);
        iNotificationService = context.getBean("notificationService", INotificationService.class);
        /*tacheDao = Mockito.mock(ITacheDao.class);
        tacheService = new TacheServiceImpl(tacheDao);

        iUtilisateurDao = Mockito.mock(IUtilisateurDao.class);
        utilisateurService = new UtilisateurServiceImpl(iUtilisateurDao);
        // iTacheDao = context.getBean("tacheDao", ITacheDao.class);

        iNotificationDao = Mockito.mock(INotificationDao.class);
        notificationService = new NotificationServiceImpl(iNotificationDao);*/
    }
    @Test
    void testEnregistrerUtilisateur() {
        Parent _createur= new Parent("Patrick", "Cissé", "patrick@cisse-test-service.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant _responsable= new Parent("Alphonse", "Cissé", "alphonse@cisse-test-service.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant _superviseur= new Parent("Germaine", "Cissé", "germaine@cisse-test-service.com", UtilisateurDaoImpl.hashPassword("1234567"));
        int createurId = iUtilisateurService.enregistrerUtilisateur(_createur);
        _createur.setId(createurId);

        int responsableId = iUtilisateurService.enregistrerUtilisateur(_responsable);
        _responsable.setId(responsableId);

        int superviseurId =iUtilisateurService.enregistrerUtilisateur(_superviseur);
        _superviseur.setId(superviseurId);

        List<Utilisateur> utilisateurs =iUtilisateurService.listerUtilisateurs();

        boolean isCreateur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("patrick@cisse-test-service.com"));
        boolean isResponsable = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("alphonse@cisse-test-service.com"));
        boolean isSuperviseur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("germaine@cisse-test-service.com"));

        assertTrue(isCreateur, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isResponsable, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isSuperviseur, "L'utilisateur inséré doit apparaître dans findAll()");
        if(isCreateur && isResponsable && isSuperviseur) {
            logger.info("testEnregistrerUtilisateur ok");
            createur = _createur;
            responsable= _responsable;
            superviseur= _superviseur;
            testEnregistrerTache();
        }

    }

    void testEnregistrerTache() {
        Tache _tache = new Tache();
        _tache.setNom("Vaisselle-test-service");
        _tache.setDescription("Laver, sécher et ranger les assiettes");
        _tache.setStatut(StatutTache.INITIALIZED);
        _tache.setDateCreation(LocalDateTime.now());
        _tache.setDateLimite(LocalDateTime.now().plusDays(2));
        _tache.setCreateur(createur);
        _tache.setResponsable(responsable);
        _tache.setSuperviseur(superviseur);


        int tacheId = iTacheService.enregistrerTache(_tache);
        _tache.setId(tacheId);
        List<Tache> taches = iTacheService.listerTaches();
        boolean isTache = taches.stream()
                .anyMatch(u ->  Objects.equals(u.getId(), tacheId));
        // Vérifie que l'utilisateur est présent dans findAll()
        assertTrue(isTache, "La tache insérée doit apparaître dans findAll()");
        if(isTache){
            tache=_tache;
            testEnregistrerNotification();
        }
    }

    void testEnregistrerNotification() {
        Notification notification = new Notification();
        notification.setMessage("Service: le parent " +
                " " + tache.getCreateur().getPrenom()
                + tache.getCreateur().getNom()
                +" a marqué la tache " + tache.getNom()
                + "comme " + tache.getStatut());
        notification.setDateCreation(LocalDateTime.now());
        notification.setTache(tache);

        int notificationId = iNotificationService.enregistrerNotification(notification);
        notification.setIdentifiant(notificationId);
        List<Notification> notifications = iNotificationService.listerNotifications();
        boolean isNotification = notifications.stream()
                .anyMatch(u ->  Objects.equals(u.getIdentifiant(), notificationId));
        // Vérifie que l'utilisateur est présent dans findAll()
        assertTrue(isNotification, "La notification insérée doit apparaître dans findAll()");
    }
   /* @Test
    void testListerTaches() {
        Tache tache = new Tache();
        t1.setNom("Test Tache 1");

        when(tacheDao.findAll()).thenReturn(Arrays.asList(t1));

        List<Tache> result = tacheService.listerTaches();
        assertEquals(1, result.size());
        assertEquals("Test Tache 1", result.get(0).getNom());

        verify(tacheDao, times(1)).findAll();
    }*/
}
