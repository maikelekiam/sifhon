package domainapp.modules.simple.dom.jugador;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QJugador extends PersistableExpressionImpl<Jugador> implements PersistableExpression<Jugador>
{
    public static final QJugador jdoCandidate = candidate("this");

    public static QJugador candidate(String name)
    {
        return new QJugador(null, name, 5);
    }

    public static QJugador candidate()
    {
        return jdoCandidate;
    }

    public static QJugador parameter(String name)
    {
        return new QJugador(Jugador.class, name, ExpressionType.PARAMETER);
    }

    public static QJugador variable(String name)
    {
        return new QJugador(Jugador.class, name, ExpressionType.VARIABLE);
    }

    public final NumericExpression<Integer> NAME_LENGTH;
    public final StringExpression nombre;
    public final StringExpression apellido;
    public final StringExpression documento;

    public QJugador(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.NAME_LENGTH = new NumericExpressionImpl<Integer>(this, "NAME_LENGTH");
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.apellido = new StringExpressionImpl(this, "apellido");
        this.documento = new StringExpressionImpl(this, "documento");
    }

    public QJugador(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.NAME_LENGTH = new NumericExpressionImpl<Integer>(this, "NAME_LENGTH");
        this.nombre = new StringExpressionImpl(this, "nombre");
        this.apellido = new StringExpressionImpl(this, "apellido");
        this.documento = new StringExpressionImpl(this, "documento");
    }
}
