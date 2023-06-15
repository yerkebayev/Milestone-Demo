//package unist.ep.milestone2.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class RatingDataTest {
//
//    @Test
//    void testGetAndSetRating() {
//        MainController.RatingData data = new MainController.RatingData();
//        data.setRating(5);
//        Assertions.assertEquals(5, data.getRating());
//    }
//
//    @Test
//    void testGetAndSetComment() {
//        MainController.RatingData data = new MainController.RatingData();
//        data.setComment("Great product!");
//        Assertions.assertEquals("Great product!", data.getComment());
//    }
//
//    @Test
//    void testConstructor() {
//        MainController.RatingData data = new MainController.RatingData(4, "Good product.");
//        Assertions.assertEquals(4, data.getRating());
//        Assertions.assertEquals("Good product.", data.getComment());
//    }
//
//    @Test
//    void testDefaultConstructor() {
//        MainController.RatingData data = new MainController.RatingData();
//        Assertions.assertNull(data.getRating());
//        Assertions.assertNull(data.getComment());
//    }
//}
