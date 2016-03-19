package wang.wangxinarhat.recyclerviewfilterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerOnItemClickListener.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    private SearchPeopleAdapter mAdapter;
    private ArrayList<People> mPeopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }


    private void initView() {

        initToolBar();
        initRecyclerView();
        initSearchView();

    }


    /**
     * init Toolbar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    /**
     * init RecyclerView
     */
    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mPeopleList = new ArrayList<>();

        String[] peopleName = {"Kaka", "Modric", "Rooney", "Ibla", "Bale", "死神", "Maurice Moss", "Roy Trenneman", "林夕", "sina", "google", "ecust"};
        String[] peopleDes = {"The best player", "莫德里奇是最好的后腰", "鲁尼踢得不好", "伊贝拉是谁？", "贝尔跑得真快", "Aaron", "Oh, four, I mean five, I mean fire!", "哈哈", "是个艺术家", "weibo", "android", "china"};

        for (int i = 0; i < peopleName.length; i++) {
            mPeopleList.add(new People(peopleName[i], peopleDes[i]));
        }

        mAdapter = new SearchPeopleAdapter(mPeopleList);
        recyclerView.setAdapter(mAdapter);
        HeaderAdapter headerAdapter = new HeaderAdapter(mAdapter);

        recyclerView.setAdapter(headerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerOnItemClickListener(this, recyclerView, this));


    }

    /**
     * init SearchView
     */
    private void initSearchView() {

        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<People> filteredModelList = filter(mPeopleList, newText);

                //reset
                mAdapter.setFilter(filteredModelList);
                mAdapter.animateTo(filteredModelList);
                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                mAdapter.setFilter(mPeopleList);
            }
        });

    }


    /**
     * 筛选逻辑
     * @param peoples
     * @param query
     * @return
     */
    private List<People> filter(List<People> peoples, String query) {
        query = query.toLowerCase();

        final List<People> filteredModelList = new ArrayList<>();
        for (People people : peoples) {

            final String nameEn = people.getName().toLowerCase();
            final String desEn = people.getDescription().toLowerCase();
            final String name = people.getName();
            final String des = people.getDescription();

            if (name.contains(query) || des.contains(query) || nameEn.contains(query) || desEn.contains(query)) {

                filteredModelList.add(people);
            }
        }
        return filteredModelList;
    }


    /**
     * 搜索按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    /**
     * 返回按钮处理
     */
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    /**
     * RecyclerView点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {

        MyToast.showShortToast(mPeopleList.get(position).getName());
    }

    /**
     * RecyclerView长按点击事件
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemLongClick(View view, int position) {
        MyToast.showShortToast(mPeopleList.get(position).getName());

    }

    /**
     * 筛选传递
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
