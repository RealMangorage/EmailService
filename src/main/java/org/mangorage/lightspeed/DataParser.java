package org.mangorage.lightspeed;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class CSVParser {
    public static void main(String[] args) throws IOException {
        parseExcel("F:\\Minecraft Forge Projects\\EmailService\\build\\run\\customers.csv");
    }
    public static void parseExcel(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            
            for (CSVRecord csvRecord : csvParser) {
                Person person = new Person();
                person.setName(csvRecord.get("name"));
                person.setAge(Integer.parseInt(csvRecord.get("age")));
                person.setEmail(csvRecord.get("email"));
                persons.add(person);
            }
        }
    }
}
