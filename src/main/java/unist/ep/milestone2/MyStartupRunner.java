package unist.ep.milestone2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.job.CSVHelper;
import unist.ep.milestone2.service.*;

import java.io.IOException;
import java.util.Objects;


@Component
@Order(value = 1)
public class MyStartupRunner implements CommandLineRunner {
    private final UserService userService;
    private final ClubService clubService;
    private final RatingService ratingService;
    private final TypeService typeService;
    private final UserClubTypeService userClubTypeService;

    public MyStartupRunner(UserService userService, ClubService clubService, RatingService ratingService, TypeService typeService, UserClubTypeService userClubTypeService) {
        this.userService = userService;
        this.clubService = clubService;
        this.ratingService = ratingService;
        this.typeService = typeService;
        this.userClubTypeService = userClubTypeService;
    }


    @Override
    public void run(String... args) throws Exception {
        uploadUserFile();
        uploadTypeFile();
        uploadClubFile();
        uploadRatingFile();
        uploadUserClubTypeFile();
    }
    public void uploadClubFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/clubs.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                clubService.importClubCsv(file);
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
                ratingService.importRatingCsv(file);
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
                userService.importUserCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void uploadTypeFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/types.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                typeService.importTypeCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void uploadUserClubTypeFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("data/userClubTypes.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                userClubTypeService.importUserClubTypeCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}