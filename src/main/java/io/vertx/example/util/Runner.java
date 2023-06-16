package io.vertx.example.util;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.example.grpc.helloworld.Client;
import io.vertx.example.grpc.helloworld.Server;

public class Runner {

  public static void main(String[] args) {
    // Start the necessary components or run
    startServer();
    startClient();
  }
  private static void startServer() {
    Vertx.vertx().deployVerticle(new Server());
  }

  private static void startClient() {
    Vertx.vertx().deployVerticle(new Client());
  }

}
