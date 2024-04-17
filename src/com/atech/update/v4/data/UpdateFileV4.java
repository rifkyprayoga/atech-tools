package com.atech.update.v4.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by andy on 09.04.2024.
 */
@Data
@Builder
public class UpdateFileV4 {
    List<UpdateFileEntryV4> update_list;
}
