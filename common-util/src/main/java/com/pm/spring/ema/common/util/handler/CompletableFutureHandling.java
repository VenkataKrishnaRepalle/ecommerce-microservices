package com.pm.spring.ema.common.util.handler;

import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import feign.FeignException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompletableFutureHandling {

  @Qualifier("asyncExecutor") private final Executor asyncExecutor;

  public <T> CompletableFuture<T> supplyAsyncWithHandling(Supplier<T> supplier) {
    return CompletableFuture.supplyAsync(supplier, asyncExecutor)
        .handle(
            (result, ex) -> {
              if (ex == null) {
                return result;
              }

              Throwable cause = unwrapException(ex);
              System.out.println("Cause: " + cause.getMessage());
              if (cause instanceof NotFoundException notFoundException) {
                throw notFoundException;
              }
              if (cause instanceof FoundException foundException) {
                throw foundException;
              }
              if (cause instanceof FeignException feignException) {
                throw feignException;
              }

              throw new RuntimeException(cause);
            });
  }

  private static Throwable unwrapException(Throwable throwable) {
    if (throwable instanceof CompletionException && throwable.getCause() != null) {
      return throwable.getCause();
    }
    return throwable;
  }
}
