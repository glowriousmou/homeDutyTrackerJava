import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Enfant;
import com.oumou.homeDutyTracker.domain.Parent;
import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.presentation.TacheTableUI;
import com.oumou.homeDutyTracker.service.TacheService;
import com.oumou.homeDutyTracker.service.ParentService;
import com.oumou.homeDutyTracker.service.UtilisateurService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDateTime;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("./config/log4j.properties");
        String password= UtilisateurService.hashPassword("1234567");
        Parent parent1= new Parent("Jean", "Cissé", "jean@cisse.com", password);
        Enfant enfant1= new Parent("Blaise", "Cissé", "blaise@cisse.com", password);
        Enfant enfant2= new Parent("Helene", "Cissé", "helene@cisse.com", password);
        Tache tache1 = new Tache(
                "Menage",
                "Passer le ballai dans le salon , la cuisine et les chambres",
                enfant1,
                enfant2,
                parent1,
                "Initialise",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
        );
        Tache tache2 = new Tache(
                "Vaisselle",
                "Netoyer, ranger et sécher la vaisselle",
                enfant2,
                enfant1,
                parent1,
                "Initialise",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
        );

        int idParent = ParentService.creerUtilisateur(parent1, "parent");
        int idEnfant1 = ParentService.creerUtilisateur(enfant1, "enfant");
        int idEnfant2= ParentService.creerUtilisateur(enfant2, "enfant");
        parent1.setId(idParent);
        enfant1.setId(idEnfant1);
        enfant2.setId(idEnfant2);

        TacheService.creerTache(tache1);
        TacheService.creerTache(tache2);

        // System.out.println("Tâche créée avec succès !");
        logger.info("Tâche créée avec succès !");



        TacheTableUI.tableLayout(); // Affiche la table



    }
}