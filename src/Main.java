import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        /Users/Nuth/Documents/Develop/Allianz/jmeter/csv/Type1_ALL.csv
        Scanner sc = new Scanner(System.in);
        System.out.print("> Enter input file path : ");
        String csvFile = sc.nextLine();
        String line;
        String cvsSplitBy = ",";
        System.out.print("> Enter output file path : ");
        String outputPath = sc.nextLine();
        System.out.print("> Enter coverage type(T1/NT1) : ");
        String coverageType = sc.nextLine();
        System.out.print("> Enter the number of total record in split file : ");
        int totalSplitRecords = sc.nextInt();

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
            splitToMultipleFile(rows, outputPath, totalSplitRecords, coverageType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void splitToMultipleFile(List<List<String>> rows, String outputPath, int totalSplitRecords, String coverageType) throws IOException {
//        /Users/Nuth/Documents/Develop/Allianz/jmeter/csv/
        System.out.println("Splitting csv file...");
        FileWriter csvWriter = new FileWriter(outputPath+"/"+coverageType+"_1.csv");
        int countWriter = 0;
        int countRow = 0;
        for (List<String> rowData : rows) {
            if (countRow%totalSplitRecords == 0) {
                countWriter++;
                csvWriter.flush();
                csvWriter.close();
                csvWriter = new FileWriter(outputPath+"/"+coverageType+"_"+countWriter+".csv");
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
        System.out.println("==================================");
        System.out.println("Total split file = "+countWriter);
        System.out.println("==================================");
    }
}
