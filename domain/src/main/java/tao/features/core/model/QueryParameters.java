package tao.features.core.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Builder
@Getter
public class QueryParameters {
    private Map<String, String> selectedFields;
    private String sortField;
    private String sortDirection;
    private String pagingPacket;
    private String offset;

    @Builder
    @Getter
    public static class UserRequest {
        private Optional<String> selectedFields;
        private Optional<String> sortField;
        private Optional<String> sortDirection;
        private Optional<String> pagingPacket;
        private Optional<String> offset;
    }
}
