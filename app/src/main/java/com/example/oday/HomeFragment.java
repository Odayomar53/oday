package com.example.oday;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oday.databinding.FragmentSearchBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView storyRv , dashboardRV;
    ArrayList<StoryModel> storylist;
    ArrayList<Post> postList ;

    FirebaseAuth auth;
    FirebaseDatabase database;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_home, container, false );

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

       /* storyRv = view.findViewById( R.id.storyRV );

        storylist= new ArrayList<>();

        StoryAdapter adapter = new StoryAdapter( storylist,getContext() );
        LinearLayoutManager layoutManager= new LinearLayoutManager( getContext(),LinearLayoutManager.HORIZONTAL,false );
        storyRv.setLayoutManager( layoutManager );
        storyRv.setNestedScrollingEnabled( false );
        storyRv.setAdapter( adapter );*/



        dashboardRV = view.findViewById( R.id .dashboardRv );
        postList= new ArrayList<>();

        PostAdapter postAdapter = new PostAdapter( postList, getContext() );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
        dashboardRV.setLayoutManager( linearLayoutManager );
        dashboardRV.addItemDecoration( new DividerItemDecoration( dashboardRV.getContext(),DividerItemDecoration.VERTICAL ) );
        dashboardRV.setNestedScrollingEnabled( false );
        dashboardRV.setAdapter( postAdapter );

        database.getReference().child( "posts" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId( dataSnapshot.getKey() );
                    postList.add( post );
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

        return view;
    }
}