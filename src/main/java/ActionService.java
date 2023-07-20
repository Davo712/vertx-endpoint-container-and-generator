import io.vertx.ext.web.RoutingContext;

public interface ActionService {
  void home(RoutingContext rs);

  void test(RoutingContext rs);
}
