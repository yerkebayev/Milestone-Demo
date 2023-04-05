package com.example.thenext;

import com.example.thenext.job.CSVHelper;
import com.example.thenext.models.Employee;
import com.example.thenext.service.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@Component
@Order(value = 1)
public class MyStartupRunner implements CommandLineRunner {
    private final Service service;
    private static final Logger log = LoggerFactory.getLogger(MyStartupRunner.class);

    public MyStartupRunner(Service service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Converting data...");
        service.saveEmployee(new Employee("Bilbo Baggins", "burglar"));
        service.saveEmployee(new Employee("Frodo Baggins", "thief"));
        uploadMovieFile();
        uploadRatingFile();
        uploadUserFile();
        System.out.println("Ready");

    }
    public void uploadMovieFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/movies.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                service.importMovieCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void uploadRatingFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/ratings.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                service.importRatingCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void uploadUserFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/users.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                service.importUserCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
