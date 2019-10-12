package com.jjo.h2.exception;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Either<L extends RuntimeException, R> {

  private final L left;
  private final Optional<R> right;

  public static <L extends RuntimeException, R> Either<L, R> of(L left, Optional<R> right) {
    return new Either<>(left, right);
  }

  public Optional<L> getLeft() {
    return Optional.ofNullable(left);
  }

  public Optional<R> getRight() {
    return right;
  }

  public boolean isLeft() {
    return left != null && !isRight();
  }

  public boolean isRight() {
    return right != null && right.isPresent();
  }

  public Either<L, R> mapRight(Function<? super R, R> mapper) {
    if (isRight()) {
      return Either.of(left, right.map(mapper));
    }
    return this;
  }

  public Either<L,R> peek(Consumer<R> action) {
    if (isRight()) {
      action.accept(right.get());
    }
    return this;
  }
  
  public void ifPresent(Consumer<? super R> action) {
    if (isRight()) {
      action.accept(right.get());
    }
  }

  public void ifPresentOrThrow(Consumer<? super R> action) {
    if (!isRight()) {
      throw left;
    } else {
      action.accept(right.get());
    }
  }

  /**
   * Returns the Right if exist, otherwise, it would return the Left
   * @return
   */
  public R getOrElseThrow() {
    if (isLeft() || getRight().isEmpty()) {
      throw left;
    }
    return right.get();
  }
}
