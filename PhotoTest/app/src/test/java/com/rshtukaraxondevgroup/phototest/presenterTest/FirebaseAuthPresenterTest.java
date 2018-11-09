package com.rshtukaraxondevgroup.phototest.presenterTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.FirebaseAuthPresenter;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;
import com.rshtukaraxondevgroup.phototest.view.AuthScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class FirebaseAuthPresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    AuthScreen authScreen;
    @Mock
    FirebaseAuthRepository firebaseAuthRepository;
    private FirebaseAuthPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new FirebaseAuthPresenter(firebaseAuthRepository, authScreen);
    }

    @Test
    public void showSuccessAuth() {
        presenter.showSuccessAuth();
        verify(authScreen).showSuccessAuth();
    }

    @Test
    public void showErrorAuth() {
        presenter.showErrorAuth(TestHelper.throwable);
        verify(authScreen).showErrorAuth();
    }

    @Test
    public void showSuccessRegistration() {
        presenter.showSuccessRegistration();
        verify(authScreen).showSuccessRegistration();
    }

    @Test
    public void showErrorRegistration() {
        presenter.showErrorRegistration(TestHelper.throwable);
        verify(authScreen).showErrorRegistration();
    }

    @Test
    public void isCurrentUserExist() {
        when(firebaseAuthRepository.isCurrentUserExist()).thenReturn(true);
        presenter.isCurrentUserExist();
    }

    @Test
    public void singIn() {
        presenter.singIn(TestHelper.email, TestHelper.password);
        verify(firebaseAuthRepository).signIn(TestHelper.email, TestHelper.password, presenter);
    }

    @Test
    public void registration() {
        presenter.registration(TestHelper.email, TestHelper.password);
        verify(firebaseAuthRepository).registration(TestHelper.email, TestHelper.password, presenter);
    }
}
