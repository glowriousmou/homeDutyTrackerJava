import com.homeDutyTracker.domain.Parent;
import com.homeDutyTracker.domain.Tache;
import com.homeDutyTracker.presentation.TacheTableUI;
import com.homeDutyTracker.service.TacheService;
import com.homeDutyTracker.service.UtilisateuService;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        Parent parent1= new Parent("Jean", "Cissé", "jean@cisse.com", "123456");
        Tache tache1 = new Tache(
                "Menage",
                "Passer le ballai dans le salon , la cuisine et les chambres",
                parent1,
                "Initialise",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
        );

        int idParent = UtilisateuService.creerUtilisateur(parent1, "parent");
        parent1.setId(idParent);

        TacheService.creerTache(tache1);

        System.out.println("Tâche créée avec succès !");



        TacheTableUI.tableLayout(); // Affiche la table



    }
}