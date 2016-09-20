package com.sparcs.casino;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
	loader = AnnotationConfigContextLoader.class,
	classes = {
		TestConfiguration.class,
	}
)
@TestPropertySource(locations = "classpath:test.properties")
public abstract class BaseTest /*extends AbstractTransactionalJUnit4SpringContextTests*/ {

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
}
