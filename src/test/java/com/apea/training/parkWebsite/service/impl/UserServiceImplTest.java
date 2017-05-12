package com.apea.training.parkWebsite.service.impl;

import com.apea.training.parkWebsite.connection.mysql.MySqlTransactionHelper;
import com.apea.training.parkWebsite.dao.AreaDao;
import com.apea.training.parkWebsite.dao.CredentialDao;
import com.apea.training.parkWebsite.dao.DaoException;
import com.apea.training.parkWebsite.dao.UserDao;
import com.apea.training.parkWebsite.dao.mysql.MySqlDaoFactory;
import com.apea.training.parkWebsite.domain.Area;
import com.apea.training.parkWebsite.domain.Credential;
import com.apea.training.parkWebsite.domain.User;
import com.apea.training.parkWebsite.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MySqlDaoFactory.class, MySqlTransactionHelper.class})
public class UserServiceImplTest {

    @Mock
    private MySqlDaoFactory factory;

    @Mock
    private MySqlTransactionHelper transactionHelper;

    @Mock
    private UserDao userDao;

    @Mock
    private CredentialDao credentialDao;

    @Before
    public void setUpMocks() {
        mockStatic(MySqlDaoFactory.class);
        mockStatic(MySqlTransactionHelper.class);
        when(MySqlDaoFactory.getInstance()).thenReturn(factory);
        when(MySqlTransactionHelper.getInstance()).thenReturn(transactionHelper);
        when(factory.getUserDao()).thenReturn(userDao);
        when(factory.getCredentialDao()).thenReturn(credentialDao);
    }

    @Test
    public void testTransactionSuccessForCreate() {
        User user = new User.Builder().setId(1).build();
        Credential credential = new Credential();
        InOrder inOrder = inOrder(transactionHelper, userDao, credentialDao);

        ServiceFactoryImpl.getInstance().getUserService().create(user, credential);

        inOrder.verify(transactionHelper).beginTransaction();
        inOrder.verify(userDao).create(eq(user));
        Assert.assertEquals(user.getId(), credential.getUserId());
        inOrder.verify(credentialDao).create(eq(credential));
        inOrder.verify(transactionHelper).commitTransaction();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testTransactionFailForCreate() {
        User user = new User();
        doThrow(DaoException.class).when(userDao).create(eq(user));

        InOrder inOrder = inOrder(transactionHelper, userDao, credentialDao);
        try {
            ServiceFactoryImpl.getInstance().getUserService().create(user, new Credential());
        } catch (ServiceException e) {
            Assert.assertTrue(e.getCause() instanceof DaoException);
        }

        inOrder.verify(transactionHelper).beginTransaction();
        inOrder.verify(userDao).create(eq(user));
        inOrder.verify(transactionHelper).rollbackTransaction();

        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testTransactionSuccessForUpdate() {
        User user = new User();
        Credential credential = new Credential();
        InOrder inOrder = inOrder(transactionHelper);

        ServiceFactoryImpl.getInstance().getUserService().update(user, credential);

        inOrder.verify(transactionHelper).beginTransaction();
        verify(userDao).update(eq(user));
        verify(credentialDao).update(eq(credential));
        inOrder.verify(transactionHelper).commitTransaction();

        verifyNoMoreInteractions(transactionHelper, credentialDao, userDao);
    }

    @Test
    public void testTransactionFailForUpdate() {
        User user = new User();
        doThrow(DaoException.class).when(userDao).create(eq(user));
        InOrder inOrder = inOrder(transactionHelper);

        try {
            ServiceFactoryImpl.getInstance().getUserService().update(user, new Credential());
        } catch (ServiceException e) {
            Assert.assertTrue(e.getCause() instanceof DaoException);
        }

        inOrder.verify(transactionHelper).beginTransaction();
        verify(userDao).update(eq(user));
        inOrder.verify(transactionHelper).commitTransaction();

        verifyNoMoreInteractions(transactionHelper, userDao);
    }

    @Test
    public void testGetAttachedAreas() {
        AreaDao areaDao = mock(AreaDao.class);
        when(factory.getAreaDao()).thenReturn(areaDao);
        when(areaDao.getAll()).thenReturn(Arrays.asList(
                new Area.Builder().setTaskmasterId(1).build(),
                new Area.Builder().setTaskmasterId(2).build(),
                new Area.Builder().setTaskmasterId(1).build()
        ));
        User taskmaster = new User.Builder().setId(1).build();
        List<Area> attachedAreas = ServiceFactoryImpl.getInstance().getUserService().getAttachedAreas(taskmaster);

        Assert.assertEquals(2, attachedAreas.size());
        verify(areaDao).getAll();

        verifyNoMoreInteractions(areaDao);
    }
}