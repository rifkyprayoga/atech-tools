package com.atech.db.hibernate;

/**
 * Created by andy on 16/02/17.
 */
public interface DataTransformer
{

    <E extends HibernateObject> boolean isTransformationRequired(Class<E> clazz);


    <E extends HibernateObject> void transformData(E dataObject, Class<E> clazz);

}
