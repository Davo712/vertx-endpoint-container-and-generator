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
            public void home(RoutingContext rs) {
                System.out.println("HOME");
                rs.response().end(Json.encodeToBuffer("WELCOME HOME"));
            }

            @Override
            public void test(RoutingContext rs) {
                System.out.println("TEST");
                if (rs.queryParam("password").isEmpty()) {
                    rs.response().end(Json.encodeToBuffer("FAIL"));
                } else {
                    rs.response().end(Json.encodeToBuffer("SUCCESS"));
                }
            }
        };


    }
}