package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.database.DatabaseSetupTestGlobal;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import com.oslead.solutions.homeDutyTracker.domain.enumeration.StatutTache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TacheDaoTest {
    private static ITacheDao iTacheDao;
    private static IUtilisateurDao iUtilisateurDao;

    @BeforeAll
    static void setup() {
       /* DatabaseSetupTest.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-test.xml");*/
        DatabaseSetupTestGlobal.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db-test-global/beans-test-global.xml");

        iTacheDao = context.getBean("tacheDao", ITacheDao.class);
        iUtilisateurDao = context.getBean("utilisateurDao", IUtilisateurDao.class);
    }

    @Test
    void testSaveAndFindAll() {

        //Parent createur = new Parent();
        //Enfant responsable = new Enfant();
       // Enfant superviseur = new Enfant();
        /*Optional<Parent> parentOpt = utilisateurs.stream()
                .filter(u -> u instanceof Parent && u.getEmail().equals("jean@cisse-test.com"))
                .map(u -> (Parent) u)
                .findFirst();
        Parent createur = parentOpt.orElseThrow(() -> new IllegalStateException("Createur non trouvé"));
        if (parentOpt.isPresent()) {
            createur = parentOpt.get();
        }

        }*/
        Parent createur= new Parent("Jean", "Cissé", "jean@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant responsable= new Parent("Blaise", "Cissé", "blaise@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        Enfant superviseur= new Parent("Helene", "Cissé", "helene@cisse-test-dao.com", UtilisateurDaoImpl.hashPassword("1234567"));
        int createurId = iUtilisateurDao.save(createur);
        createur.setId(createurId);

        int responsableId = iUtilisateurDao.save(responsable);
        responsable.setId(responsableId);

        int superviseurId = iUtilisateurDao.save(superviseur);
        superviseur.setId(superviseurId);

        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();

        boolean isCreateur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("jean@cisse-test-dao.com"));
        boolean isResponsable = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("blaise@cisse-test-dao.com"));
        boolean isSuperviseur = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equals("helene@cisse-test-dao.com"));

        assertTrue(isCreateur, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isResponsable, "L'utilisateur inséré doit apparaître dans findAll()");
        assertTrue(isSuperviseur, "L'utilisateur inséré doit apparaître dans findAll()");

        Tache tache = new Tache();
        if(isCreateur && isResponsable && isSuperviseur) {
            tache.setNom("Menage-test-dao");
            tache.setDescription("Passer le ballai dans le salon , la cuisine et les chambres");
            tache.setStatut(StatutTache.INITIALIZED);
            tache.setDateCreation(LocalDateTime.now());
            tache.setDateLimite(LocalDateTime.now().plusDays(2));
            tache.setCreateur(createur);
            tache.setResponsable(responsable);
            tache.setSuperviseur(superviseur);


            int generatedId = iTacheDao.save(tache);
            tache.setId(generatedId);
        /*Optional<Utilisateur> fetched = .findByEmail("alice.test@example.com");
        assertTrue(fetched.isPresent(), "User should be found");
        assertEquals("Alice", fetched.get().getPrenom());
        assertEquals("Test", fetched.get().getNom());*/
            //assertEquals(Role.CHILD, fetched.get().getRole());

            // Vérifie que l'utilisateur est présent dans findAll()
            List<Tache> taches = iTacheDao.findAll();
            assertTrue(taches.stream().anyMatch(u -> u.getNom().equals("Menage-test-dao")),
                    "La tache insérée doit apparaître dans findAll()");
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


        /*List<Tache> taches = iTacheDao.findAll();
        taches.stream()
                .forEach(u -> iTacheDao.deleteById(u.getId()));
        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
        utilisateurs.stream()
                .forEach(u -> iUtilisateurDao.deleteById(u.getId()));*/
    }
}
