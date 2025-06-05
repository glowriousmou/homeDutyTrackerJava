package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.ITacheDao;
import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Enfant;
import com.oslead.solutions.homeDutyTracker.domain.Parent;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilisateurDaoTest {
    private static IUtilisateurDao iUtilisateurDao;

    @BeforeAll
    static void setup() {
        DatabaseSetupTest.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-test.xml");
        iUtilisateurDao = context.getBean("utilisateurDao", IUtilisateurDao.class);
    }

    @Test
    void testSaveAndFindAll() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom("Alice");
        utilisateur.setNom("Test");
        utilisateur.setEmail("alice.test-dao@example.com");
        utilisateur.setMotPasse("securepass");
        //user.setRole(Role.CHILD);

        int generatedId = iUtilisateurDao.save(utilisateur);
        utilisateur.setId(generatedId);

        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
        assertTrue(utilisateurs.stream().anyMatch(u -> u.getEmail().equals("alice.test-dao@example.com")),
                "L'utilisateur inséré doit apparaître dans findAll()");

    }

    @AfterEach
    void cleanUp() {
        /*Optional<Utilisateur> fetched = iUtilisateurDao.findByEmail("alice.test@example.com");
        fetched.ifPresent(user -> iUtilisateurDao.deleteById(user.getId()));/*/
        /*List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
        utilisateurs.stream()
                .filter(u -> "alice.test@example.com".equals(u.getEmail()))
                .forEach(u -> iUtilisateurDao.deleteById(u.getId()));*/
        List<Utilisateur> utilisateurs = iUtilisateurDao.findAll();
        utilisateurs.stream()
                .forEach(u -> iUtilisateurDao.deleteById(u.getId()));
    }
}
