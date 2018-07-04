package tao.usecase;

import java.sql.SQLException;

public abstract class UseCase<Response, Params> {
     public abstract Response execute(Params params) throws SQLException;
}
