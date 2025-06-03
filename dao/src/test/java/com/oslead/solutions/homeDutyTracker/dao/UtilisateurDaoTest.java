package com.oslead.solutions.homeDutyTracker.dao;

import com.oslead.solutions.homeDutyTracker.dao.interfaces.IUtilisateurDao;
import com.oslead.solutions.homeDutyTracker.domain.Utilisateur;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilisateurDaoTest {
    private static IUtilisateurDao iUtilisateurDao;

    @BeforeAll
    static void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-test.xml");
        iUtilisateurDao = context.getBean("utilisateurDao", IUtilisateurDao.class);
    }

    @Test
    void testSaveAndFindByEmail() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom("Alice");
        utilisateur.setNom("Test");
        utilisateur.setEmail("alice.test@example.com");
        utilisateur.setMotPasse("securepass");
        //user.setRole(Role.CHILD);

        iUtilisateurDao.save(utilisateur);
        Optional<Utilisateur> fetched = iUtilisateurDao.findByEmail("alice.test@example.com");

        assertTrue(fetched.isPresent(), "User should be found");
        assertEquals("Alice", fetched.get().getPrenom());
        assertEquals("Test", fetched.get().getNom());
        //assertEquals(Role.CHILD, fetched.get().getRole());
    }

    @AfterEach
    void cleanUp() {
        Optional<Utilisateur> fetched = iUtilisateurDao.findByEmail("alice.test@example.com");
        fetched.ifPresent(user -> iUtilisateurDao.deleteById(user.getId()));
    }
}
