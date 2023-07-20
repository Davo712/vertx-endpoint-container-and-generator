import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
  public void start() {
    Router router = Router.router(vertx);


                router.get(String.format("%s:action", "/")).handler(rc -> {
                    CallService.get(rc, rc.pathParam("action"));
                });


                vertx.createHttpServer()
                        .requestHandler(router)
                        .listen(8080, "localhost", res -> {
                            if (res.succeeded()) {
                                System.out.println("Server started");
                            } else if (res.failed()) {
                                System.out.println("Server failed");
                            }
                        });;
  }
}
