package com.example.carslist;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Адаптер для отображения информации в RecyclerView
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // Класс - держатель одного элемента в списке RecyclerView
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout carRowView;
        TextView carBrand, carModel, carPrice;
        RecyclerViewHolder(View view) {
            super(view);
            carRowView = view.findViewById(R.id.car_row);
            carBrand = view.findViewById(R.id.car_brand);
            carModel = view.findViewById(R.id.car_model);
            carPrice = view.findViewById(R.id.car_price);

            carRowView.setOnClickListener(view1 -> {
                Car current = (Car) carRowView.getTag();
                Intent intent = new Intent(view1.getContext(), CarModifyActivity.class);
                intent.putExtra("id", current.id);
                intent.putExtra("brand", current.brand);
                intent.putExtra("model", current.model);
                intent.putExtra("color", current.color);
                intent.putExtra("price", current.price);
                view1.getContext().startActivity(intent);
            });

            carRowView.setOnLongClickListener(v -> {
                Car current = (Car) carRowView.getTag();
                Intent intent = new Intent(view.getContext(), CarDeleteActivity.class);
                intent.putExtra("id", current.id);
                view.getContext().startActivity(intent);
                return true;
            });
        }

    }

    private List<Car> cars = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_recycler_row,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Car currentCar = cars.get(position);
        holder.carBrand.setText(currentCar.brand);
        holder.carModel.setText(currentCar.model);
        holder.carPrice.setText(String.format(Integer.toString(currentCar.price)));
        holder.carRowView.setTag(currentCar);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public void sortByPrice(String order) {
        if (order.equals(MainActivity.ORDER_ASC)) {
            cars.sort((o1, o2) -> o1.price - o2.price);
        } else {
            cars.sort((o1, o2) -> o2.price - o1.price);
        }
        notifyDataSetChanged();
    }

    public void reload() {
        cars = MainActivity.database.carDao().getAllCars();
        notifyDataSetChanged();
    }
}
