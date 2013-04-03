/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.test.annotations.embeddables.nested;

import java.sql.Types;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.Mapping;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.type.CustomType;
import org.hibernate.type.EnumType;

import junit.framework.TestCase;

/**
 * @author Steve Ebersole
 */
public class NestedEmbeddableMetadataTest extends TestCase {

	public void testEnumTypeInterpretation() {
		Configuration cfg = new Configuration().addAnnotatedClass( Customer.class );
		cfg.buildMappings();
		Mapping mapping = cfg.buildMapping();
		PersistentClass classMetadata = cfg.getClassMapping( Customer.class.getName() );
		Property investmentsProperty = classMetadata.getProperty( "investments" );
		Collection investmentsValue = (Collection) investmentsProperty.getValue();
		Component investmentMetadata = (Component) investmentsValue.getElement();
		Component amountMetadata = (Component) investmentMetadata.getProperty( "amount" ).getValue();
		SimpleValue currencyMetadata = (SimpleValue) amountMetadata.getProperty( "currency" ).getValue();
		CustomType currencyType = (CustomType) currencyMetadata.getType();
		int[] currencySqlTypes = currencyType.sqlTypes( mapping );
		assertEquals( 1, currencySqlTypes.length );
		assertEquals( Types.VARCHAR, currencySqlTypes[0] );
	}
}
