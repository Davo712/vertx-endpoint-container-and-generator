import io.vertx.ext.web.RoutingContext;
import java.lang.String;

public class CallService {
  public static ActionService actionService;

  public static void get(RoutingContext rs, String action) {
     switch (action) {
        case "test": actionService.test(rs);
        break;
        case "test1": actionService.test1(rs);
        break;
        case "t": actionService.t(rs);
        break;
        case "gfds": actionService.gfds(rs);
        break;
        case "testTR": actionService.testTR(rs);
        break;
        case "f": actionService.f(rs);
        break;
        case "fnyau": actionService.fnyau(rs);
        break;
        case "fsdf": actionService.fsdf(rs);
        break;
        };
  }
}
