package com.atech.update.v4.data;

import lombok.Builder;
import lombok.Data;

/**
 * Created by andy on 09.04.2024.
 */
@Data
@Builder
public class UpdateFileEntryV4 implements Comparable<UpdateFileEntryV4> {

    int id;
    String version;
    String description;
    int dbVersion;
    String dbChecksum;

    @Override
    public int compareTo(UpdateFileEntryV4 other) {
        return this.id - other.id;
    }
}
