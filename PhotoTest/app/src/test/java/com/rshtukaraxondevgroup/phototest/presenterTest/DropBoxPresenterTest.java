package com.rshtukaraxondevgroup.phototest.presenterTest;

import com.rshtukaraxondevgroup.phototest.TestHelper;
import com.rshtukaraxondevgroup.phototest.presenter.DropBoxPresenter;
import com.rshtukaraxondevgroup.phototest.repository.DropBoxRepository;
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
public class DropBoxPresenterTest {

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    @Mock
    UploadScreen uploadScreen;
    @Mock
    DropBoxRepository dropBoxRepository;
    private DropBoxPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new DropBoxPresenter(uploadScreen, dropBoxRepository);
    }

    @Test
    public void uploadDownloadFileFromDropBox() {
        presenter.uploadDownloadFileFromDropBox(TestHelper.uriString);
        verify(dropBoxRepository).uploadDownloadFile(TestHelper.uriString, presenter);
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
