package com.example.digital_library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Service
public class SearchBookRequest {
    private static Set<String> allowedKeys = new HashSet<>();
    private static Map<String, List<String>> allowedOperatorsMap = new HashMap<>();
    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;
    @NotBlank
    private String operator;
    private boolean available;

    SearchBookRequest() {

        allowedKeys.addAll(Arrays.asList("name", "author_name", "genre", "pages", "id"));

        allowedOperatorsMap.put("name", Arrays.asList("like", "="));
        allowedOperatorsMap.put("author_name", List.of("="));
        allowedOperatorsMap.put("pages", Arrays.asList("<", "<=", ">", ">=", "="));
        allowedOperatorsMap.put("genre", List.of("="));
        allowedOperatorsMap.put("id", List.of("="));
    }

    public boolean validate() {

        if (!allowedKeys.contains(searchKey)) {
            return false;
        }
        List<String> validAllowedOperators = allowedOperatorsMap.get(this.searchKey);
        return validAllowedOperators.contains(operator);
    }
}
