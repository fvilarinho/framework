package br.com.concepting.framework.util;

import br.com.concepting.framework.exceptions.ExpectedErrorException;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.resources.exceptions.InvalidResourcesException;
import br.com.concepting.framework.security.exceptions.ExpiredPasswordException;
import br.com.concepting.framework.security.exceptions.PermissionDeniedException;
import br.com.concepting.framework.security.exceptions.UserNotAuthorizedException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionUtilTest {
    @Test
    public void testIdentification() {
        assertEquals("internalError", ExceptionUtil.getId(InternalErrorException.class));
        assertEquals("internalError", ExceptionUtil.getId(new InternalErrorException()));

        assertNull(ExceptionUtil.getId((Class<? extends Throwable>) null));
        assertNull(ExceptionUtil.getId((Throwable) null));
    }

    @Test
    public void testClassification() {
        assertTrue(ExceptionUtil.isUserNotAuthorized(new UserNotAuthorizedException()));
        assertTrue(ExceptionUtil.isPermissionDeniedException(new PermissionDeniedException()));
        assertTrue(ExceptionUtil.isInvalidResourceException(new InvalidResourcesException("resourceId", "resourceKey")));
        assertTrue(ExceptionUtil.isInternalErrorException(new InternalErrorException()));
        assertTrue(ExceptionUtil.isExpectedException(new ExpiredPasswordException()));
        assertTrue(ExceptionUtil.isExpectedWarningException(new ExpiredPasswordException()));
        assertTrue(ExceptionUtil.isExpectedErrorException(new UserNotAuthorizedException()));
        assertTrue(ExceptionUtil.belongsTo(UserNotAuthorizedException.class, ExpectedErrorException.class));

        assertFalse(ExceptionUtil.belongsTo(InternalErrorException.class, null));
        assertFalse(ExceptionUtil.belongsTo(null, ExpectedErrorException.class));
        assertFalse(ExceptionUtil.belongsTo(null, null));
    }
}
