/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package com.metamatrix.dqp.internal.datamgr.language;

import java.util.Arrays;
import java.util.List;

import com.metamatrix.connector.language.IAggregate;
import com.metamatrix.connector.language.IBulkInsert;
import com.metamatrix.connector.language.ICompareCriteria;
import com.metamatrix.connector.language.ICompoundCriteria;
import com.metamatrix.connector.language.ICriteria;
import com.metamatrix.connector.language.IDelete;
import com.metamatrix.connector.language.IElement;
import com.metamatrix.connector.language.IExistsCriteria;
import com.metamatrix.connector.language.IExpression;
import com.metamatrix.connector.language.IFrom;
import com.metamatrix.connector.language.IFromItem;
import com.metamatrix.connector.language.IFunction;
import com.metamatrix.connector.language.IGroup;
import com.metamatrix.connector.language.IGroupBy;
import com.metamatrix.connector.language.IInCriteria;
import com.metamatrix.connector.language.IInlineView;
import com.metamatrix.connector.language.IInsert;
import com.metamatrix.connector.language.IIsNullCriteria;
import com.metamatrix.connector.language.IJoin;
import com.metamatrix.connector.language.ILanguageFactory;
import com.metamatrix.connector.language.ILikeCriteria;
import com.metamatrix.connector.language.ILimit;
import com.metamatrix.connector.language.ILiteral;
import com.metamatrix.connector.language.INotCriteria;
import com.metamatrix.connector.language.IOrderBy;
import com.metamatrix.connector.language.IOrderByItem;
import com.metamatrix.connector.language.IParameter;
import com.metamatrix.connector.language.IProcedure;
import com.metamatrix.connector.language.IQuery;
import com.metamatrix.connector.language.IQueryCommand;
import com.metamatrix.connector.language.IScalarSubquery;
import com.metamatrix.connector.language.ISearchedCaseExpression;
import com.metamatrix.connector.language.ISelect;
import com.metamatrix.connector.language.ISelectSymbol;
import com.metamatrix.connector.language.ISetClause;
import com.metamatrix.connector.language.ISetClauseList;
import com.metamatrix.connector.language.ISetQuery;
import com.metamatrix.connector.language.ISubqueryCompareCriteria;
import com.metamatrix.connector.language.ISubqueryInCriteria;
import com.metamatrix.connector.language.IUpdate;
import com.metamatrix.connector.metadata.runtime.Element;
import com.metamatrix.connector.metadata.runtime.Group;
import com.metamatrix.connector.metadata.runtime.Parameter;
import com.metamatrix.connector.metadata.runtime.Procedure;

/**
 */
public class LanguageFactoryImpl implements ILanguageFactory {

