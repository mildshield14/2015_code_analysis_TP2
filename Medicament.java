import java.util.UUID;
import java.time.LocalDate;

class Medicament implements Comparable<Medicament> {
    private String nom;
    private UUID uuid;
    private LocalDate dateExpi;
    private int stock;

    @Override
    public int compareTo(Medicament m) {

        return this.uuid.compareTo(m.getUUID());

    }
   public Medicament(String nom, UUID uuid, LocalDate dateExpi, int stock){
        this.nom=nom;
        this.uuid=uuid;
        this.dateExpi=dateExpi;
        this.stock=stock;
   }

   public int getStock() {
       return stock;
   }

   public LocalDate getDateExpi() {
       return dateExpi;
   }

   public String getNom(){
    return nom;
   }


   public UUID getUUID(){
    return uuid;
   }
   
    public void setStock(int stock) {
        this.stock = stock;
    }
}

