package tao.usecase;

public abstract class UseCase<Response, Params> {
     public abstract Response execute(Params params);
}
