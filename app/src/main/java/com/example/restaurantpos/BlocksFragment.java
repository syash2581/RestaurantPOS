package com.example.restaurantpos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantpos.database.DataBaseHelper;
import com.example.restaurantpos.database.DataBaseHelperTables;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlocksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlocksFragment extends Fragment {

    DataBaseHelper myDb;


    private Button btnAdd;
    private GridView gridView;
    private List<View> blocks=new ArrayList<View>();
    private ArrayAdapter arrayAdapter;


    public BlocksFragment() {
        // Required empty public constructor
    }

    public static BlocksFragment newInstance(String param1, String param2) {
        BlocksFragment fragment = new BlocksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DataBaseHelper(getContext());

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        blocks.clear();
        View v = inflater.inflate(R.layout.fragment_blocks,container,false);

        btnAdd = v.findViewById(R.id.btnAdd);
        gridView = v.findViewById(R.id.GBlocks);

        Cursor c = myDb.getAllData();
        while(c.moveToNext())
        {
            int blockno = c.getInt(1);
            String blockname = c.getString(2);
            int blocktablescount = c.getInt(3);


            View block = inflater.inflate(R.layout.block,null);
            TextView tvBlockName = block.findViewById(R.id.tvBlockName);
            tvBlockName.setText(blockname);
            CardView cvBlock = block.findViewById(R.id.cvBlock);
            cvBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),EditBlock.class);
                    intent.putExtra("blockid",blockno);
                    startActivity(intent);
                }
            });

            blocks.add(block);
        }

        arrayAdapter= new MyAdapter(getContext(),R.layout.block,blocks);
        gridView.setAdapter(arrayAdapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(blocks.size()<6)
                {
                    int blockno = blocks.size()+1;

                    View block = inflater.inflate(R.layout.block,null);
                    TextView tvBlockName = block.findViewById(R.id.tvBlockName);
                    tvBlockName.setText("Block "+blockno);
                    CardView cvBlock = block.findViewById(R.id.cvBlock);
                    cvBlock.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(),EditBlock.class);
                            intent.putExtra("blockid",blockno);
                            startActivity(intent);
                        }
                    });

                    if(myDb.insertData(blockno,"Block "+blockno,9))
                    {
                        blocks.add(block);
                    }
                    arrayAdapter= new MyAdapter(getContext(),R.layout.block,blocks);
                    gridView.setAdapter(arrayAdapter);
                }
                else {
                    Toast.makeText(getContext(),"Can not add more than 6 blocks",Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }
}