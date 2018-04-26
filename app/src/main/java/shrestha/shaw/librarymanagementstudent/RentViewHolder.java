package shrestha.shaw.librarymanagementstudent;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RentViewHolder extends RecyclerView.ViewHolder {
    DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("Books");
    TextView titleView;
    TextView dateView;
    
    public RentViewHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.title_view);
        dateView = itemView.findViewById(R.id.date_view);
    }

    public void setTitleView(String title) {
        titleView.setText(title);
    }

    public void setDateView(String date) {
        dateView.setText(date);
    }
}
