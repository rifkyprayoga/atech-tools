package com.atech.db.hibernate.tool.data;

/**
 * Created by andy on 27/02/17.
 */
public enum DatabaseImportStrategy
{
    // As per definition we have either Clean or CleanOrAppend or Append
    // strategy
    // DoNotCleanDb is used internally
    Clean, //
    CleanOrAppend, //
    Append, //

    DoNotCleanDb, //
    ;

}
