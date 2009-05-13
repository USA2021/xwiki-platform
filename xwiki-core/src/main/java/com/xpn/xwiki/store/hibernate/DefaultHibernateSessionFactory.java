/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xpn.xwiki.store.hibernate;

import java.io.InputStream;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xwiki.component.annotation.Component;

import com.xpn.xwiki.util.Util;

/**
 * Default implementation for {@link HibernateSessionFactory}.
 * 
 * @version $Id: $
 * @since 2.0M1
 * @todo this was coded by Artem. Find out why we need this as a component.
 */
@Component
public class DefaultHibernateSessionFactory implements HibernateSessionFactory
{
    /**
     * Hibernate configuration object.
     */
    private Configuration configuration = new Configuration()
    {
        private static final long serialVersionUID = 1L;

        // there is no #configure(InputStream) so we use #configure(String) and override #getConfigurationInputStream
        @Override
        protected InputStream getConfigurationInputStream(String resource) throws HibernateException
        {
            InputStream stream = Util.getResourceAsStream(resource);
            if (stream == null) {
                throw new HibernateException("Can't find [" + resource + "] for hibernate configuration");
            }
            return stream;
        }
    };

    /**
     * Real Hibernate session factory.
     */
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     * @see HibernateSessionFactory#getConfiguration()
     */
    public Configuration getConfiguration()
    {
        return this.configuration;
    }

    /**
     * {@inheritDoc}
     * @see HibernateSessionFactory#getSessionFactory()
     */
    public SessionFactory getSessionFactory()
    {
        return this.sessionFactory;
    }

    /**
     * {@inheritDoc}
     * @see HibernateSessionFactory#setSessionFactory(SessionFactory)
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
}
