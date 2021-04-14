package domainapp.modules.simple.dom.impl;

import org.datanucleus.query.typesafe.*;
import org.datanucleus.api.jdo.query.*;

public class QSimpleObject extends PersistableExpressionImpl<SimpleObject> implements PersistableExpression<SimpleObject>
{
    public static final QSimpleObject jdoCandidate = candidate("this");

    public static QSimpleObject candidate(String name)
    {
        return new QSimpleObject(null, name, 5);
    }

    public static QSimpleObject candidate()
    {
        return jdoCandidate;
    }

    public static QSimpleObject parameter(String name)
    {
        return new QSimpleObject(SimpleObject.class, name, ExpressionType.PARAMETER);
    }

    public static QSimpleObject variable(String name)
    {
        return new QSimpleObject(SimpleObject.class, name, ExpressionType.VARIABLE);
    }

    public final StringExpression name;
    public final StringExpression notes;

    public QSimpleObject(PersistableExpression parent, String name, int depth)
    {
        super(parent, name);
        this.name = new StringExpressionImpl(this, "name");
        this.notes = new StringExpressionImpl(this, "notes");
    }

    public QSimpleObject(Class type, String name, ExpressionType exprType)
    {
        super(type, name, exprType);
        this.name = new StringExpressionImpl(this, "name");
        this.notes = new StringExpressionImpl(this, "notes");
    }
}
