import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassGenerator.path = "src/main/java/";
        ClassGenerator.start();

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());

        CallService.actionService = new ActionService() {
            @Override
            public void test(RoutingContext rs) {
                System.out.println(rs.queryParam("test"));
                rs.response().putHeader("content-type", "application/json");
                rs.response().end(Json.encodeToBuffer("test"));
                System.out.println("dsffsd");

            }

            @Override
            public void test1(RoutingContext rs) {
                rs.response().putHeader("content-type", "application/json");
                rs.response().end(Json.encodeToBuffer("test1"));
            }

            @Override
            public void t(RoutingContext rs) {
                rs.response().putHeader("content-type", "application/json");
                rs.response().end(Json.encodeToBuffer("t"));
            }

            @Override
            public void gfds(RoutingContext rs) {
                rs.response().putHeader("content-type", "application/json");
                rs.response().end(Json.encodeToBuffer("gfds"));
            }

            @Override
            public void testTR(RoutingContext rs) {
                rs.response().putHeader("content-type", "application/json");
                rs.response().end(Json.encodeToBuffer("testTR"));
            }

            @Override
            public void f(RoutingContext rs) {

            }

            @Override
            public void fnyau(RoutingContext rs) {

            }

            @Override
            public void fsdf(RoutingContext rs) {

            }
        };


    }
}