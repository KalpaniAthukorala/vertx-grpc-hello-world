package io.vertx.example.grpc.helloworld;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.grpc.server.GrpcServer;


public class Server extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new Server(),new DeploymentOptions().setInstances(1));
  }
  @Override
  public void start() {
    // Create the server
    GrpcServer rpcServer = GrpcServer.server(vertx);

    // The rpc service
    rpcServer.callHandler(GreeterGrpc.getSayHelloMethod(), request -> {
      request
        .last()
        .onSuccess(msg -> {
          System.out.println("Hello " + msg.getName());
          request.response().end(HelloReply.newBuilder().setMessage(msg.getName()).build());
        });
    });


    // start the server
    vertx.createHttpServer().requestHandler(rpcServer).listen(8080)
      .onFailure(cause -> {
        cause.printStackTrace();
      });
  }
}
