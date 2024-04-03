package org.mangorage.lightspeed;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.mangorage.lightspeed.customer.CustomerInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DataParser {
    public static void main(String[] args) throws IOException {
        var data = parseCustomerData(Path.of("customers.csv").toAbsolutePath().toString());
        for (CustomerInfo customer : data) {
            if (customer.customerID() != 1) {
                System.out.println("""
                        ---------------------------------------------------------------
                        Hello %s %s!
                        
                        Enjoy a 10%s off of any purchase that is $20 USD or more
                        
                        Thanks for being a customer at !
                        ---------------------------------------------------------------
                        """
                        .formatted(
                                customer.firstName(),
                                customer.lastName(),
                                "%"
                        )
                );
            }
        }
    }

    public static List<CustomerInfo> parseCustomerData(String filePath) {
        return parseData(filePath, CustomerInfo.class);
    }

    public static <T> List<T> parseData(String filePath, Class<T> customerClass) {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema
                .emptySchema()
                .withHeader()
                .withColumnSeparator(',');

        try (MappingIterator<T> it = mapper.readerFor(customerClass).with(schema).readValues(new File(filePath))) {
            return it.readAll();
        } catch (IOException e) {
            return List.of();
        }
    }
}
