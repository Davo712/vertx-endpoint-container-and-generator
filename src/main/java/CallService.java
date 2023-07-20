import io.vertx.ext.web.RoutingContext;
import java.lang.String;

public class CallService {
  public static ActionService actionService;

  public static void get(RoutingContext rs, String action) {
     switch (action) {
        case "home": actionService.home(rs);
        break;
        case "test": actionService.test(rs);
        break;
        };
  }
}
