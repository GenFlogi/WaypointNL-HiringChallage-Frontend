package nl.waypoint.brombauer.Frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import nl.waypoint.brombauer.bl.DataGenerator;

import java.security.NoSuchAlgorithmException;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer().requestHandler(req -> {
        try {
          req.response()
            .putHeader("content-type", "application/json")
            .end(new ObjectMapper().writeValueAsString(DataGenerator.generateContainer()));
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
          throw new RuntimeException(e);
        }
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
