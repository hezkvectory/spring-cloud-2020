//package com.hezk.es;
//
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.common.geo.GeoPoint;
//import org.elasticsearch.common.unit.DistanceUnit;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
//import org.elasticsearch.join.query.HasChildQueryBuilder;
//import org.elasticsearch.join.query.JoinQueryBuilders;
//import org.elasticsearch.script.Script;
//import org.elasticsearch.script.ScriptType;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.util.Collections.singletonMap;
//import static org.elasticsearch.index.query.QueryBuilders.*;
//import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.exponentialDecayFunction;
//import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;
//
//public class QueryDSLDocumentationTests extends EsBaseTest {
//    @Test
//    public void testBool() {
//        // tag::bool
//        BoolQueryBuilder query = boolQuery()
//                .must(termQuery("content", "test1"))                 // <1>
//                .must(termQuery("content", "test4"))                 // <1>
//                .mustNot(termQuery("content", "test2"))              // <2>
//                .should(termQuery("content", "test3"))               // <3>
//                .filter(termQuery("content", "test5"));              // <4>
//        // end::bool
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testBoosting() {
//        // tag::boosting
//        BoostingQueryBuilder query = boostingQuery(
//                termQuery("name", "kimchy"),                      // <1>
//                termQuery("name", "dadoonet"))                    // <2>
//                .negativeBoost(0.2f);                                // <3>
//        // end::boosting
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testCommonTerms() {
//        // tag::common_terms
//        CommonTermsQueryBuilder query = commonTermsQuery("name",                                     // <1>
//                "kimchy");                                  // <2>
//        // end::common_terms
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testConstantScore() {
//        // tag::constant_score
//        ConstantScoreQueryBuilder query = constantScoreQuery(
//                termQuery("name", "kimchy"))                          // <1>
//                .boost(2.0f);                                            // <2>
//        // end::constant_score
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testDisMax() {
//        // tag::dis_max
//        DisMaxQueryBuilder query = disMaxQuery()
//                .add(termQuery("name", "kimchy"))                    // <1>
//                .add(termQuery("name", "elasticsearch"))             // <2>
//                .boost(1.2f)                                         // <3>
//                .tieBreaker(0.7f);                                   // <4>
//        // end::dis_max
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testExists() {
//        // tag::exists
//        ExistsQueryBuilder query = existsQuery("name");                                         // <1>
//        // end::exists
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testFunctionScore() {
//        // tag::function_score
//        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {
//                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
//                        matchQuery("name", "kimchy"),                // <1>
//                        randomFunction("ABCDEF")),                   // <2>
//                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
//                        exponentialDecayFunction("age", 0L, 1L))     // <3>
//        };
//        functionScoreQuery(functions);
//        // end::function_score
//    }
//
//    @Test
//    public void testFuzzy() {
//        // tag::fuzzy
//        FuzzyQueryBuilder query = fuzzyQuery(
//                "name",                                              // <1>
//                "kimchy");                                           // <2>
//        // end::fuzzy
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testGeoBoundingBox() {
//        // tag::geo_bounding_box
//        GeoBoundingBoxQueryBuilder query = geoBoundingBoxQuery("pin.location")                          // <1>
//                .setCorners(40.73, -74.1,                                // <2>
//                        40.717, -73.99);                             // <3>
//        // end::geo_bounding_box
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testGeoDistance() {
//        // tag::geo_distance
//        GeoDistanceQueryBuilder query = geoDistanceQuery("pin.location")                             // <1>
//                .point(40, -70)                                          // <2>
//                .distance(200, DistanceUnit.KILOMETERS);                 // <3>
//        // end::geo_distance
//        System.out.println(query.toString());
//    }
//
//    public void testGeoPolygon() {
//        // tag::geo_polygon
//        List<GeoPoint> points = new ArrayList<GeoPoint>();           // <1>
//        points.add(new GeoPoint(40, -70));
//        points.add(new GeoPoint(30, -80));
//        points.add(new GeoPoint(20, -90));
//        geoPolygonQuery("pin.location", points);                     // <2>
//        // end::geo_polygon
//    }
//
////    public void testGeoShape() throws IOException {
////        {
////            // tag::geo_shape
////            GeoShapeQueryBuilder qb = geoShapeQuery(
////                    "pin.location",                                      // <1>
////                    ShapeBuilders.newMultiPoint(                         // <2>
////                            new CoordinatesBuilder()
////                                    .coordinate(0, 0)
////                                    .coordinate(0, 10)
////                                    .coordinate(10, 10)
////                                    .coordinate(10, 0)
////                                    .coordinate(0, 0)
////                                    .build()));
////            qb.relation(ShapeRelation.WITHIN);                           // <3>
////            // end::geo_shape
////        }
////
////        {
////            // tag::indexed_geo_shape
////            // Using pre-indexed shapes
////            GeoShapeQueryBuilder qb = geoShapeQuery(
////                    "pin.location",                                  // <1>
////                    "DEU",                                           // <2>
////                    "countries");                                    // <3>
////            qb.relation(ShapeRelation.WITHIN)                            // <4>
////                    .indexedShapeIndex("shapes")                             // <5>
////                    .indexedShapePath("location");                           // <6>
////            // end::indexed_geo_shape
////        }
////    }
//
//    @Test
//    public void testHasChild() {
//        // tag::has_child
//        HasChildQueryBuilder query = JoinQueryBuilders.hasChildQuery(
//                "blog_tag",                                          // <1>
//                termQuery("tag", "something"),                        // <2>
//                ScoreMode.None);                                     // <3>
//        // end::has_child
//        System.out.println(query.toString());
//    }
//
//    public void testHasParent() {
//        // tag::has_parent
//        JoinQueryBuilders.hasParentQuery(
//                "blog",                                                  // <1>
//                termQuery("tag", "something"),                            // <2>
//                false);                                                  // <3>
//        // end::has_parent
//    }
//
//    @Test
//    public void testIds() {
//        // tag::ids
//        IdsQueryBuilder query = idsQuery("my_type", "type2")
//                .addIds("1", "4", "100");
//        System.out.println(query.toString());
//
//        query = idsQuery()                                                   // <1>
//                .addIds("1", "4", "100");
//        // end::ids
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testMatchAll() {
//        // tag::match_all
//        MatchAllQueryBuilder query = matchAllQuery();
//        // end::match_all
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testMatch() {
//        // tag::match
//        MatchQueryBuilder query = matchQuery(
//                "name",                                              // <1>
//                "kimchy elasticsearch").operator(Operator.AND);
//        // <2>
//        // end::match
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testMoreLikeThis() {
//        // tag::more_like_this
//        String[] fields = {"name.first", "name.last"};               // <1>
//        String[] texts = {"text like this one"};                     // <2>
//
//        MoreLikeThisQueryBuilder query = moreLikeThisQuery(fields, texts, null)
//                .minTermFreq(1)                                          // <3>
//                .maxQueryTerms(12);                                      // <4>
//        // end::more_like_this
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testMultiMatch() {
//        // tag::multi_match
//        MultiMatchQueryBuilder query = multiMatchQuery(
//                "kimchy elasticsearch",                              // <1>
//                "user", "message");                                  // <2>
//        // end::multi_match
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testNested() {
//        // tag::nested
//        NestedQueryBuilder query = nestedQuery(
//                "obj1",                                              // <1>
//                boolQuery()                                          // <2>
//                        .must(matchQuery("obj1.name", "blue"))
//                        .must(rangeQuery("obj1.count").gt(5)),
//                ScoreMode.Avg);                                      // <3>
//        // end::nested
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testPrefix() {
//        // tag::prefix
//        PrefixQueryBuilder query = prefixQuery(
//                "brand",                                             // <1>
//                "heine");                                            // <2>
//        // end::prefix
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testQueryString() {
//        // tag::query_string
//        QueryStringQueryBuilder query = queryStringQuery("+kimchy -elasticsearch");
//        // end::query_string
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testRange() {
//        // tag::range
//        RangeQueryBuilder query = rangeQuery("price")                                          // <1>
//                .from(5)                                                 // <2>
//                .to(10)                                                  // <3>
//                .includeLower(true)                                      // <4>
//                .includeUpper(false);                                    // <5>
//        // end::range
//        System.out.println(query.toString());
//
//        // tag::range_simplified
//        // A simplified form using gte, gt, lt or lte
//        query = rangeQuery("age")                                             // <1>
//                .gte("10")                                                // <2>
//                .lt("20");                                                // <3>
//        // end::range_simplified
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testRegExp() {
//        // tag::regexp
//        RegexpQueryBuilder query = regexpQuery(
//                "name.first",                                        // <1>
//                "s.*y");                                             // <2>
//        // end::regexp
//        System.out.println(query.toString());
//    }
//
//    public void testScript() {
//        // tag::script_inline
//        scriptQuery(
//                new Script("doc['num1'].value > 1")                  // <1>
//        );
//        // end::script_inline
//
//        // tag::script_file
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("param1", 5);
//        scriptQuery(new Script(
//                ScriptType.STORED,                                   // <1>
//                "painless",                                          // <2>
//                "myscript",                                          // <3>
//                singletonMap("param1", 5)));                         // <4>
//        // end::script_file
//    }
//
//    @Test
//    public void testSimpleQueryString() {
//        // tag::simple_query_string
//        SimpleQueryStringBuilder query = simpleQueryStringQuery("+kimchy -elasticsearch");
//        // end::simple_query_string
//        System.out.println(query.toString());
//    }
//
//    public void testSpanContaining() {
//        // tag::span_containing
//        spanContainingQuery(
//                spanNearQuery(spanTermQuery("field1", "bar"), 5)      // <1>
//                        .addClause(spanTermQuery("field1", "baz"))
//                        .inOrder(true),
//                spanTermQuery("field1", "foo"));                      // <2>
//        // end::span_containing
//    }
//
//    public void testSpanFirst() {
//        // tag::span_first
//        spanFirstQuery(
//                spanTermQuery("user", "kimchy"),                     // <1>
//                3                                                    // <2>
//        );
//        // end::span_first
//    }
//
//    public void testSpanMultiTerm() {
//        // tag::span_multi
//        spanMultiTermQueryBuilder(
//                prefixQuery("user", "ki"));                          // <1>
//        // end::span_multi
//    }
//
//    public void testSpanNear() {
//        // tag::span_near
//        spanNearQuery(
//                spanTermQuery("field", "value1"),                     // <1>
//                12)                                                  // <2>
//                .addClause(spanTermQuery("field", "value2"))      // <1>
//                .addClause(spanTermQuery("field", "value3"))      // <1>
//                .inOrder(false);                                 // <3>
//        // end::span_near
//    }
//
//    public void testSpanNot() {
//        // tag::span_not
//        spanNotQuery(
//                spanTermQuery("field", "value1"),                     // <1>
//                spanTermQuery("field", "value2"));                    // <2>
//        // end::span_not
//    }
//
//    public void testSpanOr() {
//        // tag::span_or
//        spanOrQuery(spanTermQuery("field", "value1"))                 // <1>
//                .addClause(spanTermQuery("field", "value2"))              // <1>
//                .addClause(spanTermQuery("field", "value3"));             // <1>
//        // end::span_or
//    }
//
//    public void testSpanTerm() {
//        // tag::span_term
//        spanTermQuery(
//                "user",       // <1>
//                "kimchy");    // <2>
//        // end::span_term
//    }
//
//    public void testSpanWithin() {
//        // tag::span_within
//        spanWithinQuery(
//                spanNearQuery(spanTermQuery("field1", "bar"), 5)     // <1>
//                        .addClause(spanTermQuery("field1", "baz"))
//                        .inOrder(true),
//                spanTermQuery("field1", "foo"));                     // <2>
//        // end::span_within
//    }
//
//    @Test
//    public void testTerm() {
//        // tag::term
//        TermQueryBuilder query = termQuery(
//                "name",                                              // <1>
//                "kimchy");                                           // <2>
//        // end::term
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testTerms() {
//        // tag::terms
//        TermsQueryBuilder query = termsQuery("tags",                                           // <1>
//                "blue", "pill");                                     // <2>
//        // end::terms
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testType() {
//        // tag::type
//        TypeQueryBuilder query = typeQuery("my_type");                                        // <1>
//        // end::type
//        System.out.println(query.toString());
//    }
//
//    @Test
//    public void testWildcard() {
//        // tag::wildcard
//        WildcardQueryBuilder query = wildcardQuery(
//                "user",                                              // <1>
//                "k?mch*");                                           // <2>
//        // end::wildcard
//        System.out.println(query.toString());
//    }
//}