package org.intermine.webservicesspring.api;

import io.swagger.Swagger2SpringBoot;
import org.intermine.webservicesspring.model.Version;
import org.intermine.webservicesspring.model.VersionRelease;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Swagger2SpringBoot.class)
public class VersionApiControllerIntegrationTest {

    @Autowired
    private VersionApi api;

    @Test
    public void versionTest() throws Exception {
        String format = "format_example";
        ResponseEntity<Version> responseEntity = api.version(format);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void versionIntermineTest() throws Exception {
        String format = "format_example";
        ResponseEntity<VersionRelease> responseEntity = api.versionIntermine(format);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void versionReleaseTest() throws Exception {
        String format = "format_example";
        ResponseEntity<VersionRelease> responseEntity = api.versionRelease(format);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
