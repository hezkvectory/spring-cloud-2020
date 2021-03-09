package com.hezk.es;

import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.junit.Test;

import java.io.IOException;

public class MainDocumentationIT extends EsBaseTest {

    @Test
    public void testMain() throws IOException {
        RestHighLevelClient client = highLevelClient();
        {
            //tag::main-execute
            MainResponse response = client.info();
            //end::main-execute
            assertTrue(response.isAvailable());
            //tag::main-response
            ClusterName clusterName = response.getClusterName(); // <1>
            String clusterUuid = response.getClusterUuid(); // <2>
            String nodeName = response.getNodeName(); // <3>
            Version version = response.getVersion(); // <4>
            Build build = response.getBuild(); // <5>
            //end::main-response
            assertNotNull(clusterName);
            assertNotNull(clusterUuid);
            assertNotNull(nodeName);
            assertNotNull(version);
            assertNotNull(build);

        }
    }
}
