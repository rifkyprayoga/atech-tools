package com.atech.utils;

import com.atech.db.hibernate.HibernateDb;
import com.atech.i18n.I18nControlAbstract;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is intended for applications that don't require full stack DataAccess class implementation, so
 * classes that just want to use some of basic classes of the library.
 */
public class ATDataAccessSimple extends ATDataAccessAbstract {

    private static ATDataAccessSimple s_atDataAccessSimple;

    public static ATDataAccessSimple createInstance(I18nControlAbstract ic) {
        s_atDataAccessSimple = new ATDataAccessSimple(ic);
        return s_atDataAccessSimple;
    }

    public static ATDataAccessSimple createInstance(I18nControlAbstract ic, String imageRoot) {
        s_atDataAccessSimple = new ATDataAccessSimple(ic, imageRoot);
        return s_atDataAccessSimple;
    }

    public static ATDataAccessSimple getInstance() {
        return s_atDataAccessSimple;
    }

    @Getter @Setter
    private String imagesRoot;




    protected void defaultInit() {

    }



    /**
     * This is DataAccess constructor; Since classes use Singleton Pattern,
     * constructor is protected and can be accessed only with getInstance()
     * method.
     *
     * @param ic
     */
    protected ATDataAccessSimple(I18nControlAbstract ic) {
        super(ic);
    }

    protected ATDataAccessSimple(I18nControlAbstract ic, String imagesRoot) {
        super(ic);
        this.imagesRoot = imagesRoot;
    }

    @Override
    public void initSpecial() {

    }

    @Override
    public HibernateDb getHibernateDb() {
        return null;
    }

    @Override
    public void loadPlugIns() {

    }

    @Override
    public String getApplicationName() {
        return null;
    }

//    @Override
//    public String getImagesRoot() {
//        return "";
//    }

    @Override
    public void checkPrerequisites() {

    }

    @Override
    public void loadLanguageInfo() {

    }

    @Override
    public void initObserverManager() {

    }

    @Override
    public void loadBackupRestoreCollection() {

    }

    @Override
    public void loadGraphConfigProperties() {

    }

    @Override
    public void loadSpecialParameters() {

    }

    @Override
    public int getMaxDecimalsUsedByDecimalHandler() {
        return 0;
    }

    @Override
    protected void initDataDefinitionManager() {

    }

    @Override
    protected void initInternalSettings() {

    }
}
