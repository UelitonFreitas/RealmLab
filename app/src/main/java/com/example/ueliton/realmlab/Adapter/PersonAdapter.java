package com.example.ueliton.realmlab.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.ueliton.realmlab.Model.RealmPerson;
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
    private List<RealmPerson> mRealmPersons;
    private List<ListControl> mListControls;
    LayoutInflater inflater;

    public PersonAdapter(Context context, List<RealmPerson> realmPersons, OnCheckBoxesShow onCheckBoxesShow) {

        this.inflater = LayoutInflater.from(context);
        this.mRealmPersons = realmPersons;
        this.onCheckBoxesShow = onCheckBoxesShow;
        initializeList();
    }

    private void initializeList() {

        mListControls = new ArrayList<>();
        for (RealmPerson realmPerson : mRealmPersons) {
            mListControls.add(new ListControl(realmPerson));
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_item, parent, false);

        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        if(mRealmPersons.size() > 0) {
            ListControl listControl = mListControls.get(position);
            RealmPerson realmPerson = mRealmPersons.get(position);
            holder.setName(realmPerson.getName());
            holder.setCheckBoxVisible(listControl.getShowCheckbox());
            holder.setItemChecked(listControl.getIsSelected());
        }
    }

    @Override
    public int getItemCount() {
        return mListControls.size();
    }

    public void updatePersons(List<RealmPerson> mRealmPersons) {
        this.mRealmPersons = mRealmPersons;
        initializeList();
        notifyDataSetChanged();
    }


    public List<RealmPerson> getSelectedItens() {

        List<RealmPerson> selectedRealmPersons = new ArrayList<>();
        for (int i = 0; i < mListControls.size(); i++) {
            if(mListControls.get(i).isSelected) {
                selectedRealmPersons.add(mRealmPersons.get(i));
            }
        }
        return selectedRealmPersons;
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

        public ListControl(RealmPerson aRealmPerson) {

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
