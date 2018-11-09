package com.rshtukaraxondevgroup.phototest.repositoryTest;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.FirebasePresenter;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;

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
@PrepareForTest({StorageReference.class, FirebaseStorage.class})
@Config(manifest = Config.NONE, sdk = 21)
public class FirebaseRepositoryTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private UploadTask mockUploadTask;

    @Mock
    private FirebasePresenter firebasePresenter;

//    @Mock
//    private FirebaseStorage firebaseStorage;

    @Mock
    private StorageReference storageReference;

    private FirebaseRepository firebaseRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        storageReference = mock(StorageReference.class);
//        firebaseStorage = mock(FirebaseStorage.class);

        PowerMockito.mockStatic(StorageReference.class);
        PowerMockito.mockStatic(FirebaseStorage.class);

        when(FirebaseStorage.getInstance().getReference()).thenReturn(storageReference);
        firebaseRepository = new FirebaseRepository();
    }

    @Test
    public void uploadFileInFirebaseStorage() {
//        when(storageReference.child(TestHelper.uriString).putStream(TestHelper.inputStream)).thenReturn(mockUploadTask);
//
//        firebaseRepository.uploadFileInFirebaseStorage(TestHelper.uriString, TestHelper.inputStream, firebasePresenter);
//        verify(firebasePresenter).downloadSuccessful(TestHelper.file);
    }
}
