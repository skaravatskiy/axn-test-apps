package com.rshtukaraxondevgroup.phototest.presenterTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.FirebasePresenter;
import com.rshtukaraxondevgroup.phototest.repository.FirebaseRepository;
import com.rshtukaraxondevgroup.phototest.view.UploadScreen;

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

@RunWith(RobolectricTestRunner.class)
public class FirebasePresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    UploadScreen uploadScreen;
    @Mock
    FirebaseRepository firebaseRepository;
    private FirebasePresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new FirebasePresenter(uploadScreen, firebaseRepository);
    }

    @Test
    public void uploadDownloadFileFromDropBox() {
        presenter.uploadDownloadFileFromFirebase(TestHelper.uriString, TestHelper.inputStream);
        verify(firebaseRepository).uploadFileInFirebaseStorage(TestHelper.uriString, TestHelper.inputStream, presenter);
        verify(uploadScreen).showProgressBar();
    }

    @Test
    public void downloadSuccessful() {
        presenter.downloadSuccessful(TestHelper.file);
        verify(uploadScreen).hideProgressBar();
        verify(uploadScreen).showImage(TestHelper.file);
    }

    @Test
    public void downloadError() {
        presenter.downloadError(TestHelper.throwable);
        verify(uploadScreen).hideProgressBar();
        verify(uploadScreen).showError(TestHelper.throwable);
    }
}
