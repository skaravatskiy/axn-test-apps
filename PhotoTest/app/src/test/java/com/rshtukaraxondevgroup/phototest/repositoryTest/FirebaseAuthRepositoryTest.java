package com.rshtukaraxondevgroup.phototest.repositoryTest;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.FirebaseAuthPresenter;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseAuthRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseAuth.class})
@Config(manifest = Config.NONE, sdk = 21)
public class FirebaseAuthRepositoryTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    Task<AuthResult> mockAuthResultTask;
    @Mock
    FirebaseAuthPresenter authPresenter;
    @Mock
    FirebaseAuth auth;

    private FirebaseAuthRepository firebaseAuthRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        auth = mock(FirebaseAuth.class);
        PowerMockito.mockStatic(FirebaseAuth.class);
        when(FirebaseAuth.getInstance()).thenReturn(auth);
        firebaseAuthRepository = new FirebaseAuthRepository();
    }

    @Test
    public void signIn() {
//        when(auth.signInWithEmailAndPassword(TestHelper.email, TestHelper.password)).thenReturn(mockAuthResultTask);
//
//        firebaseAuthRepository.signIn(TestHelper.email, TestHelper.password, authPresenter);
//        verify(authPresenter).showSuccessAuth();
    }

    @Test
    public void registration() {
//        when(auth.createUserWithEmailAndPassword(TestHelper.email, TestHelper.password)).thenReturn(mockAuthResultTask);
//
//        firebaseAuthRepository.registration(TestHelper.email, TestHelper.password, authPresenter);
//        verify(authPresenter).showSuccessRegistration();
    }
}