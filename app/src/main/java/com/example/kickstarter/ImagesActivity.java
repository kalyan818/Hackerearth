package com.example.kickstarter;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListner,View.OnClickListener{
    private RecyclerView mRecyclerView;
    private TextView Sort,Filter;
    private ImageAdapter.OnItemClickListner mListener;
    private ImageAdapter mAdapter;
    private ImageView cart,search;
    private EditText editText;
    private ProgressBar mProgressCircle;
    private Button logout;
    private FirebaseAuth mAuth;
    private RecyclerView list;
    private ImageView profile;
    private RelativeLayout backsidelayout;
    private String urrl="";
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads,mUploads1;



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.sort,menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        Sort = (TextView)findViewById(R.id.sort);
        Filter = (TextView)findViewById(R.id.filter);
        Filter.setOnClickListener(this);
        editText = (EditText)findViewById(R.id.searchbox);
        backsidelayout = (RelativeLayout)findViewById(R.id.backsidelayout);
        search = (ImageView)findViewById(R.id.search);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        registerForContextMenu(Sort);
        registerForContextMenu(Filter);
        /*DatabaseReference url = FirebaseDatabase.getInstance().getReference("/Image/");
        url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               urrl = dataSnapshot.getValue().toString();
                Picasso.with(getApplicationContext())
                        .load(urrl)
                        .into(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        /*search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Toast.makeText(ImagesActivity.this,editText.getText().toString(),Toast.LENGTH_LONG).show();
                    Query query = FirebaseDatabase.getInstance().getReference("Users/products/")
                            .orderByChild("mName")
                            .equalTo(editText.getText().toString());
                    query.addListenerForSingleValueEvent(valueEventListener);
            }
        });*/
        editText.setOnEditorActionListener(editorActionListener);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = editText.getText().toString();

                firebaseUserSearch(searchText);

            }
        });
        mProgressCircle = (ProgressBar)findViewById(R.id.progress_circle);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try {
                        Upload upload = postSnapshot.getValue(Upload.class);
                        mUploads.add(upload);
                    }catch (Exception e)
                    {
                        System.out.print(e);
                    }
                }
                mAdapter = new ImageAdapter(ImagesActivity.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListner(ImagesActivity.this);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            try {
                String searchText = editText.getText().toString();
                firebaseUserSearch(searchText);
            }catch (Exception e){
                Toast.makeText(ImagesActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            }
            if (i == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            }
            return false;
        }
    };
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Alphabit:
                if (mUploads.size()!=0)
                {
                    Collections.sort(mUploads, new Comparator<Upload>() {
                        @Override
                        public int compare(Upload o1, Upload o2) {
                            return o1.getBy().compareTo(o2.getBy());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.Backers:
                if (mUploads.size()!=0)
                {
                    Collections.sort(mUploads, new Comparator<Upload>() {
                        @Override
                        public int compare(Upload o1, Upload o2) {
                            return o1.getNumbackers().compareTo(o2.getNumbackers());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.Time:
                if (mUploads.size()!=0)
                {
                    Collections.sort(mUploads, new Comparator<Upload>() {
                        @Override
                        public int compare(Upload o1, Upload o2) {
                            return o1.getEndtime().compareTo(o2.getEndtime());
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.Pledged:
                if (mUploads.size()!=0)
                {
                    Collections.sort(mUploads, new Comparator<Upload>() {
                        @Override
                        public int compare(Upload o1, Upload o2) {
                            return String.valueOf(o1.getAmtpledged()).compareTo(String.valueOf(o2.getNumbackers()));
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            case R.id.Funded:
                if (mUploads.size()!=0)
                {
                    Collections.sort(mUploads, new Comparator<Upload>() {
                        @Override
                        public int compare(Upload o1, Upload o2) {
                            return String.valueOf(o1.getPercentagefunded()).compareTo(String.valueOf(o2.getNumbackers()));
                        }
                    });
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            default:return super.onContextItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(int position, View v) {
        Upload jp1 = mUploads.get(position);
        String title = jp1.getTitle();
        String url = jp1.getUrl();
        String currency = jp1.getCurrency();
        String country = jp1.getCountry();
        String by = jp1.getBy();
        String state = jp1.getState();
        String time = jp1.getEndtime();
        String backers = jp1.getNumbackers();
        int pledged = jp1.getAmtpledged();
        String pledged1 = String.valueOf(pledged);
        int funded = jp1.getPercentagefunded();
        String funded1 = String.valueOf(funded);
        String blurb = jp1.getBlurb();
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("title",title);
        i.putExtra("Url",url);
        i.putExtra("Currency",currency);
        i.putExtra("Country",country);
        i.putExtra("By",by);
        i.putExtra("Pledged",pledged1);
        i.putExtra("Funded",funded1);
        i.putExtra("State",state);
        i.putExtra("Time",time);
        i.putExtra("Backers",backers);
        i.putExtra("Blurb",blurb);
        startActivity(i);

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onWhatEverClicked(int position) {
    }

    @Override
    public void onDeleteClick(int position) {
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mUploads.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Upload upload = snapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(ImagesActivity.this,"NO RESULT FOUND",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };




    private void firebaseUserSearch(String searchText) {


        Query firebaseSearchQuery = mDatabaseRef.orderByChild("by").startAt(searchText).endAt(searchText + "\uf8ff");
        firebaseSearchQuery.addListenerForSingleValueEvent(valueEventListener);
/*
        FirebaseRecyclerAdapter<Upload, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Upload, UsersViewHolder>(

                Upload.class,
                R.layout.image_item,
                UsersViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Upload model, int position) {
                viewHolder.setDetails(getApplicationContext(), model.getmName(), model.getmDetails(),model.getmImageUrl(),model.getmPrice(),model.getmUid());
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
        mAdapter1 = firebaseRecyclerAdapter;
        mUploads1 = (List<Upload>) mAdapter1;*/
    }

    @Override
    public void onClick(View v) {
        if(v==Sort)
        {
            if (mUploads.size()!=0)
            {
                Collections.sort(mUploads, new Comparator<Upload>() {
                    @Override
                    public int compare(Upload o1, Upload o2) {
                        return o1.getNumbackers().compareTo(o2.getNumbackers());
                    }
                });
                mAdapter.notifyDataSetChanged();
            }
        }
        if (v==Filter)
        {

        }
    }


    // View Holder Class


}
