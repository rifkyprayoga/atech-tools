package com.atech.update.v3.dto;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by andy on 14.03.15.
 */

@Root(name = "updateNotes")
public class UpdateNotesDTO
{

    @ElementList
    public List<ApplicationNoteDTO> notes;
}
