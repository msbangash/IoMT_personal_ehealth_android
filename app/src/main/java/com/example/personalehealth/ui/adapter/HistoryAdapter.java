package com.example.personalehealth.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalehealth.R;
import com.example.personalehealth.database.History;
import com.example.personalehealth.database.HistoryDetailActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    List<History> arrayList;

    public HistoryAdapter(Context context, List<History> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_history, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        History history = arrayList.get(position);
        holder.name.setText(history.getCreatedAt());

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HistoryDetailActivity.class);
                intent.putExtra("item_id", arrayList.get(position).getU_id());
                intent.putExtra("item_fname", arrayList.get(position).getFirstName());
                intent.putExtra("item_lname", arrayList.get(position).getLastName());
                intent.putExtra("item_dob", arrayList.get(position).getDob());
                intent.putExtra("item_age", arrayList.get(position).getAge());
                intent.putExtra("item_phone", arrayList.get(position).getPhone());
                intent.putExtra("item_gender", arrayList.get(position).getGender());
                intent.putExtra("item_address", arrayList.get(position).getAddress());
                intent.putExtra("item_locality", arrayList.get(position).getLocality());
                intent.putExtra("item_country", arrayList.get(position).getCountryName());
                intent.putExtra("item_lat", arrayList.get(position).getLatitude());
                intent.putExtra("item_lon", arrayList.get(position).getLongitude());
                intent.putExtra("item_pulse", arrayList.get(position).getPulse());
                intent.putExtra("item_ecg", arrayList.get(position).getEcg());
                intent.putExtra("item_ppg", arrayList.get(position).getPpg());
                intent.putExtra("item_temperature", arrayList.get(position).getTemperature());
                intent.putExtra("item_weather", arrayList.get(position).getWeatherTitle());
                intent.putExtra("item_pressure", arrayList.get(position).getPressure());
                intent.putExtra("item_humidity", arrayList.get(position).getHumidity());
                intent.putExtra("item_des", arrayList.get(position).getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout linear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.createdAt);
            linear = itemView.findViewById(R.id.linear);

        }
    }
}
