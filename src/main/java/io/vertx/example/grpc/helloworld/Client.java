package io.vertx.example.grpc.helloworld;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.net.SocketAddress;
import io.vertx.grpc.client.GrpcClient;
import io.vertx.grpc.common.GrpcReadStream;


public class Client extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new Client(),new DeploymentOptions().setInstances(1));
  }

  @Override
  public void start() {
    GrpcClient client = GrpcClient.client(vertx);
    client.request(SocketAddress.inetSocketAddress(8080, "localhost"), GreeterGrpc.getSayHelloMethod())
      .compose(request -> {
        request.end(HelloRequest.newBuilder().setName("Kalpani").build());
        return request.response().compose(GrpcReadStream::last);
      })
      .onSuccess(reply -> System.out.println("Succeeded " +reply.getMessage()))
      .onFailure(Throwable::printStackTrace);
  }
}
