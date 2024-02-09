package com.atech.db.datalayer;

/**
 *  This interface is used for determining, which objects are bound by Demo Version of 
 *  software. All settings for Demo objects are now stored in PISDbDemoMngr, so this 
 *  interface just returns id of object.
 * 
 */
public interface DatabaseDemoObject
{

    /**
     * getDemoObjectId - returns demo id of this objectname of DatabaseObject
     * 
     * @return id of demo object (assigned in PISDbDemoMngr)
     */
    int getDemoObjectId();

}
