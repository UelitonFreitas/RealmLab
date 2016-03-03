package com.example.ueliton.realmlab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ueliton.realmlab.Model.Person;
import com.example.ueliton.realmlab.Interface.OnCheckBoxesShow;
import com.example.ueliton.realmlab.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ueliton on 3/2/16.
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {


    private OnCheckBoxesShow onCheckBoxesShow;
    private List<Person> mPersons;
    private List<ListControl> mListControls;
    LayoutInflater inflater;

    public PersonAdapter(Context context, List<Person> persons, OnCheckBoxesShow onCheckBoxesShow) {

        this.inflater = LayoutInflater.from(context);
        this.mPersons = persons;
        this.onCheckBoxesShow = onCheckBoxesShow;
        initializeList();
    }

    private void initializeList() {

        mListControls = new ArrayList<>();
        for (Person person: mPersons) {
            mListControls.add(new ListControl(person));
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_item, parent, false);

        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        if(mPersons.size() > 0) {
            ListControl listControl = mListControls.get(position);
            Person person = mPersons.get(position);
            holder.setName(person.getName());
            holder.setCheckBoxVisible(listControl.getShowCheckbox());
            holder.setItemChecked(listControl.getIsSelected());
        }
    }

    @Override
    public int getItemCount() {
        return mListControls.size();
    }

    public void updatePersons(List<Person> mPersons) {
        this.mPersons = mPersons;
        initializeList();
        notifyDataSetChanged();
    }


    public List<Person> getSelectedItens() {

        List<Person> selectedPersons = new ArrayList<>();
        for (int i = 0; i < mListControls.size(); i++) {
            if(mListControls.get(i).isSelected) {
                selectedPersons.add(mPersons.get(i));
            }
        }
        return selectedPersons;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.text_view_person_name)
        TextView name;

        @Bind(R.id.check_box_select)
        CheckBox isSelected;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListControls.get(getAdapterPosition()).setIsSelected(true);
                    showCheckBoxes();
                    return true;
                }
            });

            isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mListControls.get(getAdapterPosition()).setIsSelected(isChecked);
                }
            });
        }

        public void setName(String name) {
            this.name.setText(name);
        }

        public void setCheckBoxVisible(Boolean showCheckbox) {
            if(showCheckbox)
                this.isSelected.setVisibility(View.VISIBLE);
            else
                this.isSelected.setVisibility(View.GONE);
        }

        public void setItemChecked(Boolean isChecked) {

            this.isSelected.setChecked(isChecked);
        }
    }

    private void showCheckBoxes() {
        for (ListControl listControl : mListControls) {
            listControl.setShowCheckbox(true);
        }
        onCheckBoxesShow.onCheckBoxShow();
        notifyDataSetChanged();
    }

    public class ListControl {

        private Boolean isSelected;
        private Boolean showCheckbox;

        public ListControl(Person aPerson) {

            this.setIsSelected(false);
            this.setShowCheckbox(false);

        }

        public Boolean getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(Boolean isSelected) {
            this.isSelected = isSelected;
        }

        public Boolean getShowCheckbox() {
            return showCheckbox;
        }

        public void setShowCheckbox(Boolean showCheckbox) {
            this.showCheckbox = showCheckbox;
        }
    }
}
