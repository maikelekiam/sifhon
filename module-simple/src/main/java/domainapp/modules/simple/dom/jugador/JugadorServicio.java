/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.jugador;

import java.util.List;
import org.datanucleus.query.typesafe.TypesafeQuery;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "JugadorMenu",
        repositoryFor = Jugador.class
)
@DomainServiceLayout(
        named = "Jugadores",
        menuOrder = "1"
)
public class JugadorServicio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Jugador> listAll() {
        return repositoryService.allInstances(Jugador.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Jugador> findByName(
            @ParameterLayout(named="Name")
            final String name
    ) {
        TypesafeQuery<Jugador> q = isisJdoSupport.newTypesafeQuery(Jugador.class);
        final QJugador cand = QJugador.candidate();
        q = q.filter(
                cand.name.indexOf(q.stringParameter("name")).ne(-1)
        );
        return q.setParameter("name", name)
                .executeList();
    }

    @Programmatic
    public Jugador findByNameExact(final String name) {
        TypesafeQuery<Jugador> q = isisJdoSupport.newTypesafeQuery(Jugador.class);
        final QJugador cand = QJugador.candidate();
        q = q.filter(
                cand.name.eq(q.stringParameter("name"))
        );
        return q.setParameter("name", name)
                .executeUnique();
    }

    @Programmatic
    public void ping() {
        TypesafeQuery<Jugador> q = isisJdoSupport.newTypesafeQuery(Jugador.class);
        final QJugador candidate = QJugador.candidate();
        q.range(0,2);
        q.orderBy(candidate.name.asc());
        q.executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<JugadorServicio> {}
    @Action(domainEvent = JugadorServicio.CreateDomainEvent.class)
    @MemberOrder(sequence = "3")
    public Jugador create(
            @ParameterLayout(named="Name")
            final String name) {
        return repositoryService.persist(new Jugador(name));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
