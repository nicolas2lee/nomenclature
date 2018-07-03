package tao.usecase.nomenclature.core.repository.sql.exception;

public class SqlWhereClauseFormatInvalidException extends RuntimeException {
    public SqlWhereClauseFormatInvalidException(String message) {
        super(message);
    }
}
