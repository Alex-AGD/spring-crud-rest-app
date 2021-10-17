package backend.restapp.repo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ToolsRepoTest {
    @Autowired
    private ToolsRepo toolsRepo;


    @AfterAll
    static void afterAll() {

    }

    @Test
    void countHasName() {

    }
}