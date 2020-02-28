import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "/Users/Nuth/Documents/Develop/Allianz/jmeter/csv/Type1_ALL.csv";
        String line;
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int rowNum = 0;
            List<List<String>> rows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (rowNum != 0) {
                    String[] rowValue = line.split(cvsSplitBy);
                    List<String> eachRow = Arrays.asList(rowValue[0], rowValue[1], rowValue[2], rowValue[3], rowValue[4], rowValue[5],
                            rowValue[6], rowValue[7], rowValue[8], rowValue[9], rowValue[10], rowValue[11], rowValue[12], rowValue[13],
                            rowValue[14], rowValue[15], rowValue[16]);
                    rows.add(eachRow);
                }
                rowNum++;
            }
            splitToMultipleFile(rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void splitToMultipleFile(List<List<String>> rows) throws IOException {
        FileWriter csvWriter = new FileWriter("/Users/Nuth/Documents/Develop/Allianz/jmeter/csv/new_1.csv");
        int countWriter = 0;
        int countRow = 0;
        for (List<String> rowData : rows) {
            if (countRow%1000 == 0) {
                countWriter++;
                csvWriter.flush();
                csvWriter.close();
                csvWriter = new FileWriter("/Users/Nuth/Documents/Develop/Allianz/jmeter/csv/new_"+countWriter+".csv");
                List<String> headerRow = Arrays.asList("no", "PolicyStartDate", "PolicyExpiryDate","channel","make","model","usage",
                        "garageType","vehicleIdentificationNumber","registrationNumber","regionType","engineNumber","productPackages_name",
                        "coverType","modelYear","grossTotal","productCode");
                csvWriter.append(String.join(",", headerRow));
                csvWriter.append("\n");
            }
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
            countRow++;
        }
        csvWriter.flush();
        csvWriter.close();
    }
}
