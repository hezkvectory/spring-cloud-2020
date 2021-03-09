package com.hezk.es;

import org.elasticsearch.action.main.MainResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class PingAndInfoIT extends EsBaseTest {

    public void testPing() throws IOException {
        assertTrue(highLevelClient().ping());
    }

    @Test
    public void testInfo() throws IOException {
        MainResponse info = highLevelClient().info();
        // compare with what the low level client outputs
        Map<String, Object> infoAsMap = entityAsMap(adminClient().performRequest("GET", "/"));
        assertEquals(infoAsMap.get("cluster_name"), info.getClusterName().value());
        assertEquals(infoAsMap.get("cluster_uuid"), info.getClusterUuid());

        // only check node name existence, might be a different one from what was hit by low level client in multi-node cluster
        assertNotNull(info.getNodeName());
        Map<String, Object> versionMap = (Map<String, Object>) infoAsMap.get("version");
        assertEquals(versionMap.get("build_hash"), info.getBuild().shortHash());
        assertEquals(versionMap.get("build_date"), info.getBuild().date());
        assertEquals(versionMap.get("build_snapshot"), info.getBuild().isSnapshot());
        assertEquals(versionMap.get("number"), info.getVersion().toString());
        assertEquals(versionMap.get("lucene_version"), info.getVersion().luceneVersion.toString());
    }

}