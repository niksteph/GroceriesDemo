package de.evoila.nstephan.groceriesdemo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

/**
 * Utility class containing media types, that are not included in Spring's {@link MediaType}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdditionalMediaTypes {

    /**
     * Public constant media type for {@code application/merge-patch+json}.
     */
    public static final MediaType APPLICATION_MERGE_PATCH_JSON;

    /**
     * A String equivalent of {@link AdditionalMediaTypes#APPLICATION_MERGE_PATCH_JSON}.
     */
    public static final String APPLICATION_MERGE_PATCH_JSON_VALUE = "application/merge-patch+json";

    static {
        APPLICATION_MERGE_PATCH_JSON = new MediaType("application", "merge-patch+json");
    }
}
