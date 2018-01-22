package dao;

import models.Foodtype;
import models.Restaurant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oFoodtypeDaoTest {

    private Connection conn;
    private Sql2oFoodtypeDao foodtypeDao;
    private Sql2oRestaurantDao restaurantDao;
    private Sql2oReviewDao reviewDao;



        @Before
        public void setUp() throws Exception {
            String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
            Sql2o sql2o = new Sql2o(connectionString, "", "");
            foodtypeDao = new Sql2oFoodtypeDao(sql2o);
            restaurantDao = new Sql2oRestaurantDao(sql2o);
            reviewDao = new Sql2oReviewDao(sql2o);
            conn = sql2o.open();
        }

        @After
        public void tearDown() throws Exception {
            conn.close();
        }

        @Test
        public void addingFoodtypeSetsId() {
            Foodtype testFoodtype = setupNewFoodtype();
            int originalFoodtypeId = testFoodtype.getId();
            foodtypeDao.add(testFoodtype);
            assertNotEquals(originalFoodtypeId, testFoodtype.getId());

        }

        @Test
         public void addedFoodtypesReturnFromGetAll() throws Exception {
            Foodtype testFoodtype = setupNewFoodtype();
            foodtypeDao.add(testFoodtype);
            assertEquals(1, foodtypeDao.getAll().size());
        }

        @Test
        public void noFoodtypesReturnsEmptyList() throws Exception {
            assertEquals(0, foodtypeDao.getAll().size());
        }

    @Test
    public void deleteByIdDeletesCorrectFoodtype() throws Exception {
        Foodtype foodtype = setupNewFoodtype();
        foodtypeDao.add(foodtype);
        foodtypeDao.deleteById(foodtype.getId());
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void addFoodTypeToRestaurantAddsTypeCorrectly() throws Exception {
        Restaurant testRestaurant = setupRestaurant();
        Restaurant testAltRestaurant = setupAltRestaurant();

        restaurantDao.add(testRestaurant);
        restaurantDao.add(testAltRestaurant);

        Foodtype testFoodType = setupNewFoodtype();

        foodtypeDao.add(testFoodType);

        foodtypeDao.addFoodtypeToRestaurant(testFoodType, testRestaurant);
        foodtypeDao.addFoodtypeToRestaurant(testFoodType, testAltRestaurant);

        assertEquals(2, foodtypeDao.getAllRestaurantsForAFoodtype(testFoodType.getId()).size());
    }

    @Test
    public void deletingRestaurantAlsoUpdatesJoinTable() throws Exception {
        Foodtype testFoodtype  = new Foodtype("Seafood");
        foodtypeDao.add(testFoodtype);

        Restaurant testRestaurant = setupRestaurant();
        restaurantDao.add(testRestaurant);

        Restaurant altRestaurant = setupAltRestaurant();
        restaurantDao.add(altRestaurant);

        restaurantDao.addRestaurantToFoodtype(testRestaurant,testFoodtype);
        restaurantDao.addRestaurantToFoodtype(altRestaurant, testFoodtype);

        restaurantDao.deleteById(testRestaurant.getId());
        assertEquals(0, restaurantDao.getAllFoodtypesForARestaurant(testRestaurant.getId()).size());
    }

    @Test
    public void deleteingFoodtypeAlsoUpdatesJoinTable() throws Exception {

        Restaurant testRestaurant = setupRestaurant();

        restaurantDao.add(testRestaurant);

        Foodtype testFoodtype = setupNewFoodtype();
        Foodtype otherFoodType = new Foodtype("Japanese");

        foodtypeDao.add(testFoodtype);
        foodtypeDao.add(otherFoodType);

        foodtypeDao.addFoodtypeToRestaurant(testFoodtype, testRestaurant);
        foodtypeDao.addFoodtypeToRestaurant(otherFoodType,testRestaurant);

        foodtypeDao.deleteById(testRestaurant.getId());
        assertEquals(0, foodtypeDao.getAllRestaurantsForAFoodtype(testFoodtype.getId()).size());
    }


    public Foodtype setupNewFoodtype(){
        return new Foodtype("Sushi");
    }

    public Restaurant setupRestaurant (){
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
    }

    public Restaurant setupAltRestaurant (){
        return new Restaurant("Fish Witch", "214 NE Broadway", "97232", "503-402-9874");
    }


}
