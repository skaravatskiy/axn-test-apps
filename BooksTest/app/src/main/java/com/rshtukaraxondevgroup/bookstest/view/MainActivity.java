package com.rshtukaraxondevgroup.bookstest.view;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.rshtukaraxondevgroup.bookstest.model.BookModel;
import com.rshtukaraxondevgroup.bookstest.R;
import com.rshtukaraxondevgroup.bookstest.presenter.BookPresenter;
import com.rshtukaraxondevgroup.bookstest.repository.NetworkManager;
import com.rshtukaraxondevgroup.bookstest.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends MvpAppCompatActivity implements MainScreen, SwipeRefreshLayout.OnRefreshListener {
    @InjectPresenter(type = PresenterType.GLOBAL)
    BookPresenter bookPresenter;

    private RecyclerView mRecyclerView;
    private BookListAdapter bookListAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewEmpty;
    private int page = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewEmpty = findViewById(R.id.empty);
        progressBar = findViewById(R.id.progressBar);

        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        bookListAdapter = new BookListAdapter(this, this);
        mRecyclerView.setAdapter(bookListAdapter);

        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        if (bookListAdapter.getItemCount() == 0) {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
        setUpLoadMoreListener();
        bookPresenter.getBooksList(page);
    }

    @Override
    public void onRefresh() {
        bookListAdapter.deleteList();
        page = 1;
        bookPresenter.getBooksList(page);
    }

    @ProvidePresenter(type = PresenterType.GLOBAL)
    BookPresenter provideRepositoryPresenter() {
        BookPresenter repositoryPresenter = new BookPresenter();
        repositoryPresenter.setBookRepository(RepositoryProvider.getInstance(new NetworkManager(this)));
        return repositoryPresenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sortByNumberOfPage:
                bookListAdapter.sortByNumberOfPage();
                break;
            case R.id.sortByPublisher:
                bookListAdapter.sortByPublisher();
                break;
            case R.id.sortByCountry:
                bookListAdapter.sortByCountry();
                break;
        }
        return false;
    }

    @Override
    public void addBooksList(List<BookModel> list) {
        bookListAdapter.addItems(list);
        loading = false;
        progressBar.setVisibility(View.INVISIBLE);
        if (bookListAdapter.getItemCount() > 0) {
            textViewEmpty.setVisibility(View.GONE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemSelected(BookModel linkModel) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("EXTRA_URL", linkModel.getUrl());
        startActivity(intent);
    }

    @Override
    public void onLongClick(BookModel bookModel) {
        bookPresenter.deleteItem(bookModel);
    }

    private void setUpLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + 1)) {
                    page++;
                    bookPresenter.getBooksList(page);
                    progressBar.setVisibility(View.VISIBLE);
                    loading = true;
                }
            }
        });
    }
}