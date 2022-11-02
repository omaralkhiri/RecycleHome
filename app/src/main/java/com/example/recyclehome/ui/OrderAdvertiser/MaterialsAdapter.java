package com.example.recyclehome.ui.OrderAdvertiser;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclehome.Information;
import com.example.recyclehome.Materials;
import com.example.recyclehome.R;
import com.example.recyclehome.Users;

import java.util.ArrayList;
import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MaterialsViewHolder> {
    private Context context;
    private List<Materials> MaterialsList;

    public MaterialsAdapter(Context context, List<Materials> MaterialsList) {
        this.context = context;
        this.MaterialsList = MaterialsList;

    }

    @NonNull
    @Override
    public MaterialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclable_materials, parent, false);
        return new MaterialsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialsViewHolder holder, int position) {

        Materials materials = MaterialsList.get(position);
        holder.setValues(materials);
        EditText editText=holder.weight;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    double weig=Double.parseDouble(editable.toString());
                    Information.setWeight_material(weig);
                }catch (Exception e){
                   e.printStackTrace();
                }
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return MaterialsList.size();
    }

    public void filterList(ArrayList<Materials> filteredlist) {
        MaterialsList = filteredlist;
        notifyDataSetChanged();
    }

    class MaterialsViewHolder extends RecyclerView.ViewHolder {
        private TextView materialname, price;
        private ImageView material_image;
        private CheckBox checkmaterial;
        public EditText weight;

        public MaterialsViewHolder(View itemView) {
            super(itemView);
            material_image = itemView.findViewById(R.id.mat_img);
            materialname = itemView.findViewById(R.id.mat_name);
            price = itemView.findViewById(R.id.price);
            checkmaterial = itemView.findViewById(R.id.checkBox);
            weight = itemView.findViewById(R.id.weight);


            checkmaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (checkmaterial.isChecked()) {
                        int pos = getAdapterPosition();
                        Materials materials = MaterialsList.get(pos);
                        Information.setId_material(materials.getId());
                        weight.setVisibility(View.VISIBLE);
                        weight.setText("0.0");
                    } else {
                        weight.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }
        public void setValues(Materials materials) {
            Glide.with(context).load(materials.getImageId()).into(material_image);
            materialname.setText(materials.getMeterial_Name());
            price.setText(materials.getPrice() + " of kilo");
        }
    }
}




