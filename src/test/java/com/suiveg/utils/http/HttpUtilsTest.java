package com.suiveg.utils.http;

import org.junit.Test;
import org.powermock.api.easymock.mockpolicies.Log4jMockPolicy;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Test for
 *
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 * @author <a href="mailto:marius.kristensen@gmail.com">Marius Kristensen</a>
 * @see com.suiveg.utils.image.ImageUtils
 * @since 0.2-SNAPSHOT
 */
@MockPolicy(Log4jMockPolicy.class)
@PrepareForTest({HttpUtils.class})
public class HttpUtilsTest {

}
