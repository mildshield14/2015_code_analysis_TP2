import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

class BST {

    protected static  TreeSet<Medicament> tree;

    public static TreeSet<Medicament> getTree() {
        return tree;
    }

    public static void setTree(TreeSet<Medicament> tree) {
        BST.tree = tree;
    }

    // on utilise ceci pour s'assurer que si notre code est rajouté à une boucle pour des fins de testage,
    // s'assure d'avoir un arbre vide.
    public static void firsttime (){
        tree= new TreeSet<Medicament>();
    }

    // cette méthode va trouver la date la plus proche du médicament choisi par rapport à la date courante.
    public static Medicament findClosest(String nom, LocalDate date, int total) {
        Medicament m = null;
        long closestDiff = Long.MAX_VALUE;
        for (Medicament M : tree) {
            long diff = ChronoUnit.DAYS.between(M.getDateExpi(), date);
            if (M.getStock()>=total && Math.abs(diff)<=closestDiff && M.getNom().equals( nom)) {
                closestDiff = Math.abs(diff);
                m = M;
            }
        }

        // on passe a la prochaine date vu que le stock de la date ideale n'est pas dispo
        if (m==null){
            for (Medicament M : tree) {
                long diff = ChronoUnit.DAYS.between(M.getDateExpi(), date);
                if (Math.abs(diff)<=closestDiff && M.getNom().equals( nom)) {
                    closestDiff = Math.abs(diff);
                    m = M;
                }
            }
        }

        return m;
    }

    // va permettre de mettre en sortie tout le stock de l'arbre.
    public static ArrayList<String> outputStock(){
        ArrayList<String> medsStock =new ArrayList<String>();
        for (Medicament med:tree) {
            medsStock.add(med.getNom() + "\t" + med.getStock() + "\t" + med.getDateExpi());
        }
        Collections.sort(medsStock);
        // on utilise cette methode afin de permettre et un sort sur le nom puis si e nom est pareil,
        // un sort sur la date (exprssion lambda suggeré par IntelliJ)
        Collections.sort(medsStock, (med1, med2) -> {
            String[] med1Parts = med1.split("\t");
            String[] med2Parts = med2.split("\t");

            // Compare names
            int nameComparison = med1Parts[0].compareTo(med2Parts[0]);
            if (nameComparison != 0) {
                return nameComparison;
            }

            // If names are the same, compare dates
            return med1Parts[2].compareTo(med2Parts[2]);
        });

        return medsStock;
    }

    // enleve un medicament de l'arbre
    public static void removeMed(Medicament med){
        tree.remove(med);
        setTree(tree);
    }

    // ajoute un medicament à l'arbre
    public static void addMed(Medicament med){
        tree.add(med);
        setTree(tree);
    }

    // enleve tous les medicament expirés de l'arbre
    public static void removeAllExpired(LocalDate date){

        tree.removeIf(mmm -> mmm.getDateExpi().isBefore(date));

        tree.removeIf(mmm -> mmm.getDateExpi().isEqual(date));
    }

    //cherche un medicament grace au nom et la date d'expi
    public static Medicament searchMed(Medicament med) {

        for (Medicament medicament:tree) {

            if (med.getNom().equals(medicament.getNom()) && med.getDateExpi().equals(medicament.getDateExpi())){
                return medicament;
            }
        }

        return null;
    }

}
