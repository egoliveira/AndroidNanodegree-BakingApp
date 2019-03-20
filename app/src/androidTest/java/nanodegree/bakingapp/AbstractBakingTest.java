package nanodegree.bakingapp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import android.content.Context;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

public class AbstractBakingTest {
    private MockWebServer mWebServer;

    protected void startWebServer() {
        mWebServer = new MockWebServer();

        try {
            mWebServer.start(8080);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    protected void stopWebServer() {
        try {
            mWebServer.shutdown();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    protected void setDefaultWebServerResponse() {
        mWebServer.setDispatcher(new WSDispatcher());
    }

    protected void setEmptyWebServerResponse() {
        MockResponse response = new MockResponse().setResponseCode(200)
                .setBody(getTextFile("json/empty.json"));

        mWebServer.enqueue(response);
    }

    protected boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.is_tablet);
    }

    private static Buffer getBinaryFile(String path) {
        byte[] content = null;

        try (InputStream is = AbstractBakingTest.class.getClassLoader().getResourceAsStream(path)) {
            content = IOUtils.toByteArray(is);
        } catch (IOException e) {
            // Do nothing
        }

        if (content == null) {
            Assert.fail("Invalid file: " + path);
        }

        Buffer buffer = new Buffer();
        buffer.write(content);

        return buffer;
    }

    private static String getTextFile(String path) {
        String content = null;

        try (InputStream is = AbstractBakingTest.class.getClassLoader().getResourceAsStream(path)) {
            content = IOUtils.toString(is, Charset.defaultCharset());
        } catch (IOException e) {
            // Do nothing
        }

        if (content == null) {
            Assert.fail("Invalid file: " + path);
        }

        return content;
    }

    private static class WSDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            MockResponse response;

            String path = request.getPath();

            if ("/topher/2017/May/59121517_baking/baking.json".equals(path)) {
                response = new MockResponse().setBody(getTextFile("json/original.json"));
            } else if ("/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(getBinaryFile("resources/-intro-creampie.mp4"))
                        .setResponseCode(200);
            } else if ("/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(getBinaryFile("resources/2-mix-sugar-crackers-creampie.mp4"))
                        .setResponseCode(200);
            } else if ("/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(
                                getBinaryFile("resources/4-press-crumbs-in-pie-plate-creampie.mp4"))
                        .setResponseCode(200);
            } else if ("/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(getBinaryFile("resources/1-mix-marscapone-nutella-creampie.mp4"))
                        .setResponseCode(200);
            } else if ("/del.h-cdn.co/assets/16/32/1470773544-delish-nutella-cool-whip-pie-1.jpg"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(getBinaryFile(
                                "resources/1470773544-delish-nutella-cool-whip-pie-1.jpg"))
                        .setResponseCode(200);
            } else if ("/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4"
                    .equals(path)) {
                response = new MockResponse()
                        .setBody(getBinaryFile(
                                "resources/9-add-mixed-nutella-to-crust-creampie.mp4"))
                        .setResponseCode(200);
            } else if ("/images/articles/2017_08/Facebook-yellow-cake-chocolate-frosting-recipe-dessert.jpg"
                    .equals(path)) {
                response = new MockResponse().setBody(getBinaryFile(
                        "resources/Facebook-yellow-cake-chocolate-frosting-recipe-dessert.jpg"))
                        .setResponseCode(200);
            } else {
                response = new MockResponse().setResponseCode(404);
            }

            return response;
        }
    }
}
