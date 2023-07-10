import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class ManipulationFichier {
    private static final String[] MEDICAMENTS = {
            "Medicament1", "Medicament2", "Medicament3", "Medicament4", "Medicament5",
            "Medicament6", "Medicament7", "Medicament8", "Medicament9", "Medicament10",
            "Medicament11", "Medicament12", "Medicament13", "Medicament14", "Medicament15",
            "Medicament16", "Medicament17", "Medicament18", "Medicament19", "Medicament20",
            "Medicament21", "Medicament22", "Medicament23", "Medicament24", "Medicament25",
            "Medicament26", "Medicament27", "Medicament28", "Medicament29", "Medicament30",
            "Medicament31", "Medicament32", "Medicament33", "Medicament34", "Medicament35",
            "Medicament36", "Medicament37", "Medicament38", "Medicament39", "Medicament40",
            "Medicament41", "Medicament42", "Medicament43", "Medicament44", "Medicament45",
            "Medicament46", "Medicament47", "Medicament48", "Medicament49", "Medicament50",
            "Medicament51", "Medicament52", "Medicament53", "Medicament54", "Medicament55",
            "Medicament56", "Medicament57", "Medicament58", "Medicament59", "Medicament60"
    };

    private static final int MAX_STOCK = 75;
   // public static val =0;
    private static final int MAX_PRESCRIPTION_QUANTITY = 100;
   // private static final int INPUT_SIZE = 30; // Specify the desired input size here

    private static final Random random = new Random();

    public static int maindo(int num,String[] args) {
        String fileName = "test.txt"; // Specify the output file name here
        int num1=0;
        try (FileWriter writer = new FileWriter(fileName)) {
            num1 = generateFileContent(num,writer);
        //    System.out.println("Generated file: " + fileName);
            return num1;
        } catch (IOException e) {
            System.out.println("Error creating file: " + fileName);
            e.printStackTrace();
        }
return num1;

    }

    public static int generateFileContent(int n,FileWriter writer) throws IOException {
        writer.write("DATE " + generateRandomDate() + " ;\n");
        int count=10;
        int count2=10;
        int num=0;
        for (int i = 0; i < n; i++) {
            String command = generateRandomCommand();
            //writer.write(command + " :\n");

            if (command.equals("APPROV")) {

                writer.write("APPROV : \n");
                num=num+generateMedicamentData(writer);
            } else if (command.equals("PRESCRIPTION")  && count>0) {
                count=count-1;
                writer.write("PRESCRIPTION :\n");
               generatePrescriptionData(writer);
            }else if(command.equals("STOCK")){
                writer.write("STOCK\n");
            }else if(command.equals("DATE")&& count2>0){
                count2=count2-1;
                writer.write("DATE " + generateRandomDate());
            }

            writer.write(";\n");
        }
        return num;
    }

    public static int generateMedicamentData(FileWriter writer) throws IOException {
        int numMedicaments = random.nextInt(20) + 5;

        for (int i = 0; i < numMedicaments; i++) {
            String medicament = getRandomMedicament();
            int stock = random.nextInt(MAX_STOCK) + 1;
            LocalDate date = generateRandomDate();

            writer.write(medicament + "\t" + stock + "\t" + date + "\n");
        }
        return numMedicaments;
    }

    public static void generatePrescriptionData(FileWriter writer) throws IOException {
        int numPrescriptions = random.nextInt(100) + 1; // Generate between 1 to 50 prescriptions
        int rep=random.nextInt(50) + 1;
        for (int i = 0; i < numPrescriptions; i++) {
            String medicament = getRandomMedicament();
            int quantity = random.nextInt(MAX_PRESCRIPTION_QUANTITY) + 1;

            writer.write(medicament + "\t" + quantity + "\t" + rep + "\n");
        }

    }

    public static String getRandomMedicament() {
        return MEDICAMENTS[random.nextInt(MEDICAMENTS.length)];
    }

    public static LocalDate generateRandomDate() {
        LocalDate startDate = LocalDate.of(2000, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        long randomDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay));

        return LocalDate.ofEpochDay(randomDay);
    }

    public static String generateRandomCommand() {
        String[] commands = {"PRESCRIPTION", "APPROV", "STOCK", "DATE"};
        return commands[random.nextInt(commands.length)];
    }

    public static int maindemo(int i, String filePath){
        int prescriptionCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean counting = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("PRESCRIPTION :")) {
                    counting = true;
                } else if (line.startsWith(";")) {
                    counting = false;
                } else if (counting) {
                    prescriptionCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prescriptionCount;
    }
}
