package com.sparcs.casino;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
*
* <p><b>PROBLEM</b>:
org.springframework.boot.test.autoconfigure.AutoConfigureReportTestExecutionListener
is causing prototype Beans (i.e., Rooms) to be created twice because it delegates to
a private instance of {@link DependencyInjectionTestExecutionListener} but an instance
of that listener is already in the chain - see log snippet below:
        
<pre>   Registering TestExecutionListener: org.springframework.test.context.web.ServletTestExecutionListener@1931956
   Registering TestExecutionListener: org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@14a2ec7
-> Registering TestExecutionListener: org.springframework.boot.test.autoconfigure.AutoConfigureReportTestExecutionListener@4718df
-> Registering TestExecutionListener: org.springframework.test.context.support.DependencyInjectionTestExecutionListener@1b7e5df
   Registering TestExecutionListener: org.springframework.test.context.support.DirtiesContextTestExecutionListener@a70f16
   Registering TestExecutionListener: org.springframework.test.context.transaction.TransactionalTestExecutionListener@e136d3
   Registering TestExecutionListener: org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener@8e4bb0
   Registering TestExecutionListener: org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener@79cee3
   Registering TestExecutionListener: org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener@6a205
   Registering TestExecutionListener: org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener@1ba4338
   Registering TestExecutionListener: org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener@1175e74</pre>
</p>

<p><b>WORKAROUND</b>:
Create a custom chain of @{@link TestExecutionListeners} that doesn't include AutoConfigureReportTestExecutionListener.<br>
<br>
<b>NOTE</b>: There's no mechanism to remove a listener - the complete list must be provided.  This makes
this workaround quite fragile (i.e., a future release of Spring might change the list of listeners).<br>
<br>
See Section 15.5 of
http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html</p>
*  
* @author Lee Newfeld
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(
	loader = AnnotationConfigContextLoader.class,
	classes = {
		TestConfiguration.class,
	}
)
@TestPropertySource(locations = "classpath:test.properties")
@TestExecutionListeners({
	DirtiesContextBeforeModesTestExecutionListener.class,
	//AutoConfigureReportTestExecutionListener.class,			// causes duplicates
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	SqlScriptsTestExecutionListener.class,
	RestDocsTestExecutionListener.class,
	ResetMocksTestExecutionListener.class,
	//MockRestServiceServerResetTestExecutionListener.class,	// not visible...
	//MockitoTestExecutionListener.class,						// not visible...
})
public abstract class BaseTest /*extends AbstractTransactionalJUnit4SpringContextTests*/ {

	@Mock
	protected Casino casino;

	@Mock
	protected Customer lee;

    @Before
    public void beforeTest() {
    	
        MockitoAnnotations.initMocks(this);
        
        // Create mocks for other Domain entities
        casino = Mockito.mock(Casino.class);
        lee = Mockito.mock(Customer.class);

        // Add mock functionality
        when(casino.signIn(eq("Lee"), anyString())).thenReturn(lee);
    }
}
