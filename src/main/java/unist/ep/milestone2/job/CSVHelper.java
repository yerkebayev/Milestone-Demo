package unist.ep.milestone2.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import unist.ep.milestone2.model.*;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        return TYPE.equals(file.getContentType());
    }

    public static List<Club> csvToClubs(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Club> clubs = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Club club = new Club(
                        Long.parseLong(csvRecord.get("ClubId")),
                        csvRecord.get("Name"),
                        csvRecord.get("Email"),
                        Long.parseLong(csvRecord.get("ClubTypeId")),
                        csvRecord.get("Description"),
                        csvRecord.get("Mission"),
                        csvRecord.get("Contact"),
                        Long.parseLong(csvRecord.get("HeadId"))
                );

                clubs.add(club);
            }

            return clubs;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static List<User> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                User user = new User(
                        Long.parseLong(csvRecord.get("UserId")),
                        csvRecord.get("Name"),
                        csvRecord.get("Surname"),
                        csvRecord.get("Email"),
                        csvRecord.get("Password"),
                        Integer.parseInt(csvRecord.get("Role"))
                );

                users.add(user);
            }

            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static List<Rating> csvToRatings(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
            List<Rating> ratings = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Rating rating = new Rating(
                        Long.parseLong(csvRecord.get("RatingId")),
                        Long.parseLong(csvRecord.get("UserID")),
                        Long.parseLong(csvRecord.get("ClubID")),
                        Integer.parseInt(csvRecord.get("Value")),
                        csvRecord.get("Comment")
                );

                ratings.add(rating);
            }

            return ratings;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static List<UserClubType> csvToUserClubTypes(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<UserClubType> userClubTypes = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                UserClubType userClubType = new UserClubType(
                        Long.parseLong(csvRecord.get("id")),
                        Long.parseLong(csvRecord.get("user_id")),
                        Long.parseLong(csvRecord.get("clubType_id"))
                );

                userClubTypes.add(userClubType);
            }

            return userClubTypes;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    public static List<ClubType> csvToClubTypes(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<ClubType> clubTypes = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                ClubType clubType = new ClubType(
                        Long.parseLong(csvRecord.get("id")),
                        csvRecord.get("name")
                );

                clubTypes.add(clubType);
            }

            return clubTypes;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }




}