    /**
     * Public instance, holds no state so can be shared by everyone.
     */
    public static final LanguageFactoryImpl INSTANCE = new LanguageFactoryImpl();



    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createAggregate(java.lang.String, boolean, com.metamatrix.data.language.IExpression, java.lang.Class)
     */
    public IAggregate createAggregate(String name, boolean isDistinct, IExpression expression, Class type) {
        return new AggregateImpl(name, isDistinct, expression, type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createCompareCriteria(int, com.metamatrix.data.language.IExpression, com.metamatrix.data.language.IExpression)
     */
    public ICompareCriteria createCompareCriteria(
        ICompareCriteria.Operator operator,
        IExpression leftExpression,
        IExpression rightExpression) {
        return new CompareCriteriaImpl(leftExpression, rightExpression, operator);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createCompoundCriteria(int, java.util.List)
     */
    public ICompoundCriteria createCompoundCriteria(ICompoundCriteria.Operator operator, List innerCriteria) {
        return new CompoundCriteriaImpl(innerCriteria, operator);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createDelete(com.metamatrix.data.language.IGroup, com.metamatrix.data.language.ICriteria)
     */
    public IDelete createDelete(IGroup group, ICriteria criteria) {
        return new DeleteImpl(group, criteria);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createElement(java.lang.String, com.metamatrix.data.language.IGroup, com.metamatrix.data.metadata.runtime.MetadataID)
     */
    public IElement createElement(String name, IGroup group, Element metadataReference, Class type) {
        return new ElementImpl(group, name, metadataReference, type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createExistsCriteria(com.metamatrix.data.language.IQuery)
     */
    public IExistsCriteria createExistsCriteria(IQuery query) {
        return new ExistsCriteriaImpl(query);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createFrom(java.util.List)
     */
    public IFrom createFrom(List items) {
        return new FromImpl(items);
    }
    
    @Override
    public IFunction createFunction(String functionName, IExpression[] args,
    		Class type) {
    	return new FunctionImpl(functionName, Arrays.asList(args), type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createFunction(java.lang.String, com.metamatrix.data.language.IExpression[], java.lang.Class)
     */
    public IFunction createFunction(String functionName, List<? extends IExpression> args, Class type) {
        return new FunctionImpl(functionName, args, type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createGroup(java.lang.String, java.lang.String, com.metamatrix.data.metadata.runtime.MetadataID)
     */
    public IGroup createGroup(String context, String definition, Group metadataReference) {
        return new GroupImpl(context, definition, metadataReference);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createGroupBy(java.util.List)
     */
    public IGroupBy createGroupBy(List items) {
        return new GroupByImpl(items);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createInCriteria(com.metamatrix.data.language.IExpression, java.util.List, boolean)
     */
    public IInCriteria createInCriteria(IExpression leftExpression, List rightExpressions, boolean isNegated) {
        return new InCriteriaImpl(leftExpression, rightExpressions, isNegated);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createInsert(com.metamatrix.data.language.IGroup, java.util.List, java.util.List)
     */
    public IInsert createInsert(IGroup group, List columns, List values) {
        return new InsertImpl(group, columns, values);
    }

    /**
     * @see com.metamatrix.connector.language.ILanguageFactory#createBulkInsert(com.metamatrix.connector.language.IGroup, java.util.List, java.util.List)
     */
    public IBulkInsert createBulkInsert(IGroup group, List columns, List rows) {
        return new BulkInsertImpl(group, columns, rows);
    }
    
    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createIsNullCriteria(com.metamatrix.data.language.IExpression, boolean)
     */
    public IIsNullCriteria createIsNullCriteria(IExpression expression, boolean isNegated) {
        return new IsNullCriteriaImpl(expression, isNegated);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createJoin(int, com.metamatrix.data.language.IFromItem, com.metamatrix.data.language.IFromItem, java.util.List)
     */
    public IJoin createJoin(IJoin.JoinType joinType, IFromItem leftItem, IFromItem rightItem, List criteria) {
        return new JoinImpl(leftItem, rightItem, joinType, criteria);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createLikeCriteria(com.metamatrix.data.language.IExpression, com.metamatrix.data.language.IExpression, java.lang.Character, boolean)
     */
    public ILikeCriteria createLikeCriteria(
        IExpression leftExpression,
        IExpression rightExpression,
        Character escapeCharacter,
        boolean isNegated) {
        return new LikeCriteriaImpl(leftExpression, rightExpression, escapeCharacter, isNegated);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createLiteral(java.lang.Object, java.lang.Class)
     */
    public ILiteral createLiteral(Object value, Class type) {
        return new LiteralImpl(value, type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createNotCriteria(com.metamatrix.data.language.ICriteria)
     */
    public INotCriteria createNotCriteria(ICriteria criteria) {
        return new NotCriteriaImpl(criteria);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createOrderBy(java.util.List)
     */
    public IOrderBy createOrderBy(List items) {
        return new OrderByImpl(items);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createOrderByItem(java.lang.String, com.metamatrix.data.language.IElement, boolean)
     */
    public IOrderByItem createOrderByItem(String name, IElement element, boolean direction) {
        return new OrderByItemImpl(name, direction, element);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createParameter(int, int, java.lang.Object, java.lang.Class)
     */
    public IParameter createParameter(int index, IParameter.Direction direction, Object value, Class type, Parameter metadataReference) {
        return new ParameterImpl(index, direction, value, type, metadataReference);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createProcedure(java.lang.String, java.util.List, com.metamatrix.data.metadata.runtime.MetadataID)
     */
    public IProcedure createProcedure(String name, List parameters, Procedure metadataReference) {
        return new ProcedureImpl(name, parameters, metadataReference);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createQuery(com.metamatrix.data.language.ISelect, com.metamatrix.data.language.IFrom, com.metamatrix.data.language.ICriteria, com.metamatrix.data.language.IGroupBy, com.metamatrix.data.language.ICriteria, com.metamatrix.data.language.IOrderBy)
     */
    public IQuery createQuery(
        ISelect select,
        IFrom from,
        ICriteria where,
        IGroupBy groupBy,
        ICriteria having,
        IOrderBy orderBy) {
        return new QueryImpl(select, from, where, groupBy, having, orderBy);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createScalarSubquery(com.metamatrix.data.language.IQuery)
     */
    public IScalarSubquery createScalarSubquery(IQuery query) {
        return new ScalarSubqueryImpl(query);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createSearchedCaseExpression(java.util.List, java.util.List, com.metamatrix.data.language.IExpression, java.lang.Class)
     */
    public ISearchedCaseExpression createSearchedCaseExpression(
        List whenExpressions,
        List thenExpressions,
        IExpression elseExpression,
        Class type) {
        return new SearchedCaseExpressionImpl(whenExpressions, thenExpressions, elseExpression, type);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createSelect(boolean, java.util.List)
     */
    public ISelect createSelect(boolean isDistinct, List selectSymbols) {
        return new SelectImpl(selectSymbols, isDistinct);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createSelectSymbol(java.lang.String, com.metamatrix.data.language.IExpression)
     */
    public ISelectSymbol createSelectSymbol(String name, IExpression expression) {
        return new SelectSymbolImpl(name, expression);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createSubqueryCompareCriteria(com.metamatrix.data.language.IExpression, int, int, com.metamatrix.data.language.IQuery)
     */
    public ISubqueryCompareCriteria createSubqueryCompareCriteria(
        IExpression leftExpression,
        ICompareCriteria.Operator operator,
        ISubqueryCompareCriteria.Quantifier quantifier,
        IQuery subquery) {
        return new SubqueryCompareCriteriaImpl(leftExpression, operator, quantifier, subquery);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createSubqueryInCriteria(com.metamatrix.data.language.IExpression, com.metamatrix.data.language.IQuery, boolean)
     */
    public ISubqueryInCriteria createSubqueryInCriteria(IExpression expression, IQuery subquery, boolean isNegated) {
        return new SubqueryInCriteriaImpl(expression, isNegated, subquery);
    }

    /* 
     * @see com.metamatrix.data.language.ILanguageFactory#createUpdate(com.metamatrix.data.language.IGroup, java.util.List, com.metamatrix.data.language.ICriteria)
     */
    public IUpdate createUpdate(IGroup group, ISetClauseList updates, ICriteria criteria) {
        return new UpdateImpl(group, updates, criteria);
    }

    public IInlineView createInlineView(IQueryCommand query, String name) {
        return new InlineViewImpl(query, name);
    }

    public ISetQuery createSetOp(ISetQuery.Operation operation, boolean all, IQueryCommand leftQuery, IQueryCommand rightQuery, IOrderBy orderBy, ILimit limit) {
        SetQueryImpl queryImpl = new SetQueryImpl();
        queryImpl.setOperation(operation);
        queryImpl.setAll(all);
        queryImpl.setLeftQuery(leftQuery);
        queryImpl.setRightQuery(rightQuery);
        queryImpl.setOrderBy(orderBy);
        queryImpl.setLimit(limit);
        return queryImpl;
    }

	@Override
	public ISetClause createSetClause(IElement symbol, IExpression value) {
		return new SetClauseImpl(symbol, value);
	}

	@Override
	public ISetClauseList createSetClauseList(List<ISetClause> clauses) {
		return new SetClauseListImpl(clauses);
	}
}
