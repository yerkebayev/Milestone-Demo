package com.example.thenext.job;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.thenext.models.Rating;
import com.example.thenext.models.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;
import com.example.thenext.models.Movie;

public class CSVHelper {
        public static String TYPE = "text/csv";

        public static boolean hasCSVFormat(MultipartFile file) {

            return TYPE.equals(file.getContentType());
        }

        public static List<Movie> csvToMovies(InputStream is) {
            try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                 CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

                List<Movie> movies = new ArrayList<>();

                Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                for (CSVRecord csvRecord : csvRecords) {
                    Movie movie = new Movie(
                            Long.parseLong(csvRecord.get("MovieID")),
                            csvRecord.get("Title"),
                            csvRecord.get("Genres")
                    );

                    movies.add(movie);
                }

                return movies;
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
                        Long.parseLong(csvRecord.get("UserID")),
                        csvRecord.get("Gender"),
                        Integer.parseInt(csvRecord.get("Age")),
                        csvRecord.get("Occupation"),
                        csvRecord.get("Zip-code")
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
                        Long.parseLong(csvRecord.get("UserID")),
                        Long.parseLong(csvRecord.get("MovieID")),
                        Integer.parseInt(csvRecord.get("Rating")),
                        csvRecord.get("Timestamp")
                );

                ratings.add(rating);
            }

            return ratings;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream moviesToCSV(List<Movie> movies) {
            final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
                for (Movie movie : movies) {
                    List<String> data = Arrays.asList(
                            String.valueOf(movie.getId()),
                            movie.getTitle(),
                            movie.getGenre()
                    );

                    csvPrinter.printRecord(data);
                }

                csvPrinter.flush();
                return new ByteArrayInputStream(out.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
            }
        }

    }
