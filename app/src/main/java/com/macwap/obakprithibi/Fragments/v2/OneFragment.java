package com.macwap.obakprithibi.Fragments.v2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macwap.obakprithibi.Fragments.v1.Main2Activity;
import com.macwap.obakprithibi.R;


//import com.macwap.obakprithibi.View.ViewModel;


//import android.arch.lifecycle.ViewModelProviders;



public class OneFragment extends Fragment
{
    private static final String TAG = Main2Activity.class.getName();
   // private ViewModel mWordViewModel;



    static RecyclerView productView;
    static Activity activity;




    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {



        final View rootView =  LayoutInflater.from(getActivity()).inflate(R.layout.fragment_one,container, false);

    //  db =  ROOM_AppDatabase.getAppDatabase(getActivity());

   /*     productView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        final ViewAdapter adapter = new ViewAdapter(getActivity());





            productView.setAdapter(adapter);
      //  productView.setNestedScrollingEnabled(false);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
         productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);


       mWordViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getAllWords().observe(this, new Observer<List<ROOM_User>>() {
            @Override
            public void onChanged(@Nullable final List<ROOM_User> room_users) {
                // Update the cached copy of the words in the adapter.
              //  adapter.(room_users);
            }
        });



*/









/*



        productView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        productView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);
        final   List<ROOM_User> userList = db.userDao().getAll();

        activity= getActivity();


          if(userList.size() > 0){
            productView.setVisibility(View.VISIBLE);




            mAdapter = new ROOM_Adapter(getActivity(), userList);

            mAdapter.notifyDataSetChanged();

            productView.setAdapter(mAdapter);

            //productView.scrollBy(0, 0);

        }else {
            //productView.setVisibility(View.GONE);
            //Toast.makeText(getActivity(), getString(R.string.error_not_favarite), Toast.LENGTH_LONG).show();
        }



*/

        return  rootView;
    }



    @Override
    public void onDestroy() {
      //  ROOM_AppDatabase.destroyInstance();
        super.onDestroy();
    }
}