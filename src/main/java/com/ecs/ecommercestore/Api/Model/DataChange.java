package com.ecs.ecommercestore.Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data model to outline data changes for websockets.
 * @param <T> The data type being changed.
 * @author mohamednicer
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataChange<T> {

    /** The ChangeType. */
    private ChangeType changeType;

    /** The data being changed. */
    private T data;

    /**
     * Enum to specify what kind of change is taking place.
     */
    public enum ChangeType{
        INSERT,
        UPDATE,
        DELETE
    }
}
