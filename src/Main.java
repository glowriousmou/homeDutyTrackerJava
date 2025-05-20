import com.oumou.homeDutyTracker.dao.database.ConnexionDB;
import com.oumou.homeDutyTracker.domain.Parent;
import com.oumou.homeDutyTracker.domain.Tache;
import com.oumou.homeDutyTracker.presentation.TacheTableUI;
import com.oumou.homeDutyTracker.service.TacheService;
import com.oumou.homeDutyTracker.service.ParentService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.time.LocalDateTime;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("./config/log4j.properties");

        Parent parent1= new Parent("Jean", "Cissé", "jean@cisse.com", "1234567");
        Tache tache1 = new Tache(
                "Menage",
                "Passer le ballai dans le salon , la cuisine et les chambres",
                parent1,
                "Initialise",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
        );

        int idParent = ParentService.creerUtilisateur(parent1, "parent");
        parent1.setId(idParent);

        TacheService.creerTache(tache1);

        // System.out.println("Tâche créée avec succès !");
        logger.info("Tâche créée avec succès !");



        TacheTableUI.tableLayout(); // Affiche la table



    }
}