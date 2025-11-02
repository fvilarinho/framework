package br.com.concepting.framework.persistence.types;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersistenceTypesTest {
    @Test
    public void testRelationJoinTypes() {
        assertEquals("none", RelationJoinType.NONE.getOperator());
        assertEquals("left join", RelationJoinType.LEFT_JOIN.getOperator());
        assertEquals("right join", RelationJoinType.RIGHT_JOIN.getOperator());
        assertEquals("inner join", RelationJoinType.INNER_JOIN.getOperator());
    }

    @Test
    public void testRepositoryTypes() {
        assertEquals(8082, RepositoryType.H2.getDefaultPort(), 0);
        assertEquals(1433, RepositoryType.MSSQL.getDefaultPort(), 0);
        assertEquals(2368, RepositoryType.SYBASE.getDefaultPort(), 0);
        assertEquals(50000, RepositoryType.DB2.getDefaultPort(), 0);
        assertEquals(1521, RepositoryType.ORACLE.getDefaultPort(), 0);
        assertEquals(3306, RepositoryType.MYSQL.getDefaultPort(), 0);
        assertEquals(5432, RepositoryType.POSTGRESQL.getDefaultPort(), 0);
        assertEquals(26437, RepositoryType.INFORMIX.getDefaultPort(), 0);

        assertEquals("\"", RepositoryType.H2.getOpenQuote());
        assertEquals("\"", RepositoryType.H2.getCloseQuote());
        assertEquals("[", RepositoryType.MSSQL.getOpenQuote());
        assertEquals("]", RepositoryType.MSSQL.getCloseQuote());
        assertEquals("\"", RepositoryType.SYBASE.getOpenQuote());
        assertEquals("\"", RepositoryType.SYBASE.getCloseQuote());
        assertEquals("\"", RepositoryType.DB2.getOpenQuote());
        assertEquals("\"", RepositoryType.DB2.getCloseQuote());
        assertEquals("\"", RepositoryType.ORACLE.getOpenQuote());
        assertEquals("\"", RepositoryType.ORACLE.getCloseQuote());
        assertEquals("`", RepositoryType.MYSQL.getOpenQuote());
        assertEquals("`", RepositoryType.MYSQL.getCloseQuote());
        assertEquals("\"", RepositoryType.POSTGRESQL.getOpenQuote());
        assertEquals("\"", RepositoryType.POSTGRESQL.getCloseQuote());
        assertEquals("\"", RepositoryType.INFORMIX.getOpenQuote());
        assertEquals("\"", RepositoryType.INFORMIX.getCloseQuote());

        assertEquals("H2 Database Engine", RepositoryType.H2.getDescription());
        assertEquals("MS-SQL Server", RepositoryType.MSSQL.getDescription());
        assertEquals("Sybase ASE/ASA", RepositoryType.SYBASE.getDescription());
        assertEquals("IBM DB2", RepositoryType.DB2.getDescription());
        assertEquals("IBM DB2", RepositoryType.DB2.getDescription());
        assertEquals("Oracle", RepositoryType.ORACLE.getDescription());
        assertEquals("MySQL", RepositoryType.MYSQL.getDescription());
        assertEquals("PostgreSQL", RepositoryType.POSTGRESQL.getDescription());
        assertEquals("Informix", RepositoryType.INFORMIX.getDescription());
    }
}
