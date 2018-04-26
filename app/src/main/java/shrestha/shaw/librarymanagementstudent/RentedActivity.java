package shrestha.shaw.librarymanagementstudent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RentedActivity extends AppCompatActivity {
    RecyclerView recyclerView ;
    RecyclerView.LayoutManager  layoutManager;
    DatabaseReference reference;
    FirebaseRecyclerOptions<String> options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reference = FirebaseDatabase.getInstance().getReference("Rent").child("16500214006");



        Query query = reference;

        options = new FirebaseRecyclerOptions.Builder<String>().setQuery(query, String.class).build();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<String, RentViewHolder> adapter = new FirebaseRecyclerAdapter<String, RentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RentViewHolder holder, int position, @NonNull String model) {
                Log.d("TAG", model);
                holder.setDateView(model);
                final String isbn = getRef(position).getKey();
                DatabaseReference bookRef;
                bookRef = FirebaseDatabase.getInstance().getReference("Books");
                bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(isbn)){
                            holder.setTitleView(dataSnapshot.child(isbn).child("title").getValue(String.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
               holder.setTitleView(getRef(position).getKey());
            }

            @NonNull
            @Override
            public RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
                return new RentViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
