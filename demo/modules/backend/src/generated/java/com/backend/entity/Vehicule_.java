package com.backend.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Vehicule.class)
public abstract class Vehicule_ extends com.backend.entity.DistributedEntity_ {

	public static volatile SingularAttribute<Vehicule, String> number;
	public static volatile SingularAttribute<Vehicule, VehiculeType> type;

	public static final String NUMBER = "number";
	public static final String TYPE = "type";

}

