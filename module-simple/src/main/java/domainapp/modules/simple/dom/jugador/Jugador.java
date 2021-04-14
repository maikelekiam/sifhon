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

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import com.google.common.collect.ComparisonChain;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import lombok.AccessLevel;
import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table="Jugador"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="jugador_id"
)
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version"
)
@javax.jdo.annotations.Unique(
        name="Jugador_documento_UNQ",
        members = {"documento"}
)
@DomainObject(
        bounded = true,
        auditing = Auditing.ENABLED
)
@DomainObjectLayout(

)  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Jugador implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;

    public Jugador(String nombre, String apellido, String documento) {
        this.nombre=nombre;
        this.apellido=apellido;
        this.documento=documento;
    }

    public TranslatableString title() {
        return TranslatableString.tr("{nombre}", "nombre",
                this.getApellido() + ", " + this.getNombre()+" (DNI: "+this.getDocumento()+")");
    }

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(editing = Editing.ENABLED)
    @Title(prepend = "Jugador: ")
    private String nombre;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false", length = NAME_LENGTH)
    @Property(editing = Editing.ENABLED)
    @Title(prepend = "Jugador: ")
    private String apellido;

    //DOCUMENTO
    @MemberOrder(sequence = "3")
    @Column(allowsNull="false", length=8)
    @Property(editing = Editing.ENABLED)
    @Title(prepend = "Jugador: ")
    private String documento;

    @Override
    public String toString() {
        return getNombre();
    }

    public int compareTo(final Jugador other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getNombre())
                .result();
    }


    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}