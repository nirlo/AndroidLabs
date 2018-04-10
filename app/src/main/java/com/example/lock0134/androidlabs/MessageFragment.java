package com.example.lock0134.androidlabs;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lock0134 on 2018-04-04.
 */

public class MessageFragment extends Fragment {


    TextView tv1 ;
    TextView tv2 ;
    Button btnFragment;

    ListView listViewChat;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.details_fragment,null);
        tv1 = view.findViewById(R.id.message_view);
        tv2 = view.findViewById( R.id.message_id);
        btnFragment = view.findViewById(R.id.delete);

        listViewChat = getActivity().findViewById(R.id.lView);


        final Bundle bundle = getArguments();
        final int currentId = bundle.getInt("id");
        final boolean isTablet = bundle.getBoolean("FLAG");

        if (bundle != null )

        {
            tv1.setText(bundle.getString("message"));
            tv2.setText(String.valueOf(bundle.getInt("id")));
        }
        //delete button
        btnFragment.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // to delete the message
                if (!isTablet) {
                    Toast.makeText(getActivity(),"Have fun with on phone mode",Toast.LENGTH_LONG).show();
                    Intent temp = new Intent();
                    temp.putExtra("message", tv1.getText().toString());
                    getActivity().setResult(RESULT_OK, temp);
                    getActivity().finish();
                }//if is on the

                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                MessageFragment messageFragment = (MessageFragment) getFragmentManager().findFragmentByTag("FragmentOnTablet");
                fragmentTransaction.remove(messageFragment);
                fragmentTransaction.commit();
                Toast.makeText(getActivity(),"Have fun with tablet mode",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}




