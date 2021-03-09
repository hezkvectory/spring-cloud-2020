package com.hezk.es;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_REPLICAS;
import static org.elasticsearch.cluster.metadata.IndexMetaData.SETTING_NUMBER_OF_SHARDS;

public class MigrationDocumentationIT extends EsBaseTest {

    @Test
    public void testCreateIndex() throws IOException {
        RestClient restClient = client();
        {
            //tag::migration-create-index
            Settings indexSettings = Settings.builder() // <1>
                    .put(SETTING_NUMBER_OF_SHARDS, 1)
                    .put(SETTING_NUMBER_OF_REPLICAS, 0)
                    .build();

            String payload = XContentFactory.jsonBuilder() // <2>
                    .startObject()
                        .startObject("settings") // <3>
                            .value(indexSettings)
                        .endObject()
                        .startObject("mappings")  // <4>
                            .startObject("doc")
                                .startObject("properties")
                                    .startObject("time")
                                        .field("type", "date")
                                    .endObject()
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject().string();

            HttpEntity entity = new NStringEntity(payload, ContentType.APPLICATION_JSON); // <5>

            Response response = restClient.performRequest("PUT", "my-index", emptyMap(), entity); // <6>
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                // <7>
            }
            //end::migration-create-index
            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testClusterHealth() throws IOException {
        RestClient restClient = client();
        {
            //tag::migration-cluster-health
            Map<String, String> parameters = singletonMap("wait_for_status", "green");
            Response response = restClient.performRequest("GET", "/_cluster/health", parameters); // <1>

            ClusterHealthStatus healthStatus;
            try (InputStream is = response.getEntity().getContent()) { // <2>
                Map<String, Object> map = XContentHelper.convertToMap(XContentType.JSON.xContent(), is, true); // <3>
                healthStatus = ClusterHealthStatus.fromString((String) map.get("status")); // <4>
            }

            if (healthStatus == ClusterHealthStatus.GREEN) {
                // <5>
            }
            //end::migration-cluster-health
            assertSame(ClusterHealthStatus.GREEN, healthStatus);
        }
    }

    @Test
    public void testRequests() throws Exception {
        RestHighLevelClient client = highLevelClient();
        {
            IndexRequest request = new IndexRequest("index", "doc", "id"); // <1>
            request.source("{\"field\":\"value\"}", XContentType.JSON);
            //end::migration-request-ctor

            //tag::migration-request-ctor-execution
            IndexResponse response = client.index(request);
            //end::migration-request-ctor-execution
            assertEquals(RestStatus.CREATED, response.status());
        }
        {
            //tag::migration-request-async-execution
            DeleteRequest request = new DeleteRequest("index", "doc", "id"); // <1>
            client.deleteAsync(request, new ActionListener<DeleteResponse>() { // <2>
                @Override
                public void onResponse(DeleteResponse deleteResponse) {
                    // <3>
                }

                @Override
                public void onFailure(Exception e) {
                    // <4>
                }
            });
            //end::migration-request-async-execution
            assertBusy(() -> assertFalse(client.exists(new GetRequest("index", "doc", "id"))));
        }
        {
            //tag::migration-request-sync-execution
            DeleteRequest request = new DeleteRequest("index", "doc", "id");
            DeleteResponse response = client.delete(request); // <1>
            //end::migration-request-sync-execution
            assertEquals(RestStatus.NOT_FOUND, response.status());
        }
    }

    @Test
    public void deleteIndex(){
        RestHighLevelClient client = highLevelClient();
    }
}
