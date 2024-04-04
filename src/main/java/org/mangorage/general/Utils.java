package org.mangorage.general;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class Utils {
    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name, T defaultValue) {
        try {
            return Enum.valueOf(enumClass, name);
        } catch (Exception e ) {
            return defaultValue;
        }
    }

    public static void writeToFile(String content, Path filePath) {

        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InternetAddress[] getAddresses(List<String> addresses) {
        return addresses.stream()
                .map(a -> {
                    try {
                        return InternetAddress.parse(a, false);
                    } catch (AddressException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Arrays::stream)
                .toList()
                .toArray(new InternetAddress[]{});
    }

}
