package com.oslead.solutions.homeDutyTracker.presentation;


import com.oslead.solutions.homeDutyTracker.dao.database.DatabaseSetup;
import com.oslead.solutions.homeDutyTracker.domain.Tache;
import com.oslead.solutions.homeDutyTracker.service.interfaces.ITacheService;
import com.oslead.solutions.homeDutyTracker.service.interfaces.IUtilisateurService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class TacheTableUI {
    private static ITacheService iTacheService;
    public static void tableLayout() throws Exception {

        DatabaseSetup.createDatabaseIfNotExists();
        ApplicationContext context = new ClassPathXmlApplicationContext("db/beans-service.xml");
        iTacheService = context.getBean("tacheService", ITacheService.class);

        JFrame frame = new JFrame("Liste des tâches");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);

        frame.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Liste des Tâches", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Colonnes du tableau
        String[] columnNames = {"Nom", "Description", "Date de création", "Date limite", "Statut", "Créateur","superviseur", "responsable"};

       /* TacheDAOV1ImplV1 tacheDAOImplV1 = new TacheDAOV1ImplV1();
        // Récupérer les données depuis la base
        TacheV1ServiceImpl tacheService = new TacheV1ServiceImpl(tacheDAOImplV1);
        List<Tache> taches = tacheService.afficherListTache();*/
        List<Tache> taches = iTacheService.listerTaches();

        // Convertir les données en tableau d’objets pour JTable
        Object[][] data = new Object[taches.size()][columnNames.length];
        for (int i = 0; i < taches.size(); i++) {
            Tache t = taches.get(i);
            data[i][0] = t.getNom();
            data[i][1] = t.getDescription();
            data[i][2] = t.getDateCreation(); // ou formatée avec DateTimeFormatter
            data[i][3] = t.getDateLimite();
            data[i][4] = t.getStatut();
            data[i][5] = t.getCreateur().getPrenom() + " " + t.getCreateur().getNom(); // Affichage du créateur
            data[i][6] = t.getSuperviseur().getPrenom() + " " + t.getSuperviseur().getNom(); // Affichage du superviseur
            data[i][7] = t.getResponsable().getPrenom() + " " + t.getResponsable().getNom(); // Affichage du responsable
        }

        // Création de la table avec les données
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // Centrer le texte
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Styliser l’en-tête
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        // Ajouter la table dans un JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.WHITE);
        table.setBackground(Color.WHITE);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel principal
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
