package dao;

import models.Foodtype;
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

        public Foodtype setupNewFoodtype() {
            return new Foodtype("Pizza");
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


}
